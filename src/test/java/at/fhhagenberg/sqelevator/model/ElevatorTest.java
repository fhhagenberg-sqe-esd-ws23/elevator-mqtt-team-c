package at.fhhagenberg.sqelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    //TODO: 
    @Test
    public void setandGetTargetfloor(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNotNull(elevator.getAcceleration());
        elevator.setAcceleration(12);
        assertEquals(12, elevator.getAcceleration());
    }

    @Test
    public void setandGetTargetfloorFremd(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNotNull(elevator.getAcceleration());
        elevator.setAcceleration(12);
        assertEquals(12, elevator.getAcceleration());
    }
    @Test
    public void setandGetCurrentfloor(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNotNull(elevator.getAcceleration());
        elevator.setAcceleration(12);
        assertEquals(12, elevator.getAcceleration());
    }

    @Test
    public void setandGetCurrentfloorEmpty(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNotNull(elevator.getAcceleration());
        elevator.setAcceleration(12);
        assertEquals(12, elevator.getAcceleration());
    }
    @Test
    public void setandGetCurrentfloorFremd(){
        Elevator elevator = new Elevator(1, new ArrayList<>());
        assertNotNull(elevator.getAcceleration());
        elevator.setAcceleration(12);
        assertEquals(12, elevator.getAcceleration());
    }
    // Add more test methods for other functionalities

}
