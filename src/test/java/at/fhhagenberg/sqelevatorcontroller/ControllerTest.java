package at.fhhagenberg.sqelevatorcontroller;

import at.fhhagenberg.sqelevator.model.Direction;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ControllerTest {
    @Test
    void ControlleTest(){
        Building b = new Building();
        MqttConnector c = Mockito.mock(MqttConnector.class);
        Controller uut=new Controller(0,b, c);
        b.setElevatorCount(1);
        b.setFloorCount(5);
        b.setCurrentFloor(0, 0);
        b.setDirection(0, Direction.UP);
        b.enqueFloorRequest(3);
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 5);
        b.setCurrentFloor(0, 5);
        b.enqueFloorRequest(2);
        b.enqueElevatorRequest(0, 4);
        
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 4);
        b.setCurrentFloor(0, 4);
        
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 2);
    }
    @Test
    void Controlle2Test(){
        Building b = new Building();
        MqttConnector c = Mockito.mock(MqttConnector.class);
        Controller uut=new Controller(0,b, c);
        b.setElevatorCount(1);
        b.setFloorCount(5);
        b.setCurrentFloor(0, 5);
        b.setDirection(0, Direction.DOWN);
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 3);
        b.setCurrentFloor(0, 3);
        b.setElevatorButton(0, 3, false);

        // b.setElevatorButton(0, 2, true);
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 1);
        b.setCurrentFloor(0, 1);
        b.setElevatorButton(0, 1, false);

        uut.run();
        Mockito.verify(c,Mockito.times(1)).setDirection(0, Direction.UP);

    }
}
