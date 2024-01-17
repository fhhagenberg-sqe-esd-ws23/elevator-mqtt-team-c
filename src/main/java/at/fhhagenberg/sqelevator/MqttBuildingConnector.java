package at.fhhagenberg.sqelevator;


import at.fhhagenberg.sqelevator.controller.ElevatorController;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.ScheduleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;


public final class MqttBuildingConnector {
    private static final Logger logger = LogManager.getLogger(MqttBuildingConnector.class);

    static String stopPath = "system/stop";
    static String elPath(Elevator id,String p)
    {
        return "elevator/" + id.getElevatorNumber()+"/"+p;
    }
    static String flPath(Floor id,String p)
    {
        return flPath(id.getFloorNumber() ,p);
    }
    static String flPath(Integer id,String p)
    {
        return "floor/" + id +"/"+p;
    }
    static String acceleration="accel";
    static String direction="direction";
    static String currentFloor="floor";
    static String currentPosition="curentPos";
    static String speed="curentspeed";
    static String weight="weight";
    static String doorStatus="doorState";
    static String floorBtn="Button";
    static String servedFloor="served";
    static String targetFloor="targetfloor";
    static String btnUp="btn/up";
    static String btnDown="btn/down";

    private MqttBuildingConnector(){
        // Utility Class pattern
    }

    public static void connect(MqttService mqttService, ElevatorController controller, Building building,
                               ScheduleService scheduler) {
        for (Elevator  elevator : building.getElevators()) {

            // ================= publish =================
            elevator.addAccelerationListener((id, value) -> mqttService
                    .publish(elPath(id,acceleration), value));
            elevator.addCommittedDirectionListener((id, value) -> mqttService
                    .publish(elPath(id,direction), String.valueOf(value)));
            elevator.addCurrentFloorListener((id,value)->mqttService
                    .publish(elPath(id,currentFloor),  String.valueOf(value.getFloorNumber())));
            elevator.addCurrentPositionListener((id,value)->mqttService
                    .publish(elPath(id,currentPosition),  String.valueOf(value)));
            elevator.addCurrentSpeedListener((id,value)->mqttService
                    .publish(elPath(id,speed),  String.valueOf(value)));
            elevator.addCurrentWeightListener((id,value)->mqttService
                    .publish(elPath(id,weight),  String.valueOf(value)));
            elevator.addDoorStatusListener((id,value)->mqttService
                    .publish(elPath(id,doorStatus),  String.valueOf(value)));
            elevator.addFloorButtonsStateListener((id,index,val)->mqttService
                    .publish(elPath(id, flPath(index,floorBtn)), val));
            elevator.addFloorsServerdListener((id,index,val)->mqttService
                    .publish(elPath(id, flPath(index, servedFloor)), val));
            elevator.addTargetFloorListener((id,value)->mqttService
                    .publish(elPath(id,targetFloor),  String.valueOf(value.getFloorNumber())));
            // ================= subscribe =================

            mqttService.subscribe(stopPath, (ign, ored) -> {
                scheduler.stop();
                logger.info("Received stop signal. Bye!");
            });
            mqttService.subscribe(elPath(elevator, direction), (topic,publish)->{
                logger.debug("{} {}", topic, publish.getPayloadAsBytes());

                int i=Integer.parseInt(new String(publish.getPayloadAsBytes()));
                controller.setCommittedDirection(elevator.getElevatorNumber(), Direction.values()[i]);
            });
            mqttService.subscribe(elPath(elevator, "floor/+/served"), (topic,publish)->{
                String[] topics=topic.split("/");
                int floor=Integer.parseInt(topics[topics.length-2]);
                controller.setServicesFloors(elevator.getElevatorNumber(),floor,ByteBuffer.wrap(publish.getPayloadAsBytes()).getInt()==1);
            });
            mqttService.subscribe(elPath(elevator, targetFloor), (topic,publish)->{
                logger.debug("{} {}", topic, publish.getPayloadAsBytes());
                int i=Integer.parseInt(new String(publish.getPayloadAsBytes()));
                controller.setTarget(elevator.getElevatorNumber(),i);
            });
        }

        for (Floor floor: building.getFloors()) {
            // ================= publish =================
            floor.upButton.addListner((id, value) -> mqttService
                    .publish(flPath(id,btnUp), value));
            floor.downButton.addListner((id, value) -> mqttService
                    .publish(flPath(id,btnDown), value));

            // // ================= subscribe =================

            mqttService.subscribe(flPath(floor,btnUp), (topic, publish) -> {
                logger.debug("Floor {} UP Pressed: {}", floor.getFloorNumber(), publish.getPayloadAsBytes());
                controller.setTarget(0, floor.getFloorNumber());
            });
            mqttService.subscribe(flPath(floor,btnDown), (topic, publish) -> {
                logger.debug("Floor {} DOWN Pressed: {}", floor.getFloorNumber(), publish.getPayloadAsBytes());
                controller.setTarget(0, floor.getFloorNumber());
            });
        }
    }
}
