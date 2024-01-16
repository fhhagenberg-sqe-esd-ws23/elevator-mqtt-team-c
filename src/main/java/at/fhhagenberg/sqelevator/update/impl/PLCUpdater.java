package at.fhhagenberg.sqelevator.update.impl;

import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import sqelevator.IElevator;

import java.rmi.RemoteException;

public class PLCUpdater{

    private final IElevator controller;

    private final Building building;

    public PLCUpdater(IElevator controller, Building building) {
        this.controller = controller;
        this.building = building;
    }

    public void updateCommittedDirection(int elevatorNumber, int direction) throws RemoteException {

        if (Direction.values()[direction] != building.getElevator(elevatorNumber).getCommittedDirectionValue()) {
            controller.setCommittedDirection(elevatorNumber, direction);
        }
    }
}
