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

public final class MqttBuildingConnector {
    private static final Logger logger = LogManager.getLogger(MqttBuildingConnector.class);
    
    private MqttBuildingConnector(){
        // Utility Class pattern
    }
    public static void preConnect(MqttService mqttService, Building building)
    {
        building.addElevatorCountListner((id,val)-> mqttService
        .publish(MqttTopicGenerator.ELEVATORS, String.valueOf(val)));
        building.addFloorCountListner((id,val)-> mqttService
        .publish(MqttTopicGenerator.FLOORS, String.valueOf(val)));
    }
    public static void connect(MqttService mqttService, ElevatorController controller, Building building,ScheduleService scheduler) {
        
        for (Elevator  elevator : building.getElevators()) {

            // ================= publish =================
            elevator.addAccelerationListener((id, value) -> mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.ACCELERATION), value));
            elevator.addCommittedDirectionListener((id, value) -> mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.DIRECTION), String.valueOf(value)));
            elevator.addCurrentFloorListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.CURRENT_FLOOR),  String.valueOf(value.getFloorNumber())));
            elevator.addCurrentPositionListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.CURRENT_POSITION),  String.valueOf(value)));
            elevator.addCurrentSpeedListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.SPEED),  String.valueOf(value)));
            elevator.addCurrentWeightListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.WEIGHT),  String.valueOf(value)));
            elevator.addDoorStatusListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.DOOR_STATE),  String.valueOf(value)));
            elevator.addFloorButtonsStateListener((id,index,val)->mqttService
            .publish(MqttTopicGenerator.elPath(id, MqttTopicGenerator.flPath(index,MqttTopicGenerator.FLOOR_BTN)), val));
            elevator.addFloorsServerdListener((id,index,val)->mqttService
            .publish(MqttTopicGenerator.elPath(id, MqttTopicGenerator.flPath(index, MqttTopicGenerator.SERVED_FLOOR)), val));
            elevator.addTargetFloorListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.TARGET_FLOOR),  String.valueOf(value.getFloorNumber())));
            // ================= subscribe =================


            mqttService.subscribe(MqttTopicGenerator.STOP_PATH, (ign, ored) -> {scheduler.stop();
            logger.info("Received stop signal. Bye!");});
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.DIRECTION), (topic,publish)->{
                MqttParser.Ret<Direction,Integer> x=MqttParser.parse(topic, publish, Direction::valueOf, Integer::valueOf);
                controller.setCommittedDirection(x.topics[0], x.value);
            });
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.flPath('+',MqttTopicGenerator.SERVED_FLOOR)), (topic,publish)->{
                MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
                controller.setServicesFloors(elevator.getElevatorNumber(),x.topics[0].intValue(),x.value);
            });
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.TARGET_FLOOR), (topic,publish)->{
                MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
                controller.setTarget(elevator.getElevatorNumber(),x.value);
            });
        }

        for (Floor floor: building.getFloors()) {
            // ================= publish =================
            floor.upButton.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.flPath(id,MqttTopicGenerator.BTN_UP), value));
            floor.downButton.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.flPath(id,MqttTopicGenerator.BTN_DOWN), value));
        }
    }
}
