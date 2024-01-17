package at.fhhagenberg.sqelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuildingTest {
  @Test
  void testCtor_Empty() {
    var uut = new Building();
    assertEquals(0, uut.getElevatorNum());
    assertEquals(0, uut.getFloorNum());
  }

  @Test
  void testCtor_List() {
    List<Elevator> elevators = List.of(Mockito.mock(Elevator.class));
    List<Floor> floors = List.of(Mockito.mock(Floor.class));

    var uut = new Building(elevators, floors);
    assertEquals(elevators, uut.getElevators());
    assertEquals(floors, uut.getFloors());
  }

  @Test
  void testAddElevatorFloor() {
    var uut = new Building();
    var floors = List.of(new Floor(0), new Floor(1));
    uut.setElevators(List.of(new Elevator(0, floors)));
    uut.setFloors(floors);
    assertEquals(1, uut.getElevatorNum());
    assertEquals(2, uut.getFloorNum());
  }

  @Test
  void testGetElevator_valid() {
    var uut = new Building();
    List<Floor> floors = new ArrayList<>();
    var e1 = new Elevator(0, floors);
    var elevators = List.of(new Elevator(0, floors), e1);
    uut.setElevators(elevators);
    assertEquals(e1, uut.getElevator(1));

  }

  @Test
  void testGetFloor_valid() {
    var uut = new Building();
    var f1 = new Floor(1);
    List<Floor> floors = List.of(new Floor(2), f1, new Floor(2));
    uut.setFloors(floors);
    assertEquals(f1, uut.getFloor(1));
  }

  @Test
  void testGetElevatorsFloors() {
    var uut = new Building();
    var floors = List.of(new Floor(0), new Floor(1));
    var elevators = List.of(new Elevator(0, floors));
    uut.setElevators(elevators);
    uut.setFloors(floors);
    assertEquals(elevators, uut.getElevators());
    assertEquals(floors, uut.getFloors());
  }

  @Test
  void testGetElevator_invalid() {
    var uut = new Building();
    List<Floor> floors = new ArrayList<>();
    var e1 = new Elevator(0, floors);
    var elevators = List.of(new Elevator(0, floors), e1);
    uut.setElevators(elevators);
    assertNull(uut.getElevator(3));
  }

  @Test
  void testGetFloor_invalid() {
    var uut = new Building();
    var f1 = new Floor(1);
    List<Floor> floors = List.of(new Floor(2), f1, new Floor(2));
    uut.setFloors(floors);
    assertNull(uut.getFloor(3));
  }

  @Test
  void testGetElevator_negative() {
    var uut = new Building();
    List<Floor> floors = new ArrayList<>();
    var e1 = new Elevator(0, floors);
    var elevators = List.of(new Elevator(0, floors), e1);
    uut.setElevators(elevators);
    assertNull(uut.getElevator(-1));
  }

  @Test
  void testGetFloor_negative() {
    var uut = new Building();
    var f1 = new Floor(1);
    List<Floor> floors = List.of(new Floor(2), f1, new Floor(2));
    uut.setFloors(floors);
    assertNull(uut.getFloor(-1));
  }

  @Test
  void testElevatorCount(){
    var uut = new Building();
    uut.addElevatorCountListner((a,b)->{
      assertEquals(uut, a);
      assertEquals(3, b);
    });
    uut.setElevators(List.of(Mockito.mock(Elevator.class),Mockito.mock(Elevator.class),Mockito.mock(Elevator.class)));
    assertEquals(3, uut.getElevatorNum());
  }
  @Test
  void testFloorCount(){
    var uut = new Building();
    uut.addFloorCountListner((a,b)->{
      assertEquals(uut, a);
      assertEquals(3, b);
    });
    uut.setFloors(List.of(Mockito.mock(Floor.class),Mockito.mock(Floor.class),Mockito.mock(Floor.class)));
    assertEquals(3, uut.getFloorNum());
  }
}
