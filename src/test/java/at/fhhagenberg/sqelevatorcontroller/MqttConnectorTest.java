package at.fhhagenberg.sqelevatorcontroller;


import at.fhhagenberg.sqelevator.MqttTopicGenerator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.MqttService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class MqttConnectorTest {

    @Mock
    MqttService s;
    @Mock
    Building b;
    @Test
    void ConnectTest() throws Exception
    {
        MqttConnector uut=new MqttConnector(s);
        uut.connect(b);
        verify(s,times(1)).subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.flPath('+',MqttTopicGenerator.FLOOR_BTN)), Mockito.any());
        verify(s,times(1)).subscribe(MqttTopicGenerator.elPath('+',MqttTopicGenerator.CURRENT_FLOOR), Mockito.any());
        verify(s,times(1)).subscribe(MqttTopicGenerator.ELEVATORS, Mockito.any());
        verify(s,times(1)).subscribe(MqttTopicGenerator.FLOORS, Mockito.any());
        verify(s,times(1)).subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.BTN_UP), Mockito.any());
        verify(s,times(1)).subscribe(MqttTopicGenerator.flPath('+',MqttTopicGenerator.BTN_DOWN), Mockito.any());
        verify(s,times(1)).subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.DIRECTION), Mockito.any());
        verify(s,times(1)).subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.DOOR_STATE), Mockito.any());
        uut.setDirection(0, Direction.DOWN);
        uut.setTargetFloor(0, 0);
        uut.setElevatorButton(0, 0, false);
        uut.setUpButton(0, false);

    }


    @Test
    void setDirectionTest() throws Exception
    {
        MqttConnector uut=new MqttConnector(s);
        
        uut.setDirection(0, Direction.DOWN);
        verify(s,times(1)).publish(MqttTopicGenerator.elPath(0, MqttTopicGenerator.DIRECTION), Direction.DOWN.toString());
        uut.setTargetFloor(0, 0);
        verify(s,times(1)).publish(MqttTopicGenerator.elPath(0, MqttTopicGenerator.TARGET_FLOOR), 0);
        uut.setElevatorButton(0, 0, false);
        verify(s,times(1)).publish(MqttTopicGenerator.elPath(0, MqttTopicGenerator.flPath(0, MqttTopicGenerator.FLOOR_BTN)), false);
        uut.setUpButton(0, false);
        verify(s,times(1)).publish(MqttTopicGenerator.flPath(0, MqttTopicGenerator.BTN_UP), false);
        uut.setDownButton(0, false);
        verify(s,times(1)).publish(MqttTopicGenerator.flPath(0, MqttTopicGenerator.BTN_DOWN), false);
        

    }
    
    
}
