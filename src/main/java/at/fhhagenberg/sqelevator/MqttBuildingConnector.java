package at.fhhagenberg.sqelevator;


import java.io.Console;
import java.nio.ByteBuffer;

import at.fhhagenberg.sqelevator.controller.ElevatorController;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.service.MqttService;


public class MqttBuildingConnector {
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
    public MqttBuildingConnector(MqttService mqttService, ElevatorController controller, Building building) throws Exception {
        for (Elevator  elevator : building.getElevators()) {
            
            // ================= publish =================
            elevator.acceleration.addListner((id, value) -> mqttService
                .publish(elPath(id,acceleration), value));    
            elevator.committedDirection.addListner((id, value) -> mqttService
                .publish(elPath(id,direction), String.valueOf(value)));
            elevator.currentFloor.addListner((id,value)->mqttService
                .publish(elPath(id,currentFloor),  String.valueOf(value)));
            elevator.currentPosition.addListner((id,value)->mqttService
                .publish(elPath(id,currentPosition),  String.valueOf(value)));
            elevator.currentSpeed.addListner((id,value)->mqttService
                .publish(elPath(id,speed),  String.valueOf(value)));
            elevator.currentWeight.addListner((id,value)->mqttService
                .publish(elPath(id,weight),  String.valueOf(value)));
            elevator.doorStatus.addListner((id,value)->mqttService
                .publish(elPath(id,doorStatus),  String.valueOf(value)));
            elevator.floorButtonsState.addListner((id,index,val)->mqttService
                .publish(elPath(id, flPath(index,floorBtn)), val));
            elevator.floorsServerd.addListner((id,index,val)->mqttService
                .publish(elPath(id, flPath(index, servedFloor)), val));
            elevator.targetFloor.addListner((id,value)->mqttService
                .publish(elPath(id,targetFloor),  String.valueOf(value.getFloorNumber())));
            // ================= subscribe =================
            
            
            mqttService.subscribe(elPath(elevator, direction), (topic,publish)->{
                // System.out.println(topic+" "+publish.getPayloadAsBytes());
                int i=Integer.parseInt(new String(publish.getPayloadAsBytes()));
                controller.setCommittedDirection(elevator.getElevatorNumber(), Direction.values()[i]);
            });
            mqttService.subscribe(elPath(elevator, "floor/+/served"), (topic,publish)->{
                System.out.println(topic+" "+publish.getPayloadAsBytes());
                String[] topics=topic.split("/");
                int floor=Integer.parseInt(topics[topics.length-2]);
                controller.setServicesFloors(elevator.getElevatorNumber(),floor,ByteBuffer.wrap(publish.getPayloadAsBytes()).getInt()==1);
            });
            mqttService.subscribe(elPath(elevator, targetFloor), (topic,publish)->{
                System.out.println(topic+" "+new String(publish.getPayloadAsBytes()));
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

            // mqttService.subscribe(flPath(floor,btnUp), (topic, publish) -> {
            //     System.out.println("Floor " + floor.getFloorNumber() + " UP Pressed: " + new String(publish.getPayloadAsBytes()));
            //     controller.setTarget(0, floor.getFloorNumber());
            // });
            // mqttService.subscribe(flPath(floor,btnDown), (topic, publish) -> {
            //     System.out.println("Floor " + floor.getFloorNumber() + " DOWN Pressed: " + new String(publish.getPayloadAsBytes()));
            //     controller.setTarget(0, floor.getFloorNumber());
            // });
        }
    }
}
