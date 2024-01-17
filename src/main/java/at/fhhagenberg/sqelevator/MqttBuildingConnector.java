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
    
    private MqttBuildingConnector(){
        // Utility Class pattern
    }
    public static void preConnect(MqttService mqttService, ElevatorController controller, Building building)
    {
        building.addElevatorCountListner((id,val)-> mqttService
        .publish(MqttTopicGenerator.elevators, String.valueOf(val)));
        building.addFloorCountListner((id,val)-> mqttService
        .publish(MqttTopicGenerator.floors, String.valueOf(val)));
    }
    public static void connect(MqttService mqttService, ElevatorController controller, Building building,ScheduleService scheduler) throws Exception {
        
        for (Elevator  elevator : building.getElevators()) {

            // ================= publish =================
            elevator.addAccelerationListener((id, value) -> mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.acceleration), value));
            elevator.addCommittedDirectionListener((id, value) -> mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.direction), String.valueOf(value)));
            elevator.addCurrentFloorListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.currentFloor),  String.valueOf(value.getFloorNumber())));
            elevator.addCurrentPositionListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.currentPosition),  String.valueOf(value)));
            elevator.addCurrentSpeedListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.speed),  String.valueOf(value)));
            elevator.addCurrentWeightListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.weight),  String.valueOf(value)));
            elevator.addDoorStatusListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.doorStatus),  String.valueOf(value)));
            elevator.addFloorButtonsStateListener((id,index,val)->mqttService
            .publish(MqttTopicGenerator.elPath(id, MqttTopicGenerator.flPath(index,MqttTopicGenerator.floorBtn)), val));
            elevator.addFloorsServerdListener((id,index,val)->mqttService
            .publish(MqttTopicGenerator.elPath(id, MqttTopicGenerator.flPath(index, MqttTopicGenerator.servedFloor)), val));
            elevator.addTargetFloorListener((id,value)->mqttService
            .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.targetFloor),  String.valueOf(value.getFloorNumber())));
            // ================= subscribe =================


            mqttService.subscribe(MqttTopicGenerator.stopPath, (ign, ored) -> {scheduler.stop();
            logger.info("Received stop signal. Bye!");});
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.direction), (topic,publish)->{
                MqttParser.Ret<Direction,Integer> x=MqttParser.parse(topic, publish, Direction::valueOf, Integer::valueOf);
                controller.setCommittedDirection(x.topics[0], x.value);
            });
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.flPath('+',MqttTopicGenerator.servedFloor)), (topic,publish)->{
                MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
                controller.setServicesFloors(elevator.getElevatorNumber(),x.topics[0].intValue(),x.value);
            });
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.targetFloor), (topic,publish)->{
                MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
                controller.setTarget(elevator.getElevatorNumber(),x.value);
            });
        }

        for (Floor floor: building.getFloors()) {
            // ================= publish =================
            floor.upButton.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.flPath(id,MqttTopicGenerator.btnUp), value));
            floor.downButton.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.flPath(id,MqttTopicGenerator.btnDown), value));

            // // ================= subscribe =================

        }
    }
}
