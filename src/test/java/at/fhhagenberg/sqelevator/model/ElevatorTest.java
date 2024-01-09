package at.fhhagenberg.sqelevator.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    assertEquals(floors.size(), elevator.Floors.size());
    assertNull(elevator.targetFloor.get());
    assertNull(elevator.currentFloor.get());
    // assertEquals(floors, elevator.getServicesFloors());
    // Add more assertions based on your class structure
  }


  @Test
  void testSetAndGetCommittedDirection() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.committedDirection.addListner((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(Direction.UP, b);
    });
    elevator.committedDirection.set(Direction.UP);
    assertEquals(Direction.UP, elevator.committedDirection.get());
  }

  // Add more test methods for other functionalities

  @Test
  void testSetAndGetDoorStatus() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.doorStatus.addListner((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(DoorStatus.OPEN, b);
    });
    elevator.doorStatus.set(DoorStatus.OPEN);
    assertEquals(DoorStatus.OPEN, elevator.doorStatus.get());
  }

  @Test
  void testSetandGetAcceleration() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.acceleration.addListner((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(12, b);
    });
    elevator.acceleration.set(12);
    assertEquals(12, elevator.acceleration.get());
  }

  @Test
  void testSetandGetWheight() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.currentWeight.addListner((a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(12, b);
    });
    elevator.currentWeight.set(12);
    assertEquals(12, elevator.currentWeight.get());
  }

  @Test
  void testSetandGetTargetfloor() {

    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    // Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
        
    List<Floor> floors = List.of(f1, f2);
    Elevator elevator = new Elevator(1, floors);
    elevator.floorsServerd.set(true,1);

    assertNull(elevator.targetFloor.get());
    elevator.targetFloor.set(f2);
    assertEquals(f2, elevator.targetFloor.get());
  }

  @Test
  void testSetandGetTargetfloorEmpty() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    assertNull(elevator.targetFloor.get());
  }

  @Test
  void testSetandGetTargetfloorForeign() {
    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
    Elevator elevator = new Elevator(1, List.of(f1));
    elevator.floorsServerd.set(true,0);
    elevator.targetFloor.set(f1);
    elevator.targetFloor.set(f2);
    assertEquals(f1, elevator.targetFloor.get());
  }

  @Test
  void testSetandGetCurrentFloor() {

    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    // Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
    List<Floor> floors = List.of(f1, f2);
    Elevator elevator = new Elevator(1, floors);
    elevator.floorsServerd.set(true,0);
    elevator.floorsServerd.set(true,1);
    elevator.floorsServerd.addListner((a,b,c)->{
      assertEquals(elevator, a);
      assertEquals(1, b);
      assertTrue(c);
    });
    assertNull(elevator.currentFloor.get());
    elevator.currentFloor.set(f2);
    assertEquals(f2, elevator.currentFloor.get());
  }

  @Test
  void testSetandGetCurrentFloorEmpty() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    assertNull(elevator.currentFloor.get());
  }

  @Test
  void testSetandGetCurrentFloorForeign() {
    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    Mockito.when(f1.getFloorNumber()).thenReturn(0);
    Mockito.when(f2.getFloorNumber()).thenReturn(1);
    Elevator elevator = new Elevator(1, List.of(f1));
    elevator.floorsServerd.set(true, 0);
    elevator.currentFloor.set(f1);
    elevator.currentFloor.set(f2);
    assertEquals(f1, elevator.currentFloor.get());
  }
}
