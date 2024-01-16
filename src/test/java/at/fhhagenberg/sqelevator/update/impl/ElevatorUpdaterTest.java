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
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.property.ListProperty;
import at.fhhagenberg.sqelevator.property.Property;

@ExtendWith(MockitoExtension.class)
class ElevatorUpdaterTest {
    @Mock
    private IElevator controller;
    @Mock
    private Elevator elevator;

    @BeforeEach
    void beforeach()
    {
        // make pritty
    }

    @Test
    void testUpdate() throws RemoteException
    {
        List<Floor> floors=List.of(new Floor(0),new Floor(1),new Floor(2));
        elevator.setFloors(floors);
        List<Boolean> btnlist = List.of(false,false,false);
       
        var uut=new ElevatorUpdater(controller, elevator);
        Mockito.when(elevator.getElevatorNumber()).thenReturn(1);
        Mockito.when(elevator.getAllElevatorButtons()).thenReturn(btnlist);
        Mockito.when(controller.getServicesFloors(1,0)).thenReturn(true);
        Mockito.when(controller.getServicesFloors(1,1)).thenReturn(true);
        Mockito.when(controller.getCommittedDirection(1)).thenReturn(1);
        Mockito.when(controller.getElevatorAccel(1)).thenReturn(12);
        // Mockito.when(elevator.getButton(btnlist.get(0).getFloor())).thenReturn(btnlist.get(0));
        // Mockito.when(elevator.getButton(btnlist.get(1).getFloor())).thenReturn(btnlist.get(1));
        // Mockito.when(elevator.getButton(btnlist.get(2).getFloor())).thenReturn(btnlist.get(2));
        Mockito.when(controller.getElevatorButton(1, 0)).thenReturn(true);
        Mockito.when(controller.getElevatorDoorStatus(1)).thenReturn(1);
        Mockito.when(controller.getElevatorFloor(1)).thenReturn(2);
        Mockito.when(controller.getElevatorPosition(1)).thenReturn(200);
        Mockito.when(controller.getElevatorSpeed(1)).thenReturn(120);
        Mockito.when(controller.getElevatorWeight(1)).thenReturn(110);
        Mockito.when(controller.getTarget(1)).thenReturn(0);
        uut.update();

        verify(elevator,times(1)).setCommittedDirectionValue(Direction.DOWN);
        verify(elevator,times(1)).setAccelerationValue(12);
        verify(elevator,times(1)).setDoorStatusValue(DoorStatus.CLOSED);
        verify(elevator,times(1)).setCurrentFloorValue(floors.get(2));
        verify(elevator,times(1)).setCurrentPositionValue(200);
        verify(elevator,times(1)).setCurrentSpeedValue(120);
        verify(elevator,times(1)).setCurrentWeightValue(110);
        verify(elevator,times(1)).setTargetFloorValue(floors.get(0));

    }
    @Test
    void testUpdate2() throws RemoteException
    {

        List<Boolean> btnlist = List.of(false,false,false);
        var uut=new ElevatorUpdater(controller, elevator);
        
        Mockito.when(elevator.getElevatorNumber()).thenReturn(1);
        Mockito.when(controller.getElevatorDoorStatus(1)).thenReturn(2);
        Mockito.when(controller.getElevatorFloor(1)).thenReturn(4);
        Mockito.when(controller.getTarget(1)).thenReturn(4);
        Mockito.when(elevator.getAllElevatorButtons()).thenReturn(btnlist);
        uut.update();
        verify(elevator,times(0)).setDoorStatusValue(any());
        verify(elevator,times(0)).setCurrentFloorValue(any());
        verify(elevator,times(0)).setTargetFloorValue(any());
    }
    @Test
    void testUpdate_faultyControler() throws RemoteException
    {
        // List<Floor> floors=List.of(new Floor(0),new Floor(1),new Floor(2));
        List<Boolean> btnlist = List.of(false,false,false);
        var uut=new ElevatorUpdater(controller, elevator);

        Mockito.when(elevator.getAllElevatorButtons()).thenReturn(btnlist);
        Mockito.when(elevator.getElevatorNumber()).thenReturn(2);
        

        Mockito.when(controller.getCommittedDirection(1)).thenReturn(-1);
        Mockito.when(controller.getElevatorDoorStatus(1)).thenReturn(-1);
        Mockito.when(controller.getElevatorFloor(1)).thenReturn(-1);
        Mockito.when(controller.getTarget(1)).thenReturn(-1);

        // Mockito.when(elevator.getButton(btnlist.get(0).getFloor())).thenReturn(btnlist.get(0));
        // Mockito.when(elevator.getButton(btnlist.get(1).getFloor())).thenReturn(btnlist.get(1));
        // Mockito.when(elevator.getButton(btnlist.get(2).getFloor())).thenReturn(btnlist.get(2));
        Mockito.when(controller.getCommittedDirection(2)).thenReturn(3);
        Mockito.when(controller.getElevatorDoorStatus(2)).thenReturn(2);
        Mockito.when(controller.getElevatorFloor(2)).thenReturn(3);
        Mockito.when(controller.getTarget(2)).thenReturn(3);

        uut.update();
        Mockito.when(elevator.getElevatorNumber()).thenReturn(1);
        uut.update();

        verify(elevator,times(0)).setDoorStatusValue(any());
        verify(elevator,times(0)).setCurrentFloorValue(any());
        verify(elevator,times(0)).setTargetFloorValue(any());
        verify(elevator,times(0)).setCommittedDirectionValue(any());

    }
}
