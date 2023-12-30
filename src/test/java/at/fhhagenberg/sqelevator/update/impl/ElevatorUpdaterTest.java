package at.fhhagenberg.sqelevator.update.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.Property;
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

    @BeforeEach
    void beforeach()
    {

        elevator.committedDirection=Mockito.mock(Property.class);
        elevator.acceleration=Mockito.mock(Property.class);
        elevator.doorStatus=Mockito.mock(Property.class);
        elevator.currentFloor=Mockito.mock(Property.class);
        elevator.currentPosition=Mockito.mock(Property.class);
        elevator.currentSpeed=Mockito.mock(Property.class);
        elevator.currentWeight=Mockito.mock(Property.class);
        elevator.targetFloor=Mockito.mock(Property.class);
    }

    @Test
    void testUpdate() throws RemoteException
    {

        List<ElevatorButton> btnlist = List.of(new ElevatorButton(new Floor(0),null),new ElevatorButton(new Floor(1),null),new ElevatorButton(new Floor(2),null));
        var uut=new ElevatorUpdater(controller, elevator);
        Mockito.when(elevator.getElevatorNumber()).thenReturn(1);
        Mockito.when(elevator.getAllElevatorButtons()).thenReturn(btnlist);
        Mockito.when(controller.getServicesFloors(1,0)).thenReturn(true);
        Mockito.when(controller.getServicesFloors(1,1)).thenReturn(true);
        Mockito.when(controller.getCommittedDirection(1)).thenReturn(1);
        Mockito.when(controller.getElevatorAccel(1)).thenReturn(12);
        Mockito.when(elevator.getButton(btnlist.get(0).getFloor())).thenReturn(btnlist.get(0));
        Mockito.when(elevator.getButton(btnlist.get(1).getFloor())).thenReturn(btnlist.get(1));
        Mockito.when(elevator.getButton(btnlist.get(2).getFloor())).thenReturn(btnlist.get(2));
        Mockito.when(controller.getElevatorButton(1, 0)).thenReturn(true);
        Mockito.when(controller.getElevatorDoorStatus(1)).thenReturn(1);
        Mockito.when(controller.getElevatorFloor(1)).thenReturn(2);
        Mockito.when(controller.getElevatorPosition(1)).thenReturn(200);
        Mockito.when(controller.getElevatorSpeed(1)).thenReturn(120);
        Mockito.when(controller.getElevatorWeight(1)).thenReturn(110);
        Mockito.when(controller.getTarget(1)).thenReturn(0);
        uut.update();

        // verify(elevator,times(1)).clearServesFloors();
        // verify(elevator,times(1)).clearServedButtons();
        // verify(elevator,times(2)).addServedFloor(any(Floor.class));
        // verify(elevator,times(2)).addServedFloorButton(any(ElevatorButton.class));
        verify(elevator.committedDirection,times(1)).set(Direction.DOWN);
        verify(elevator.acceleration,times(1)).set(12);
        verify(elevator.doorStatus,times(1)).set(DoorStatus.CLOSED);
        verify(elevator.currentFloor,times(1)).set(btnlist.get(2).getFloor());
        verify(elevator.currentPosition,times(1)).set(200);
        verify(elevator.currentSpeed,times(1)).set(120);
        verify(elevator.currentWeight,times(1)).set(110);
        verify(elevator.targetFloor,times(1)).set(btnlist.get(0).getFloor());

    }
    // @Test
    // void testUpdate2() throws RemoteException
    // {
    //     var uut=new ElevatorUpdater(controller, elevator);
        
    //     Mockito.when(elevator.getElevatorNumber()).thenReturn(1);
    //     Mockito.when(controller.getElevatorDoorStatus(1)).thenReturn(2);
    //     Mockito.when(controller.getElevatorFloor(1)).thenReturn(4);
    //     Mockito.when(controller.getTarget(1)).thenReturn(4);
    //     uut.update();
    //     verify(elevator,times(0)).setDoorStatus(any());
    //     verify(elevator,times(0)).setCurrentFloor(any());
    //     verify(elevator,times(0)).setTargetFloor(any());
    // }
    @Test
    void testUpdate_faultyControler() throws RemoteException
    {
        List<ElevatorButton> btnlist = List.of(new ElevatorButton(new Floor(0),null),new ElevatorButton(new Floor(1),null),new ElevatorButton(new Floor(2),null));
        var uut=new ElevatorUpdater(controller, elevator);

        Mockito.when(elevator.getAllElevatorButtons()).thenReturn(btnlist);
        Mockito.when(elevator.getElevatorNumber()).thenReturn(2);
        

        Mockito.when(controller.getCommittedDirection(1)).thenReturn(-1);
        Mockito.when(controller.getElevatorDoorStatus(1)).thenReturn(-1);
        Mockito.when(controller.getElevatorFloor(1)).thenReturn(-1);
        Mockito.when(controller.getTarget(1)).thenReturn(-1);

        Mockito.when(elevator.getButton(btnlist.get(0).getFloor())).thenReturn(btnlist.get(0));
        Mockito.when(elevator.getButton(btnlist.get(1).getFloor())).thenReturn(btnlist.get(1));
        Mockito.when(elevator.getButton(btnlist.get(2).getFloor())).thenReturn(btnlist.get(2));
        Mockito.when(controller.getCommittedDirection(2)).thenReturn(3);
        Mockito.when(controller.getElevatorDoorStatus(2)).thenReturn(2);
        Mockito.when(controller.getElevatorFloor(2)).thenReturn(3);
        Mockito.when(controller.getTarget(2)).thenReturn(3);

        uut.update();
        Mockito.when(elevator.getElevatorNumber()).thenReturn(1);
        uut.update();

        verify(elevator.doorStatus,times(0)).set(any());
        verify(elevator.currentFloor,times(0)).set(any());
        verify(elevator.targetFloor,times(0)).set(any());
        verify(elevator.committedDirection,times(0)).set(any());

    }
}
