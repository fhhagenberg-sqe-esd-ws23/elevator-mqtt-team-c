package at.fhhagenberg.sqelevator.controller;

import at.fhhagenberg.sqelevator.model.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;

import java.rmi.RemoteException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorControllerTest {
    @Mock
    private IElevator controller;

    @Test
    void testSetCommitedDirection() throws RemoteException {
        ElevatorController elevatorController = new ElevatorController(controller);
        Direction direction = Direction.DOWN;

        elevatorController.setCommittedDirection(1, direction);

        verify(controller, times(1)).setCommittedDirection(1, direction.ordinal());
    }

    @Test
    void testSetCommitedDirectionRemoteException() throws RemoteException {
        ElevatorController elevatorController = new ElevatorController(controller);
        Direction direction = Direction.DOWN;

        Mockito.doThrow(new RemoteException()).when(controller).setCommittedDirection(anyInt(), anyInt());

        // do not throw, just log (the show must go on)
        Assertions.assertDoesNotThrow(() -> elevatorController.setCommittedDirection(1, direction));
    }

    @Test
    void testSetServicesFloors() throws RemoteException {
        ElevatorController elevatorController = new ElevatorController(controller);

        elevatorController.setServicesFloors(1, 5, true);
        elevatorController.setServicesFloors(2, 2, false);

        verify(controller, times(1)).setServicesFloors(1,5, true);
        verify(controller, times(1)).setServicesFloors(2,2, false);
    }

    @Test
    void testSetServicesFloorsRemoteException() throws RemoteException {
        ElevatorController elevatorController = new ElevatorController(controller);

        Mockito.doThrow(new RemoteException()).when(controller).setServicesFloors(anyInt(), anyInt(), anyBoolean());

        // do not throw, just log (the show must go on)
        Assertions.assertDoesNotThrow(() -> elevatorController.setServicesFloors(2, 3, false));
    }

    @Test
    void testSetTarget() throws RemoteException {
        ElevatorController elevatorController = new ElevatorController(controller);

        elevatorController.setTarget(1, 5);
        elevatorController.setTarget(2, 2);

        verify(controller, times(1)).setTarget(1,5);
        verify(controller, times(1)).setTarget(2,2);
    }

    @Test
    void testSetTargetRemoteException() throws RemoteException {
        ElevatorController elevatorController = new ElevatorController(controller);

        Mockito.doThrow(new RemoteException()).when(controller).setTarget(anyInt(), anyInt());

        // do not throw, just log (the show must go on)
        Assertions.assertDoesNotThrow(() -> elevatorController.setTarget(2, 3));
    }


}
