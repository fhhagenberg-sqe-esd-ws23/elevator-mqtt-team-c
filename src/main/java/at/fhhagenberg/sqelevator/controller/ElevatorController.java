package at.fhhagenberg.sqelevator.controller;

import at.fhhagenberg.sqelevator.model.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sqelevator.IElevator;

import java.rmi.RemoteException;

public class ElevatorController {
    private static final Logger logger = LogManager.getLogger(ElevatorController.class);

    private final IElevator controller;

    public ElevatorController(IElevator controller) {
        this.controller = controller;
    }
    public void setCommittedDirection(int elevatorNumber, Direction direction) {
        try {
            controller.setCommittedDirection(elevatorNumber, direction.ordinal());
        } catch (RemoteException e) {
            logger.error("Failed to set committed direction. ", e);
        }
    }

    public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
        try {
            controller.setServicesFloors(elevatorNumber, floor, service);
        } catch (RemoteException e) {
            logger.error("Failed to set servicesFloors. ", e);

        }
    }
    public void setTarget(int elevatorNumber, int target) {
        try {
            controller.setTarget(elevatorNumber, target);
        } catch (RemoteException e) {
            logger.error("Failed to set target. ", e);

        }
    }

}
