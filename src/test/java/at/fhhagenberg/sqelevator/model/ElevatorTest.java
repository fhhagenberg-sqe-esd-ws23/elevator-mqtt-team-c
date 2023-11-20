package at.fhhagenberg.sqelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElevatorTest {
    @Test
    public void testInitialization() {
        List<Floor> floors = new ArrayList<>();
        floors.add(mock(Floor.class));
        floors.add(mock(Floor.class));

        Elevator elevator = new Elevator(1, floors);

        assertEquals(1, elevator.getElevatorNumber());
        assertEquals(floors.size(), elevator.getAllElevatorButtons().size());
        assertEquals(floors.get(0),elevator.getTargetFloor());
        assertEquals(floors.get(0),elevator.getCurrentFloor());
        assertEquals(floors, elevator.getServicesFloors());
        // Add more assertions based on your class structure
    }

    @Test
    public void testUpdateFloors() {
        List<Floor> floors = new ArrayList<>();
        floors.add(mock(Floor.class));
        floors.add(mock(Floor.class));

        Elevator elevator = new Elevator(1, floors);

        List<Floor> newFloors = new ArrayList<>();
        newFloors.add(mock(Floor.class));
        newFloors.add(mock(Floor.class));

        elevator.updateFloors(newFloors);

        assertEquals(newFloors.size(), elevator.getAllElevatorButtons().size());
        // Add more assertions based on your class structure
    }

    @Test
    public void testSetAndGetCommittedDirection() {
        Elevator elevator = new Elevator(1, new ArrayList<>());

        elevator.setCommittedDirection(Direction.UP);
        assertEquals(Direction.UP, elevator.getCommittedDirection());
    }

    // Add more test methods for other functionalities

    @Test
    public void testAddAndRemoveServedFloor() {
        Elevator elevator = new Elevator(1, new ArrayList<>());

        Floor floor = mock(Floor.class);
        elevator.addServedFloor(floor);

        assertEquals(1, elevator.getServicesFloors().size());

        boolean removed = elevator.removeServedFloor(floor);
        assertTrue(removed);
        assertEquals(0, elevator.getServicesFloors().size());
    }

    @Test
    public void testAddAndRemoveServedFloorButton() {
        Elevator elevator = new Elevator(1, new ArrayList<>());

        ElevatorButton button = new ElevatorButton(mock(Floor.class));
        elevator.addServedFloorButton(button);

        assertEquals(1, elevator.getServedButtons().size());

        boolean removed = elevator.removeServedFloorButton(button);
        assertTrue(removed);
        assertEquals(0, elevator.getServedButtons().size());
    }

    @Test
    public void testSetAndGetDoorStatus() {
        Elevator elevator = new Elevator(1, new ArrayList<>());

        elevator.setDoorStatus(DoorStatus.OPEN);
        assertEquals(DoorStatus.OPEN, elevator.getDoorStatus());
    }

    @Test
    public void setandGetAcceleration(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNotNull(elevator.getAcceleration());
        elevator.setAcceleration(12);
        assertEquals(12, elevator.getAcceleration());
    }
    @Test
    public void setandGetWheight(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNotNull(elevator.getCurrentWeight());
        elevator.setCurrentWeight(12);
        assertEquals(12, elevator.getCurrentWeight());
    }
    @Test
    public void setandGetTargetfloor(){

        Floor f1=mock(Floor.class);
        Floor f2=mock(Floor.class);
        List<Floor> floors = List.of(f1,f2);
        Elevator elevator = new Elevator(1, floors);
        assertEquals(f1,elevator.getTargetFloor());
        elevator.setTargetFloor(f2);
        assertEquals(f2, elevator.getTargetFloor());
    }

    @Test
    public void setandGetTargetfloorEmpty(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNull(elevator.getAcceleration());
    }
    @Test
    public void setandGetTargetfloorFremd(){
        Floor f1=mock(Floor.class);
        Floor f2=mock(Floor.class);
        Elevator elevator = new Elevator(1,List.of(f1));
        elevator.setTargetFloor(f2);
        assertEquals(f1,elevator.getTargetFloor());
    }

    @Test
    public void setandGetCurrentFloor(){

        Floor f1=mock(Floor.class);
        Floor f2=mock(Floor.class);
        List<Floor> floors = List.of(f1,f2);
        Elevator elevator = new Elevator(1, floors);
        assertEquals(f1,elevator.getCurrentFloor());
        elevator.setCurrentFloor(f2);
        assertEquals(f2, elevator.getCurrentFloor());
    }

    @Test
    public void setandGetCurrentFloorEmpty(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNull(elevator.getAcceleration());
    }
    @Test
    public void setandGetCurrentFloorFremd(){
        Floor f1=mock(Floor.class);
        Floor f2=mock(Floor.class);
        Elevator elevator = new Elevator(1,List.of(f1));
        elevator.setCurrentFloor(f2);
        assertEquals(f1,elevator.getCurrentFloor());
    }
    
    // Add more test methods for other functionalities

}
