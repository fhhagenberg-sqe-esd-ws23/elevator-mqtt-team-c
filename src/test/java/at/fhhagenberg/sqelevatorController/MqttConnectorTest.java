package at.fhhagenberg.sqelevatorController;


import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.MqttService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class MqttConnectorTest {

    @Mock
    MqttService s;
    @Mock
    Building b;
    @Test
    void Test1() throws Exception
    {
        MqttConnector uut=new MqttConnector(Mockito.mock(MqttService.class));
        uut.connect(b);
        uut.setDirection(0, Direction.DOWN);
        uut.setTargetFloor(0, 0);

    }
    
}
