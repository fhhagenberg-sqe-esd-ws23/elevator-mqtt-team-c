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
    Integer timeout=0;
    enum State{
        DRIVING,
        WAITING;
    }
    State state=State.WAITING;

    Controller(int rlnr, Building b, MqttConnector c) {
        ElevatorNumber = rlnr;
        building = b;
        conn = c;
    }

    void run() {
        if(timeout>0)timeout--;
        if (building.getSpeed(ElevatorNumber) == 0 && timeout==0) {
            if(state==State.DRIVING)
            
            {timeout=15;
                state=State.WAITING;
            }
            else
            if (building.getDirection(ElevatorNumber) == Direction.UP) {
                int nextFloor = building.getCurrentFloor(ElevatorNumber);
                do {
                    nextFloor++;
                } while (nextFloor < building.getFloorCount()
                        && !(building.getUpButton(nextFloor) || building.getElevaorButton(ElevatorNumber, nextFloor)));
                if (nextFloor < building.getFloorCount()) {
                    conn.setTargetFloor(ElevatorNumber, nextFloor);
                    state=State.DRIVING;
                } else {
                    if(building.getCurrentFloor(ElevatorNumber)==building.getFloorCount()-1)
                    {conn.setDirection(ElevatorNumber, Direction.DOWN);}
                    else
                    {conn.setTargetFloor(ElevatorNumber, building.getCurrentFloor(ElevatorNumber)+1);
                    state=State.DRIVING;}
                }

            } else {
                int nextFloor = building.getCurrentFloor(ElevatorNumber);
                do {
                    nextFloor--;
                } while (nextFloor > 0 && !(building.getDownButton(nextFloor)
                        || building.getElevaorButton(ElevatorNumber, nextFloor)));
                if (nextFloor > 0) {
                    conn.setTargetFloor(ElevatorNumber, nextFloor);
                    state=State.DRIVING;
                } else {
                    if(building.getCurrentFloor(ElevatorNumber)==0)
                    {conn.setDirection(ElevatorNumber, Direction.UP);}
                    else
                    {
                    conn.setTargetFloor(ElevatorNumber, building.getCurrentFloor(ElevatorNumber)-1);
                    state=State.DRIVING;
                    }
                }
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
