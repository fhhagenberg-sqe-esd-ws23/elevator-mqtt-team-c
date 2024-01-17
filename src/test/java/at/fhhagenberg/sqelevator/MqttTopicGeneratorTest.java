package at.fhhagenberg.sqelevator;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttTopicGeneratorTest {
    @Test
    void CodeCoverage(){
        Floor f = Mockito.mock(Floor.class);
        Elevator e = Mockito.mock(Elevator.class);
        Mockito.when(e.getElevatorNumber()).thenReturn(5);
        Mockito.when(f.getFloorNumber()).thenReturn(3);
        assertEquals("elevator/5/a", MqttTopicGenerator.elPath(e, "a"));
        assertEquals("elevator/5/a", MqttTopicGenerator.elPath(5, "a"));
        assertEquals("elevator/+/a", MqttTopicGenerator.elPath('+', "a"));
        assertEquals("floor/3/a", MqttTopicGenerator.flPath(f ,"a"));
        assertEquals("floor/3/a", MqttTopicGenerator.flPath(3 ,"a"));
        assertEquals("floor/+/a", MqttTopicGenerator.flPath('+' ,"a"));

    }
    
}
