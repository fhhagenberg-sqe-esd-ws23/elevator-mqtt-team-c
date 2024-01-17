package at.fhhagenberg.sqelevatorController;

import at.fhhagenberg.sqelevator.MqttParser;
import at.fhhagenberg.sqelevator.MqttTopicGenerator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.service.MqttService;

public class MqttConnector {
MqttService mqttService;
    MqttConnector(MqttService s){
        mqttService=s;
    }
    public void connect( Building b) throws Exception {
        // TODO Auto-generated method stub 
        String t=MqttTopicGenerator.elPath('+', MqttTopicGenerator.flPath('+',MqttTopicGenerator.floorBtn));
        mqttService.subscribe(t,(topic,publish)->{
            MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
            b.setElevatorButton(x.topics[0].intValue(), x.topics[1].intValue(), new String(publish.getPayloadAsBytes())=="true");
            if(x.value) b.enqueElevatorRequest(x.topics[0].intValue(), x.topics[1].intValue());
    });
    mqttService.subscribe(MqttTopicGenerator.elPath('+',MqttTopicGenerator.currentFloor),(topic,publish)->{
        MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
            b.setCurrentFloor(x.topics[0], x.value);
    });
    // mqttService.subscribe(MqttTopicGenerator.elPath('+',MqttTopicGenerator.doorStatus),(topic,publish)->{String[] topics=publish.getTopic().toString().split("/");
    //         int floor=Integer.parseInt(topics[topics.length-2]);
    //         int door=DoorStatus.values()[Integer.parseInt(new String(publish.getPayloadAsBytes()))];
    // });
    mqttService.subscribe(MqttTopicGenerator.elevators,(topic,publish)->{
        MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
        b.setElevatorCount(x.value);
    });
    mqttService.subscribe(MqttTopicGenerator.floors,(topic,publish)->{
        MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
        b.setFloorCount(x.value);
    });
    mqttService.subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.btnUp),(topic,publish)->{String[] topics=publish.getTopic().toString().split("/");
    MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
    b.setUpButton(x.topics[0], x.value);
    if(x.value)b.enqueFloorRequest(x.topics[0]);
    });
    mqttService.subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.btnDown),(topic,publish)->{String[] topics=publish.getTopic().toString().split("/");
    MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
        b.setDownButton(x.topics[0], x.value);
        b.enqueFloorRequest(x.topics[0]);
    });
    mqttService.subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.direction),(topic,publish)->{
        MqttParser.Ret<Direction,Integer> x=MqttParser.parse(topic, publish, Direction::valueOf, Integer::valueOf);
        b.setDirection(x.topics[0].intValue(), x.value);
    });
    mqttService.subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.doorStatus), (topic,publish)->{
        MqttParser.Ret<DoorStatus,Integer> x=MqttParser.parse(topic, publish, DoorStatus::valueOf, Integer::valueOf)
        b.setElevatorDoor(x.topics[0], x.value);
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
