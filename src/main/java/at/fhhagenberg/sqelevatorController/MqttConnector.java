package at.fhhagenberg.sqelevatorController;

import at.fhhagenberg.sqelevator.MqttTopicGenerator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.MqttService;

public class MqttConnector {
MqttService mqttService;
    MqttConnector(MqttService s){
        mqttService=s;
    }
    public void connect( Building b) throws Exception {
        // TODO Auto-generated method stub 
        mqttService.subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.flPath('+',MqttTopicGenerator.floorBtn)),(topic,publish)->{
            String[] topics=topic.split("/");
            int floor=Integer.parseInt(topics[topics.length-2]);
            int elevator=Integer.parseInt(topics[topics.length-4]);
            b.setElevatorButton(elevator, floor, new String(publish.getPayloadAsBytes())=="1");
    });
    mqttService.subscribe(MqttTopicGenerator.elPath('+',MqttTopicGenerator.currentFloor),(topic,publish)->{String[] topics=topic.split("/");
            int floor=Integer.parseInt(topics[topics.length-2]);
            int currentFloor=Integer.parseInt(new String(publish.getPayloadAsBytes()));
            b.setCurrentFloor(floor, currentFloor);
    });
    // mqttService.subscribe(MqttTopicGenerator.elPath('+',MqttTopicGenerator.doorStatus),(topic,publish)->{String[] topics=topic.split("/");
    //         int floor=Integer.parseInt(topics[topics.length-2]);
    //         int door=DoorStatus.values()[Integer.parseInt(new String(publish.getPayloadAsBytes()))];
    // });
    mqttService.subscribe(MqttTopicGenerator.elevators,(topic,publish)->{
        int i=Integer.parseInt(new String(publish.getPayloadAsBytes()));
        b.setElevatorCount(i);
    });
    mqttService.subscribe(MqttTopicGenerator.floors,(topic,publish)->{
        int i=Integer.parseInt(new String(publish.getPayloadAsBytes()));
        b.setFloorCount(i);
    });
    mqttService.subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.btnUp),(topic,publish)->{String[] topics=topic.split("/");
    int floor=Integer.parseInt(topics[topics.length-3]);
        b.setUpButton(floor, new String(publish.getPayloadAsBytes())=="1");
    });
    mqttService.subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.btnDown),(topic,publish)->{String[] topics=topic.split("/");
    int floor=Integer.parseInt(topics[topics.length-3]);
        b.setDownButton(floor, new String(publish.getPayloadAsBytes())=="1");
    });

}
    public void setDirection(int elevator,Direction direction)
    {
        mqttService.publish(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.direction), String.valueOf(direction));
    }
    public void setTargetFloor(int elevator, int floor) {
        mqttService.publish(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.targetFloor), String.valueOf(floor));
    }
    public void setElevatorButton(int elevator,int floor,Boolean value){
        mqttService.publish(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.flPath(floor, MqttTopicGenerator.floorBtn)), String.valueOf(value));
    }
    public void setDownButton(int floor, boolean value) {
        mqttService.publish(MqttTopicGenerator.flPath(floor, MqttTopicGenerator.btnDown), String.valueOf(value));
    
    }public void setUpButton(int floor, boolean value) {
        mqttService.publish(MqttTopicGenerator.flPath(floor, MqttTopicGenerator.btnUp), String.valueOf(value));
    }
}
