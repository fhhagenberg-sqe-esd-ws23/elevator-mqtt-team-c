package at.fhhagenberg.sqelevator.update.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;

@ExtendWith(MockitoExtension.class)
class BuildingUpdaterTest {
  @Mock
  private IElevator controller;
  @Mock
  private Building building;
  @Captor
  ArgumentCaptor<List> captor;

  @Test
  void testFirstUpdate() {
    BuildingUpdater uut = new BuildingUpdater(controller, building);
    try {
      Mockito.when(controller.getElevatorNum()).thenReturn(3);
      Mockito.when(controller.getFloorNum()).thenReturn(4);

      uut.update();
    } catch (RemoteException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    verify(building).setElevators(captor.capture());
    assertEquals(3, captor.getValue().size());
    verify(building).setFloors(captor.capture());
    assertEquals(4, captor.getValue().size());
  }

  @Test
  void testSecondUpdateSame() throws RemoteException {
    BuildingUpdater uut = new BuildingUpdater(controller, building);

    Mockito.when(controller.getElevatorNum()).thenReturn(3);
    Mockito.when(controller.getFloorNum()).thenReturn(4);

    uut.update();

    verify(building).setElevators(captor.capture());
    assertEquals(3, captor.getValue().size());
    verify(building).setFloors(captor.capture());
    assertEquals(4, captor.getValue().size());

    Mockito.when(building.getElevatorNum()).thenReturn(3);
    Mockito.when(building.getFloorNum()).thenReturn(4);

    uut.update();

    verify(building, times(1)).setElevators(anyList());
    verify(building, times(1)).setElevators(anyList());
  }

  @Test
  void testSecondUpdateDiffrent() throws RemoteException {
    BuildingUpdater uut = new BuildingUpdater(controller, building);

    Mockito.when(controller.getElevatorNum()).thenReturn(3);
    Mockito.when(controller.getFloorNum()).thenReturn(4);

    uut.update();

    verify(building).setElevators(captor.capture());
    assertEquals(3, captor.getValue().size());
    verify(building).setFloors(captor.capture());
    assertEquals(4, captor.getValue().size());

    Mockito.when(building.getElevatorNum()).thenReturn(4);
    Mockito.when(building.getFloorNum()).thenReturn(4);

    uut.update();

    verify(building, times(2)).setElevators(anyList());
    verify(building, times(2)).setElevators(anyList());
  }

}
