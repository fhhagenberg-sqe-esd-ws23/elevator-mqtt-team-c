package at.fhhagenberg.sqelevatorController;

import at.fhhagenberg.sqelevator.model.Direction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ControllerTest {
    // @Test
    // void ControlleTest(){
    //     Building b = new Building();
    //     MqttConnector c = Mockito.mock(MqttConnector.class);
    //     Controller uut=new Controller(0,b, c);
    //     b.setElevatorCount(1);
    //     b.setFloorCount(5);
    //     b.setCurrentFloor(0, 0);
    //     b.setDirection(0, Direction.UP);
    //     b.setElevatorButton(0, 0, false);
    //     b.setElevatorButton(0, 1, true);
    //     b.setElevatorButton(0, 2, false);
    //     b.setUpButton(0, false);
    //     b.setUpButton(1, false);
    //     b.setUpButton(2, false);
    //     b.setDownButton(0, false);
    //     b.setDownButton(1, false);
    //     b.setDownButton(2, true);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 1);
    //     Mockito.verify(c,Mockito.times(1)).setElevatorButton(0, 1,false);
    //     b.setCurrentFloor(0, 1);
    //     b.setElevatorButton(0, 1, false);

    //     b.setElevatorButton(0, 2, true);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 2);
    //     Mockito.verify(c,Mockito.times(1)).setElevatorButton(0, 2,false);
    //     b.setCurrentFloor(0, 2);
    //     b.setElevatorButton(0, 2, false);

    //     b.setUpButton(4, true);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 4);
    //     Mockito.verify(c,Mockito.times(1)).setUpButton( 4,false);
    //     b.setCurrentFloor(0, 4);
    //     b.setUpButton( 4, false);

    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setDirection(0, Direction.DOWN);
    // }
    // @Test
    // void Controlle2Test(){
    //     Building b = new Building();
    //     MqttConnector c = Mockito.mock(MqttConnector.class);
    //     Controller uut=new Controller(0,b, c);
    //     b.setElevatorCount(1);
    //     b.setFloorCount(5);
    //     b.setCurrentFloor(0, 5);
    //     b.setDirection(0, Direction.DOWN);
    //     b.setElevatorButton(0, 0, false);
    //     b.setElevatorButton(0, 1, false);
    //     b.setElevatorButton(0, 2, false);
    //     b.setElevatorButton(0, 3, true);
    //     b.setUpButton(0, false);
    //     b.setUpButton(1, false);
    //     b.setUpButton(2, true);
    //     b.setDownButton(0, false);
    //     b.setDownButton(1, true);
    //     b.setDownButton(2, false);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 3);
    //     Mockito.verify(c,Mockito.times(1)).setElevatorButton(0, 3,false);
    //     b.setCurrentFloor(0, 3);
    //     b.setElevatorButton(0, 3, false);

    //     // b.setElevatorButton(0, 2, true);
    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setTargetFloor(0, 1);
    //     Mockito.verify(c,Mockito.times(1)).setDownButton(1,false);
    //     b.setCurrentFloor(0, 1);
    //     b.setElevatorButton(0, 1, false);

    //     uut.run();
    //     Mockito.verify(c,Mockito.times(1)).setDirection(0, Direction.UP);

    // }
}
