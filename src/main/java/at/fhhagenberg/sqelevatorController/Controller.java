package at.fhhagenberg.sqelevatorController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.fhhagenberg.sqelevator.Parser;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.impl.MqttServiceImpl;

public class Controller {

    private final int ElevatorNumber;
    Building building;
    MqttConnector conn;
    Integer timeout = 0;

    enum State {
        DRIVING,
        WAITING;
    }

    State state = State.WAITING;

    Controller(int rlnr, Building b, MqttConnector c) {
        ElevatorNumber = rlnr;
        building = b;
        conn = c;
    }

    int findElButton(int start, Boolean up) {
        int i = start;
        Boolean found = false;
        while (up ? (i < building.getFloorCount()) : (i > 0) && !found) {
            if (up) {
                // found=building.getFloorButtonUp
            }
            i = up ? i + 1 : i - 1;
        }
        return i;
    }

    void run() {
        if (timeout > 0&&building.getSpeed(ElevatorNumber) == 0)
            timeout--;
        if (building.getSpeed(ElevatorNumber) == 0 && timeout == 0 ) {
            Integer nextFloor = building.dequeElevatorRequest(ElevatorNumber);

            if (nextFloor == null)
                {
                    nextFloor =building.dequeFloorRequest();
                }

            if(nextFloor == null)
            {
                conn.setDirection(ElevatorNumber,Direction.UNCOMMITTED);
            }
            else
            {
                int cmp=nextFloor-building.getCurrentFloor(ElevatorNumber);
                if(cmp>0)
                {
                    conn.setDirection(ElevatorNumber,Direction.UP);
                }else if(cmp<0)
                {
                    conn.setDirection(ElevatorNumber,Direction.DOWN);
                }else{
                    conn.setDirection(ElevatorNumber,Direction.UNCOMMITTED);   
                }
                logger.info("depart {} to {}",ElevatorNumber,nextFloor);
                conn.setTargetFloor(ElevatorNumber,nextFloor);
                timeout=10;
            }
        }
    }

    private static AtomicBoolean stopFlag = new AtomicBoolean(false);
    private static final Logger logger = LogManager.getLogger(Controller.class);

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
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }
        MqttService mqttService = new MqttServiceImpl(p.getMqttAddress(), p.getMqttPort());
        mqttService.connect();
        MqttConnector connector = new MqttConnector(mqttService);
        Building building = new Building();
        connector.connect(building);
        run(p, building, connector);
    }

    public static void run(Parser config, Building building, MqttConnector connector) {
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

            // todo: implement stopFlag logic
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
