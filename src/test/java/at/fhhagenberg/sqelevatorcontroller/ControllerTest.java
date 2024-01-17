package at.fhhagenberg.sqelevatorcontroller;

import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;

import static org.mockito.ArgumentMatchers.any;

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
        b.setElevatorDoor(0, DoorStatus.OPEN);
        b.setSpeed(0, 0);
        b.setDirection(0, Direction.UP);
        b.enqueFloorRequest(3);
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 3);
        Mockito.verify(c,Mockito.times(1)).setDirection(0,Direction.UP );
        b.setCurrentFloor(0, 5);
        b.enqueFloorRequest(2);
        b.enqueElevatorRequest(0, 4);
        
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 4);
        Mockito.verify(c,Mockito.times(1)).setDirection(0,Direction.DOWN );
        b.setCurrentFloor(0, 4);
        
        uut.run();
        Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 2);
        Mockito.verify(c,Mockito.times(2)).setDirection(0,Direction.DOWN );
    }
    // @Test
    // void Controlle2Test(){
    //     Building b = new Building();
    //     MqttConnector c = Mockito.mock(MqttConnector.class);
    //     Controller uut=new Controller(0,b, c);
    //     b.setElevatorCount(1);
    //     b.setFloorCount(5);
    //     b.setElevatorDoor(0, DoorStatus.CLOSED);
    //     b.setSpeed(0, 0);
    //     b.setCurrentFloor(0, 5);
    //     b.setDirection(0, Direction.DOWN);
    //     Mockito.verify(c,Mockito.times(0)).setTargetFloor(any(), any());
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(0)).setTargetFloor(any(), any());

    //     b.enqueFloorRequest(1);
    //     b.setSpeed(0, 5);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(0)).setTargetFloor(any(), any());

    //     b.setSpeed(0, 0);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 5);
    //     b.enqueElevatorRequest(0, 1);
    //     b.enqueElevatorRequest(0, 5);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 1);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 5);
    //     Mockito.verify(c,Mockito.times(1)).setDirection(0, Direction.UP);

    // }
}
