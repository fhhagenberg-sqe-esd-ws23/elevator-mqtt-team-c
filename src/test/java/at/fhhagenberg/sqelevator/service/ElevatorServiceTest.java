package at.fhhagenberg.sqelevator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.update.IUpdater;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;

@ExtendWith(MockitoExtension.class)
class ElevatorServiceTest {
  private ElevatorService elevatorService;
  @Mock
  private IElevator controller;
  @Spy
  private List<IUpdater> updaters = new ArrayList<>();

  private Building building;

  @BeforeEach
  public void setUp() {
    building = new Building();
    //  @Mock
    BuildingUpdater buildingUpdater = new BuildingUpdater(controller, building);
    elevatorService = Mockito.spy(new ElevatorService(controller, buildingUpdater, updaters));
  }

  @Test
  void testCreateBuilding() throws RemoteException {
    Mockito.when(controller.getFloorNum()).thenReturn(10);
    Mockito.when(controller.getElevatorNum()).thenReturn(3);

//    elevatorService.initUpdaters(building);
    elevatorService.update(building);

    Assertions.assertEquals(10, building.getFloorNum());
    Assertions.assertEquals(3, building.getElevatorNum());
  }

  @Test
  void testChangeBuildingSize() throws RemoteException {
    Mockito.when(controller.getFloorNum())
        .thenReturn(3)
        .thenReturn(2);
    Mockito.when(controller.getElevatorNum())
        .thenReturn(2)
        .thenReturn(1);

//    elevatorService.initUpdaters(building);
    elevatorService.update(building);

    Assertions.assertEquals(3, building.getFloorNum());
    Assertions.assertEquals(2, building.getElevatorNum());

    elevatorService.update(building);

    Assertions.assertEquals(2, building.getFloorNum());
    Assertions.assertEquals(1, building.getElevatorNum());
  }

  // @Test
  // void testFirstToSecondFloor() throws RemoteException {
  //   Mockito.when(controller.getFloorNum())
  //       .thenReturn(2);
  //   Mockito.when(controller.getElevatorNum())
  //       .thenReturn(1);
  //   Mockito.when(controller.getServicesFloors(0, 0))
  //       .thenReturn(true);
  //   Mockito.when(controller.getServicesFloors(0, 1))
  //       .thenReturn(true);
  //   Mockito.when(controller.getElevatorFloor(0))
  //       .thenReturn(0);

  //   elevatorService.update(building);

  //   Assertions.assertEquals(3, building.getFloorNum());
  //   Assertions.assertEquals(2, building.getElevatorNum());
  // }

  @Test
  void testGeneral() throws RemoteException{
    Mockito.when(controller.getFloorNum()).thenReturn(3);
    Mockito.when(controller.getElevatorNum()).thenReturn(2);
    Mockito.when(controller.getCommittedDirection(0)).thenReturn(0);
    Mockito.when(controller.getCommittedDirection(1)).thenReturn(1);
    Mockito.when(controller.getElevatorAccel(0)).thenReturn(12);
    Mockito.when(controller.getElevatorButton(0, 0)).thenReturn(false);
    Mockito.when(controller.getElevatorButton(0, 1)).thenReturn(true);
    Mockito.when(controller.getElevatorButton(0, 2)).thenReturn(false);
    Mockito.when(controller.getServicesFloors(anyInt(), anyInt())).thenReturn(true);
    elevatorService.update(building);
    assertEquals(3, building.getFloors().size());
    assertEquals(2, building.getElevators().size());
    assertEquals(3, building.getElevator(0).getServedButtons().size());
    assertEquals(Direction.UP, building.getElevator(0).getCommittedDirection());
    assertEquals(Direction.DOWN, building.getElevator(1).getCommittedDirection());
    assertEquals(12,building.getElevator(0).getAcceleration());
    assertEquals(0,building.getElevator(0).getAllElevatorButtons().get(0).getFloor().getFloorNumber());
    assertFalse(building.getElevator(0).getAllElevatorButtons().get(0).isPressed());
    assertTrue(building.getElevator(0).getAllElevatorButtons().get(1).isPressed());
    assertFalse(building.getElevator(0).getAllElevatorButtons().get(2).isPressed());

    Mockito.when(controller.getCommittedDirection(0)).thenReturn(2);
    Mockito.when(controller.getCommittedDirection(1)).thenReturn(0);

    elevatorService.update(building);
    assertEquals(3, building.getFloors().size());
    assertEquals(2, building.getElevators().size());
    assertEquals(Direction.UNCOMMITTED, building.getElevator(0).getCommittedDirection());
    assertEquals(Direction.UP, building.getElevator(1).getCommittedDirection());

  }
}
