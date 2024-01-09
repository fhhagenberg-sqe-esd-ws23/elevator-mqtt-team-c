package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Floor;

@ExtendWith(MockitoExtension.class)
class FloorUpdaterTest {
  @Mock
  private IElevator controller;

  @Mock
  private Floor floor;

  @Test
  void testButtonsPressed() throws RemoteException {
    FloorUpdater floorUpdater = new FloorUpdater(controller, floor);

    Mockito.when(floor.getFloorNumber()).thenReturn(1);
    Mockito.when(controller.getFloorButtonUp(1)).thenReturn(true);
    Mockito.when(controller.getFloorButtonDown(1)).thenReturn(true);

    floorUpdater.update();

    Mockito.verify(floor, Mockito.times(0)).setUpButton(false);
    Mockito.verify(floor, Mockito.times(1)).setUpButton(true);
    Mockito.verify(floor, Mockito.times(0)).setDownButton(false);
    Mockito.verify(floor, Mockito.times(1)).setDownButton(true);
  }
}
