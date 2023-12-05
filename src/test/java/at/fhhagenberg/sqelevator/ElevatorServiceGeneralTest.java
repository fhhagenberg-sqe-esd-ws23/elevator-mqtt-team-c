package at.fhhagenberg.sqelevator;

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

import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.ElevatorService;
import at.fhhagenberg.sqelevator.update.IUpdater;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;


@ExtendWith(MockitoExtension.class)
class ElevatorServiceGeneralTest {
  @Mock
  private IElevator controller;

  @Mock
  private IUpdater floorUpdater;
  @Mock
  private IUpdater elevatorUpdater;
  private ElevatorService elevatorService;

  private Building building;

  @BeforeEach
  public void setUp() {
    List<IUpdater> updaters = new ArrayList<>();
    updaters.add(floorUpdater);
    updaters.add(elevatorUpdater);
    building = new Building();

    BuildingUpdater buildingUpdater = new BuildingUpdater(controller, building);

    elevatorService = Mockito.spy(new ElevatorService(controller, buildingUpdater, updaters));
  }

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
    Mockito.when(controller.getServicesFloors(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

    elevatorService.update(building);

    Assertions.assertEquals(3, building.getFloors().size());
    Assertions.assertEquals(2, building.getElevators().size());
    // Assertions.assertEquals(3, building.getElevator(0).getServedButtons().size());
    Assertions.assertEquals(Direction.UP, building.getElevator(0).getCommittedDirection());
    Assertions.assertEquals(Direction.DOWN, building.getElevator(1).getCommittedDirection());
    Assertions.assertEquals(12,building.getElevator(0).getAcceleration());
    Assertions.assertEquals(0,building.getElevator(0).getAllElevatorButtons().get(0).getFloor().getFloorNumber());
    Assertions.assertFalse(building.getElevator(0).getAllElevatorButtons().get(0).isPressed());
    Assertions.assertTrue(building.getElevator(0).getAllElevatorButtons().get(1).isPressed());
    Assertions.assertFalse(building.getElevator(0).getAllElevatorButtons().get(2).isPressed());

    Mockito.when(controller.getCommittedDirection(0)).thenReturn(2);
    Mockito.when(controller.getCommittedDirection(1)).thenReturn(0);

    elevatorService.update(building);
    Assertions.assertEquals(3, building.getFloors().size());
    Assertions.assertEquals(2, building.getElevators().size());
    Assertions.assertEquals(Direction.UNCOMMITTED, building.getElevator(0).getCommittedDirection());
    Assertions.assertEquals(Direction.UP, building.getElevator(1).getCommittedDirection());

  }

}
