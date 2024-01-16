package at.fhhagenberg.sqelevatorController;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.MqttService;

public class MqttConnectorTest {

    @Mock
    MqttService s;
    @Mock
    Building b;
    @Test
    public void Test1() throws Exception
    {
        MqttConnector uut=new MqttConnector(Mockito.mock(MqttService.class));
        uut.connect(b);
        uut.setDirection(0, Direction.DOWN);
        uut.setTargetFloor(0, 0);

    }
    
}
