package at.fhhagenberg.sqelevator.update.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
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
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.ElevatorButton;
import at.fhhagenberg.sqelevator.model.Floor;

@ExtendWith(MockitoExtension.class)
public class ElevatorUpdaterTest {
    @Mock
    private IElevator controller;
    @Mock
    private Elevator elevator;


    @Test
    void testUpdate() throws RemoteException
    {
        List<ElevatorButton> btnlist = List.of(new ElevatorButton(new Floor(0)),new ElevatorButton(new Floor(1)),new ElevatorButton(new Floor(2)));
        var uut=new ElevatorUpdater(controller, elevator);
        Mockito.when(elevator.getElevatorNumber()).thenReturn(1);
        Mockito.when(elevator.getAllElevatorButtons()).thenReturn(btnlist);
        Mockito.when(controller.getServicesFloors(1,0)).thenReturn(true);
        Mockito.when(controller.getServicesFloors(1,1)).thenReturn(true);
        Mockito.when(controller.getCommittedDirection(1)).thenReturn(1);
        Mockito.when(controller.getElevatorAccel(1)).thenReturn(12);
        Mockito.when(elevator.getServedButtons()).thenReturn(btnlist);
        Mockito.when(controller.getElevatorButton(1, 0)).thenReturn(true);
        Mockito.when(controller.getElevatorDoorStatus(1)).thenReturn(1);
        Mockito.when(controller.getElevatorFloor(1)).thenReturn(2);
        Mockito.when(controller.getElevatorPosition(1)).thenReturn(200);
        Mockito.when(controller.getElevatorSpeed(1)).thenReturn(120);
        Mockito.when(controller.getElevatorWeight(1)).thenReturn(110);
        Mockito.when(controller.getTarget(1)).thenReturn(0);
        uut.update();

        verify(elevator,times(1)).clearServesFloors();
        verify(elevator,times(1)).clearServedButtons();
        verify(elevator,times(2)).addServedFloor(any(Floor.class));
        verify(elevator,times(2)).addServedFloorButton(any(ElevatorButton.class));
        verify(elevator,times(1)).setCommittedDirection(Direction.DOWN);
        verify(elevator,times(1)).setAcceleration(12);
        verify(elevator,times(1)).setDoorStatus(DoorStatus.CLOSED);
        verify(elevator,times(1)).setCurrentFloor(btnlist.get(2).getFloor());
        verify(elevator,times(1)).setCurrentPosition(200);
        verify(elevator,times(1)).setCurrentSpeed(120);
        verify(elevator,times(1)).setCurrentWeight(110);
        verify(elevator,times(1)).setTargetFloor(btnlist.get(0).getFloor());


    }


}
