package at.fhhagenberg.sqelevator.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ElevatorTest {
  @Test
  void testInitialization() {
    List<Floor> floors = new ArrayList<>();
    floors.add(mock(Floor.class));
    floors.add(mock(Floor.class));

    Elevator elevator = new Elevator(1, floors);

    assertEquals(1, elevator.getElevatorNumber());
    assertEquals(floors.size(), elevator.getFloors().size());
    assertNull(elevator.getTargetFloorValue());
    assertNull(elevator.getCurrentFloorValue());
    // assertEquals(floors, elevator.getServicesFloors());
    // Add more assertions based on your class structure
  }


  @Test
  void testSetAndGetCommittedDirection() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.addCommittedDirectionListener((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(Direction.UP, b);
    });
    elevator.setCommittedDirectionValue(Direction.UP);
    assertEquals(Direction.UP, elevator.getCommittedDirectionValue());
  }

  // Add more test methods for other functionalities

  @Test
  void testSetAndGetDoorStatus() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.addDoorStatusListener((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(DoorStatus.OPEN, b);
    });
    elevator.setDoorStatusValue(DoorStatus.OPEN);
    assertEquals(DoorStatus.OPEN, elevator.getDoorStatusValue());
  }

  @Test
  void testSetandGetAcceleration() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.addAccelerationListener((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(12, b);
    });
    elevator.setAccelerationValue(12);
    assertEquals(12, elevator.getAccelerationValue());
  }

  @Test
  void testSetandGetWheight() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.addCurrentWeightListener((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(12, b);
    });
    elevator.setCurrentWeightValue(12);
    assertEquals(12, elevator.getCurrentWeightValue());
  }

  @Test
  void testSetandGetTargetfloor() {

    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    // Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
        
    List<Floor> floors = List.of(f1, f2);
    Elevator elevator = new Elevator(1, floors);
    elevator.setFloorsServerdValue(true,1);

    assertNull(elevator.getTargetFloorValue());
    elevator.setTargetFloorValue(f2);
    assertEquals(f2, elevator.getTargetFloorValue());
  }

  @Test
  void testSetandGetTargetfloorEmpty() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    assertNull(elevator.getTargetFloorValue());
  }

  @Test
  void testSetandGetTargetfloorForeign() {
    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
    Elevator elevator = new Elevator(1, List.of(f1));
    elevator.setFloorsServerdValue(true,0);
    elevator.setTargetFloorValue(f1);
    elevator.setTargetFloorValue(f2);
    assertEquals(f1, elevator.getTargetFloorValue());
  }

  @Test
  void testSetandGetCurrentFloor() {

    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    // Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
    List<Floor> floors = List.of(f1, f2);
    Elevator elevator = new Elevator(1, floors);
    elevator.setFloorsServerdValue(true,0);
    elevator.setFloorsServerdValue(true,1);
    elevator.addFloorsServerdListener((a,b,c)->{
      assertEquals(elevator, a);
      assertEquals(1, b);
      assertTrue(c);
    });
    assertNull(elevator.getCurrentFloorValue());
    elevator.setCurrentFloorValue(f2);
    assertEquals(f2, elevator.getCurrentFloorValue());
  }

  @Test
  void testSetandGetCurrentFloorEmpty() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    assertNull(elevator.getCurrentFloorValue());
  }

  @Test
  void testSetandGetCurrentFloorForeign() {
    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
    Elevator elevator = new Elevator(1, List.of(f1));
    elevator.setFloorsServerdValue(true, 0);
    elevator.setCurrentFloorValue(f1);
    elevator.setCurrentFloorValue(f2);
    assertEquals(f1, elevator.getCurrentFloorValue());
  }
}
