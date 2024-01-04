package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdater;
import sqelevator.IElevator;

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
    // todo: isUpdate logic missing
    int floorId = floor.getFloorNumber();

    boolean buttonDown = controller.getFloorButtonDown(floorId);
    // todo: isUpdated |= floor.setDownButton(buttonDown);
    floor.setDownButton(buttonDown);

    boolean buttonUp = controller.getFloorButtonUp(floorId);
    floor.setUpButton(buttonUp);

    return isUpdated;
  }
}
