package at.fhhagenberg.sqelevatorcontroller;

import at.fhhagenberg.sqelevator.model.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuildingTest {
    
    @Test
    void UpButtonsTest(){
        Building uut=new Building();
        assertEquals(false, uut.getUpButton(0));
        uut.setUpButton(0, true);
        assertEquals(true, uut.getUpButton(0));
        uut.setUpButton(2, true);
        assertEquals(true, uut.getUpButton(2));
        assertEquals(false, uut.getUpButton(1));
        uut.setUpButton(2, false);
        assertEquals(false, uut.getUpButton(2));
    }
    @Test
    void DownButtonsTest(){
        Building uut=new Building();
        assertEquals(false, uut.getDownButton(0));
        uut.setDownButton(0, true);
        assertEquals(true, uut.getDownButton(0));
        uut.setDownButton(2, true);
        assertEquals(true, uut.getDownButton(2));
        assertEquals(false, uut.getDownButton(1));
        uut.setDownButton(2, false);
        assertEquals(false, uut.getDownButton(2));
    }
    @Test
    void DirectionTest(){
        Building uut=new Building();
        assertEquals(Direction.UNCOMMITTED, uut.getDirection(0));
        uut.setDirection(0, Direction.DOWN);
        assertEquals(Direction.DOWN, uut.getDirection(0));
        uut.setDirection(2, Direction.DOWN);
        assertEquals(Direction.DOWN, uut.getDirection(2));
        assertEquals(Direction.UNCOMMITTED, uut.getDirection(1));
        uut.setDirection(2,Direction.UP);
        assertEquals(Direction.UP, uut.getDirection(2));
    }
    @Test
    void currentFloorTest(){
        Building uut=new Building();
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
            uut.getCurrentFloor(2);
        });
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
            uut.setCurrentFloor(2,5);
        });
        uut.setElevatorCount(3);
        assertEquals(0, uut.getCurrentFloor(0));
        uut.setCurrentFloor(0, 5);
        assertEquals(5, uut.getCurrentFloor(0));
        assertEquals(0, uut.getCurrentFloor(2));
        uut.setCurrentFloor(2, 4);
        assertEquals(4, uut.getCurrentFloor(2));
    }
    @Test
    void ElevatorTest(){
        Building uut=new Building();
        assertEquals(0, uut.getElevatorCount());
        uut.setElevatorCount(4);
        assertEquals(4, uut.getElevatorCount());
        uut.setElevatorCount(2);
        assertEquals(2, uut.getElevatorCount());
    }
    @Test
    void FloorTest(){
        Building uut=new Building();
        assertEquals(0, uut.getFloorCount());
        uut.setFloorCount(4);
        assertEquals(4, uut.getFloorCount());
        uut.setFloorCount(2);
        assertEquals(2, uut.getFloorCount());
    }
    @Test
    void SpeedTest(){
        Building uut=new Building();
        assertEquals(0, uut.getSpeed(0));
        uut.setSpeed(0, 4);
        assertEquals(4, uut.getSpeed(0));
        uut.setSpeed(2, 4);
        assertEquals(4, uut.getSpeed(2));
        assertEquals(0, uut.getSpeed(1));
        uut.setSpeed(2, 1);
        assertEquals(1, uut.getSpeed(2));
    }
    @Test
    void ElevatorButtonsTest(){
        Building uut=new Building();
        assertEquals(false, uut.getElevaorButton(0,0));
        uut.setElevatorButton(0,0,true);
        assertEquals(true, uut.getElevaorButton(0, 0));
        uut.setElevatorButton(4,3,true);
        assertEquals(true, uut.getElevaorButton(4, 3));
        assertEquals(false, uut.getElevaorButton(3, 3));
        assertEquals(false, uut.getElevaorButton(4, 2));
        uut.setElevatorButton(4,3,false);
        assertEquals(false, uut.getElevaorButton(4, 3));
    }

    @Test
    void ElevatorRequestTest(){
        Building uut=new Building();
        assertEquals(null, uut.dequeElevatorRequest(0));
        assertEquals(null, uut.dequeElevatorRequest(1));
        uut.enqueElevatorRequest(0, 1);
        uut.enqueElevatorRequest(0, 2);
        uut.enqueElevatorRequest(1, 3);
        uut.enqueElevatorRequest(1, 4);
        assertEquals(1, uut.dequeElevatorRequest(0));
        assertEquals(2, uut.dequeElevatorRequest(0));
        assertEquals(3, uut.dequeElevatorRequest(1));
        assertEquals(4, uut.dequeElevatorRequest(1));
        assertEquals(null, uut.dequeElevatorRequest(0));
        assertEquals(null, uut.dequeElevatorRequest(1));
    }
    @Test
    void FloorRequestTest(){
        Building uut=new Building();
        assertEquals(null, uut.dequeFloorRequest());
        uut.enqueFloorRequest( 1);
        uut.enqueFloorRequest(2);
        assertEquals(1, uut.dequeFloorRequest());
        assertEquals(2, uut.dequeFloorRequest());
        assertEquals(null, uut.dequeFloorRequest());
    }
    
}
