package at.fhhagenberg.sqelevator.controller;

import at.fhhagenberg.sqelevator.model.Direction;
import sqelevator.IElevator;

import java.rmi.RemoteException;

public class ElevatorController {
    private final IElevator controller;

    public ElevatorController(IElevator controller) {
        this.controller = controller;
    }
    //        todo: exception handling
    public void setCommittedDirection(int elevatorNumber, Direction direction) {
        try {
            controller.setCommittedDirection(elevatorNumber, direction.ordinal());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
        try {
            controller.setServicesFloors(elevatorNumber, floor, service);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTarget(int elevatorNumber, int target) {
        try {
            controller.setTarget(elevatorNumber, target);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
