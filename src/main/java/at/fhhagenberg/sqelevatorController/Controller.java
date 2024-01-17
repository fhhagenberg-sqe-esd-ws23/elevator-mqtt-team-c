package at.fhhagenberg.sqelevatorcontroller;

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
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.impl.MqttServiceImpl;

public class Controller {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private final int elevatorNumber;
    Building building;
    MqttConnector conn;
    Integer timeout = 0;

    public Controller(int elevatorCount, Building b, MqttConnector c) {
        elevatorNumber = elevatorCount;
        building = b;
        conn = c;
    }

    public void run() {
        if (building.getSpeed(elevatorNumber) == 0
                && building.getElevatorDoor(elevatorNumber)==DoorStatus.OPEN
                && timeout == 0 ) {
            Integer nextFloor = building.dequeElevatorRequest(elevatorNumber);

            if (nextFloor == null)
                {
                    nextFloor =building.dequeFloorRequest();
                }

            if(nextFloor == null)
            {
                conn.setDirection(elevatorNumber,Direction.UNCOMMITTED);
            }
            else
            {
                int cmp=nextFloor-building.getCurrentFloor(elevatorNumber);
                if(cmp>0)
                {
                    conn.setDirection(elevatorNumber,Direction.UP);
                }else if(cmp<0)
                {
                    conn.setDirection(elevatorNumber,Direction.DOWN);
                }else{
                    conn.setDirection(elevatorNumber,Direction.UNCOMMITTED);
                }
                logger.info("elevator {} departs to {}", elevatorNumber,nextFloor);
                conn.setTargetFloor(elevatorNumber,nextFloor);
            }
        }
    }


}
