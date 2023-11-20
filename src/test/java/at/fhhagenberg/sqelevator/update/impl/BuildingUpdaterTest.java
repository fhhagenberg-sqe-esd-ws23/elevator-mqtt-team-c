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

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;

@ExtendWith(MockitoExtension.class)
public class BuildingUpdaterTest {
  @Mock
  private IElevator controller;
  @Mock
  private Building building;
    @Captor ArgumentCaptor<List> captor;
    @Test
    public void firstUpdate(){
      BuildingUpdater uut=new BuildingUpdater(controller, building);
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
    }@Test
    public void secondUpdateSame() throws RemoteException{
      BuildingUpdater uut=new BuildingUpdater(controller, building);
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
      verify(building,times(1)).setElevators(anyList());
      verify(building,times(1)).setElevators(anyList());
    }
    @Test
    public void secondUpdateDiffrent() throws RemoteException{
      BuildingUpdater uut=new BuildingUpdater(controller, building);
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
      verify(building,times(2)).setElevators(anyList());
      verify(building,times(2)).setElevators(anyList());
    }
    
}
