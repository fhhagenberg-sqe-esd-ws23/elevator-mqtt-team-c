package at.fhhagenberg.sqelevatorcontroller;

import at.fhhagenberg.sqelevator.MqttParser;
import at.fhhagenberg.sqelevator.MqttTopicGenerator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.service.MqttService;

public class MqttConnector {
MqttService mqttService;
    public MqttConnector(MqttService s){
        mqttService=s;
    }
    public void connect( Building b) {
        String t=MqttTopicGenerator.elPath('+', MqttTopicGenerator.flPath('+',MqttTopicGenerator.FLOOR_BTN));
        mqttService.subscribe(t,(topic,publish)->{
            MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
            b.setElevatorButton(x.topics.get(0), x.topics.get(1), new String(publish.getPayloadAsBytes()).equals("true"));
            if(x.value) b.enqueElevatorRequest(x.topics.get(0), x.topics.get(1));
    });
    mqttService.subscribe(MqttTopicGenerator.elPath('+',MqttTopicGenerator.CURRENT_FLOOR),(topic,publish)->{
        MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
            b.setCurrentFloor(x.topics.get(0), x.value);
    });
    mqttService.subscribe(MqttTopicGenerator.ELEVATORS,(topic,publish)->{
        MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
        b.setElevatorCount(x.value);
    });
    mqttService.subscribe(MqttTopicGenerator.FLOORS,(topic,publish)->{
        MqttParser.Ret<Integer,Integer> x=MqttParser.parse(topic, publish, Integer::valueOf, Integer::valueOf);
        b.setFloorCount(x.value);
    });
    mqttService.subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.BTN_UP),(topic,publish)->{
    MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
    b.setUpButton(x.topics.get(0), x.value);
    if(x.value)b.enqueFloorRequest(x.topics.get(0));
    });
    mqttService.subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.BTN_DOWN),(topic,publish)->{

        MqttParser.Ret<Boolean,Integer> x=MqttParser.parse(topic, publish, Boolean::valueOf, Integer::valueOf);
        int num = x.topics.get(0).intValue();
        Boolean value = x.value;
        b.setDownButton(num, value);
        b.enqueFloorRequest(5);
        System.out.println("lel");
    });
    mqttService.subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.DIRECTION),(topic,publish)->{
        MqttParser.Ret<Direction,Integer> x=MqttParser.parse(topic, publish, Direction::valueOf, Integer::valueOf);
        b.setDirection(x.topics.get(0).intValue(), x.value);
    });
    mqttService.subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.DOOR_STATE), (topic,publish)->{
        MqttParser.Ret<DoorStatus,Integer> x=MqttParser.parse(topic, publish, DoorStatus::valueOf, Integer::valueOf);
        b.setElevatorDoor(x.topics.get(0), x.value);
    });

}
    public void setDirection(int elevator,Direction direction)
    {
        mqttService.publish(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.DIRECTION), String.valueOf(direction));
    }
    public void setTargetFloor(int elevator, int floor) {
        mqttService.publish(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.TARGET_FLOOR), String.valueOf(floor));
    }
    public void setElevatorButton(int elevator,int floor,Boolean value){
        mqttService.publish(MqttTopicGenerator.elPath(elevator, MqttTopicGenerator.flPath(floor, MqttTopicGenerator.FLOOR_BTN)), String.valueOf(value));
    }
    public void setDownButton(int floor, boolean value) {
        mqttService.publish(MqttTopicGenerator.flPath(floor, MqttTopicGenerator.BTN_DOWN), String.valueOf(value));
    
    }public void setUpButton(int floor, boolean value) {
        mqttService.publish(MqttTopicGenerator.flPath(floor, MqttTopicGenerator.BTN_UP), String.valueOf(value));
    }
}
