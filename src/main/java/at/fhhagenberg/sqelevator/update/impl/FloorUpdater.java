package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdate;

public class FloorUpdater implements IUpdate {
  private final IElevator controller;
  private Floor floor;

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
    floor.setDownButton(buttonDown);

    boolean buttonUp = controller.getFloorButtonUp(floorId);
    floor.setUpButton(buttonUp);

    return isUpdated;
  }
}
