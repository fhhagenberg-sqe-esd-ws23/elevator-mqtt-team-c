package at.fhhagenberg.sqelevator;


import at.fhhagenberg.sqelevator.controller.ElevatorController;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.service.MqttService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;


public final class MqttBuildingConnector {
    private static final Logger logger = LogManager.getLogger(MqttBuildingConnector.class);
    
    private MqttBuildingConnector(){
        // Utility Class pattern
    }

    public static void connect(MqttService mqttService, ElevatorController controller, Building building) throws Exception {
        for (Elevator  elevator : building.getElevators()) {

            // ================= publish =================
            elevator.acceleration.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.acceleration), value));
            elevator.committedDirection.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.direction), String.valueOf(value)));
            elevator.currentFloor.addListner((id,value)->mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.currentFloor),  String.valueOf(value.getFloorNumber())));
            elevator.currentPosition.addListner((id,value)->mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.currentPosition),  String.valueOf(value)));
            elevator.currentSpeed.addListner((id,value)->mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.speed),  String.valueOf(value)));
            elevator.currentWeight.addListner((id,value)->mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.weight),  String.valueOf(value)));
            elevator.doorStatus.addListner((id,value)->mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.doorStatus),  String.valueOf(value)));
            elevator.floorButtonsState.addListner((id,index,val)->mqttService
                    .publish(MqttTopicGenerator.elPath(id, MqttTopicGenerator.flPath(index,MqttTopicGenerator.floorBtn)), val));
            elevator.floorsServerd.addListner((id,index,val)->mqttService
                    .publish(MqttTopicGenerator.elPath(id, MqttTopicGenerator.flPath(index, MqttTopicGenerator.servedFloor)), val));
            elevator.targetFloor.addListner((id,value)->mqttService
                    .publish(MqttTopicGenerator.elPath(id,MqttTopicGenerator.targetFloor),  String.valueOf(value.getFloorNumber())));
            // ================= subscribe =================


            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.direction), (topic,publish)->{
                logger.debug("{} {}", topic, publish.getPayloadAsBytes());

                int i=Integer.parseInt(new String(publish.getPayloadAsBytes()));
                controller.setCommittedDirection(elevator.getElevatorNumber(), Direction.values()[i]);
            });
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.flPath('+',MqttTopicGenerator.servedFloor)), (topic,publish)->{
                String[] topics=topic.split("/");
                int floor=Integer.parseInt(topics[topics.length-2]);
                controller.setServicesFloors(elevator.getElevatorNumber(),floor,ByteBuffer.wrap(publish.getPayloadAsBytes()).getInt()==1);
            });
            mqttService.subscribe(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.targetFloor), (topic,publish)->{
                logger.debug("{} {}", topic, publish.getPayloadAsBytes());
                int i=Integer.parseInt(new String(publish.getPayloadAsBytes()));
                controller.setTarget(elevator.getElevatorNumber(),i);
            });
        }

        for (Floor floor: building.getFloors()) {
            // ================= publish =================
            floor.upButton.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.flPath(id,MqttTopicGenerator.btnUp), value));
            floor.downButton.addListner((id, value) -> mqttService
                    .publish(MqttTopicGenerator.flPath(id,MqttTopicGenerator.btnDown), value));

            // // ================= subscribe =================

            mqttService.subscribe(MqttTopicGenerator.flPath(floor,MqttTopicGenerator.btnUp), (topic, publish) -> {
                logger.debug("Floor {} UP Pressed: {}", floor.getFloorNumber(), publish.getPayloadAsBytes());
                controller.setTarget(0, floor.getFloorNumber());
            });
            mqttService.subscribe(MqttTopicGenerator.flPath(floor,MqttTopicGenerator.btnDown), (topic, publish) -> {
                logger.debug("Floor {} DOWN Pressed: {}", floor.getFloorNumber(), publish.getPayloadAsBytes());
                controller.setTarget(0, floor.getFloorNumber());
            });
        }
    }
}
