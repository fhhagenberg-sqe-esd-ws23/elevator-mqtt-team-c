package at.fhhagenberg.sqelevatorcontroller;

import at.fhhagenberg.sqelevator.Parser;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.impl.MqttServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerRunner {
    private static AtomicBoolean stopFlag = new AtomicBoolean(false);
    private static final Logger logger = LogManager.getLogger(ControllerRunner.class);

    public static void main(String[] args) throws Exception {
        Parser p = new Parser();
        var configFile = new File(p.parseArguments(args));
        try {
            if (!configFile.exists()) {
                logger.error("File not found: {}", configFile.getAbsolutePath());
                System.exit(-1);
            }
            p.parseFile(new FileInputStream(configFile));
        } catch (IOException e) {

            e.printStackTrace();
            System.exit(-1);
        }
        MqttService mqttService = new MqttServiceImpl(p.getMqttAddress(), p.getMqttPort());
        mqttService.connect();
        MqttConnector connector = new MqttConnector(mqttService);
        Building building = new Building();
        connector.connect(building);

        ControllerRunner runner = new ControllerRunner();
        runner.run(p, building, connector);
    }

    public void run(Parser config, Building building, MqttConnector connector) {
        List<Controller> controllers = new ArrayList<>();
        int interval = config.getInterval();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        try {
            executor.scheduleAtFixedRate(() -> {
                if (stopFlag.get()) {
                    executor.shutdown();
                    return;
                }

                for (int i = controllers.size(); i < building.getElevatorCount(); i++) {
                    controllers.add(new Controller(i, building, connector));
                }

                for (Controller controller : controllers) {
                    controller.run();
                }

            }, 0, interval, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("Error scheduling elevators ", e);
        }
    }
}
