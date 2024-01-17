package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdater;

public class FloorUpdater implements IUpdater {
  private final IElevator controller;
  private final Floor floor;

  public FloorUpdater(IElevator controller, Floor floor) {
    this.controller = controller;
    this.floor = floor;
  }

  @Override
  public boolean update() throws RemoteException {
    boolean isUpdated = false;

    int floorId = floor.getFloorNumber();

    boolean buttonDown = controller.getFloorButtonDown(floorId);
    floor.downButton.set(buttonDown);

    boolean buttonUp = controller.getFloorButtonUp(floorId);
    floor.upButton.set(buttonUp);
    return isUpdated;
  }
}
