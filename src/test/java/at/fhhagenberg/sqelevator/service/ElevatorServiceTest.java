package at.fhhagenberg.sqelevator.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdater;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;

@ExtendWith(MockitoExtension.class)
class ElevatorServiceTest {
  @Mock
  private IElevator controller;
  @Mock
  private BuildingUpdater buildingUpdater;
  @Mock
  private IUpdater floorUpdater;
  @Mock
  private IUpdater elevatorUpdater;
  private List<IUpdater> updaters;
  private ElevatorService elevatorService;

  @BeforeEach
  public void setUp() {
    updaters = new ArrayList<>();
    updaters.add(floorUpdater);
    updaters.add(elevatorUpdater);

    elevatorService = Mockito.spy(new ElevatorService(controller, buildingUpdater, updaters));
  }


  @Test
  void testUpdateWithBuildingChange() throws RemoteException {
    Building building = Mockito.mock(Building.class);
    List<Floor> floors = List.of(mock(Floor.class));
    List<Elevator> elevators = List.of(mock(Elevator.class));

    Mockito.when(building.getFloors()).thenReturn(floors);
    Mockito.when(building.getElevators()).thenReturn(elevators);
    Mockito.when(buildingUpdater.update()).thenReturn(true);

    elevatorService.update(building);

    Assertions.assertEquals(floors.size() + elevators.size(), updaters.size());
  }

  @Test
  void testUpdateWithNoBuildingChange() throws RemoteException {
    Building building = Mockito.mock(Building.class);
    Mockito.when(buildingUpdater.update()).thenReturn(false);

    elevatorService.update(building);

    for (IUpdater updater : updaters) {
      Mockito.verify(updater, Mockito.times(1)).update();
    }
  }

  @Test
  void testUpdateWithRemoteException() throws RemoteException {
    Building building = Mockito.mock(Building.class);

    Mockito.doThrow(new RemoteException()).when(floorUpdater).update();

    Assertions.assertThrows(RemoteException.class, () -> elevatorService.update(building));

    Mockito.verify(floorUpdater, times(1)).update();
    Mockito.verify(elevatorUpdater, Mockito.never()).update();
  }

//  @Test
//  void testGeneral() throws RemoteException{
//    Mockito.when(controller.getFloorNum()).thenReturn(3);
//    Mockito.when(controller.getElevatorNum()).thenReturn(2);
//    Mockito.when(controller.getCommittedDirection(0)).thenReturn(0);
//    Mockito.when(controller.getCommittedDirection(1)).thenReturn(1);
//    Mockito.when(controller.getElevatorAccel(0)).thenReturn(12);
//    Mockito.when(controller.getElevatorButton(0, 0)).thenReturn(false);
//    Mockito.when(controller.getElevatorButton(0, 1)).thenReturn(true);
//    Mockito.when(controller.getElevatorButton(0, 2)).thenReturn(false);
//    Mockito.when(controller.getServicesFloors(anyInt(), anyInt())).thenReturn(true);
//    elevatorService.update(building);
//    assertEquals(3, building.getFloors().size());
//    assertEquals(2, building.getElevators().size());
//    assertEquals(3, building.getElevator(0).getServedButtons().size());
//    assertEquals(Direction.UP, building.getElevator(0).getCommittedDirection());
//    assertEquals(Direction.DOWN, building.getElevator(1).getCommittedDirection());
//    assertEquals(12,building.getElevator(0).getAcceleration());
//    assertEquals(0,building.getElevator(0).getAllElevatorButtons().get(0).getFloor().getFloorNumber());
//    assertFalse(building.getElevator(0).getAllElevatorButtons().get(0).isPressed());
//    assertTrue(building.getElevator(0).getAllElevatorButtons().get(1).isPressed());
//    assertFalse(building.getElevator(0).getAllElevatorButtons().get(2).isPressed());
//
//    Mockito.when(controller.getCommittedDirection(0)).thenReturn(2);
//    Mockito.when(controller.getCommittedDirection(1)).thenReturn(0);
//
//    elevatorService.update(building);
//    assertEquals(3, building.getFloors().size());
//    assertEquals(2, building.getElevators().size());
//    assertEquals(Direction.UNCOMMITTED, building.getElevator(0).getCommittedDirection());
//    assertEquals(Direction.UP, building.getElevator(1).getCommittedDirection());
//
//  }
}
