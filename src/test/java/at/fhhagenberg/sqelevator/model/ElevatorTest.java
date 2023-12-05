package at.fhhagenberg.sqelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    assertEquals(floors.size(), elevator.getAllElevatorButtons().size());
    assertNull(elevator.getTargetFloor());
    assertNull(elevator.getCurrentFloor());
    // assertEquals(floors, elevator.getServicesFloors());
    // Add more assertions based on your class structure
  }


  @Test
  void testSetAndGetCommittedDirection() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.commitedDirectionListener=(a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(Direction.UP, b);
    };
    elevator.setCommittedDirection(Direction.UP);
    assertEquals(Direction.UP, elevator.getCommittedDirection());
  }

  // Add more test methods for other functionalities

  @Test
  void testSetAndGetDoorStatus() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.doorStatusListener=(a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(DoorStatus.OPEN, b);
    };
    elevator.setDoorStatus(DoorStatus.OPEN);
    assertEquals(DoorStatus.OPEN, elevator.getDoorStatus());
  }

  @Test
  void testSetandGetAcceleration() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.accelListener=(a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(12, b);
    };
    elevator.setAcceleration(12);
    assertEquals(12, elevator.getAcceleration());
  }

  @Test
  void testSetandGetWheight() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    elevator.weightListner=(a,b)->
    {
      assertEquals(elevator, a);
      assertEquals(12, b);
    };
    elevator.setCurrentWeight(12);
    assertEquals(12, elevator.getCurrentWeight());
  }

  @Test
  void testSetandGetTargetfloor() {

    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    List<Floor> floors = List.of(f1, f2);
    Elevator elevator = new Elevator(1, floors);
    elevator.getButton(f2).setServed(true);
    elevator.getButton(f2).serverListner=(a,b)->{
      assertEquals(elevator.getButton(f2), a); 
      assertEquals(true, b);
    };
    assertNull(elevator.getTargetFloor());
    elevator.setTargetFloor(f2);
    assertEquals(f2, elevator.getTargetFloor());
  }

  @Test
  void testSetandGetTargetfloorEmpty() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    assertNull(elevator.getTargetFloor());
  }

  // @Test
  // void testSetandGetTargetfloorForeign() {
  //   Floor f1 = mock(Floor.class);
  //   Floor f2 = mock(Floor.class);
  //   Elevator elevator = new Elevator(1, List.of(f1));
  //   elevator.addServedFloor(f1);
  //   elevator.setTargetFloor(f1);
  //   elevator.setTargetFloor(f2);
  //   assertEquals(f1, elevator.getTargetFloor());
  // }

  @Test
  void testSetandGetCurrentFloor() {

    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    List<Floor> floors = List.of(f1, f2);
    Elevator elevator = new Elevator(1, floors);
    elevator.getButton(f1).setServed(true);
    elevator.getButton(f2).setServed(true);
    elevator.getButton(f2).serverListner=(a,b)->{
      assertEquals(elevator.getButton(f2), a); 
      assertEquals(true, b);
    };
    assertNull(elevator.getCurrentFloor());
    elevator.setCurrentFloor(f2);
    assertEquals(f2, elevator.getCurrentFloor());
  }

  @Test
  void testSetandGetCurrentFloorEmpty() {
    Elevator elevator = new Elevator(1, new ArrayList<>());
    assertNull(elevator.getCurrentFloor());
  }

  @Test
  void testSetandGetCurrentFloorForeign() {
    Floor f1 = mock(Floor.class);
    Floor f2 = mock(Floor.class);
    Elevator elevator = new Elevator(1, List.of(f1));
    elevator.getButton(f1).setServed(true);
    elevator.getButton(f2).serverListner=(a,b)->{
      assertEquals(elevator.getButton(f2), a); 
      assertEquals(true, b);
    };
    elevator.setCurrentFloor(f1);
    elevator.setCurrentFloor(f2);
    assertEquals(f1, elevator.getCurrentFloor());
  }
}
