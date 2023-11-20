package at.fhhagenberg.sqelevator.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdate;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;
import at.fhhagenberg.sqelevator.update.impl.ElevatorUpdater;
import at.fhhagenberg.sqelevator.update.impl.FloorUpdater;

public class ElevatorService {
  private final IElevator controller;
  private final BuildingUpdater buildingUpdater;
  private final List<IUpdate> updates = new ArrayList<>();
  
  public ElevatorService(IElevator controller, BuildingUpdater buildingUpdater) throws RemoteException {
    this.controller = controller;
    this.buildingUpdater = buildingUpdater;
  }

  private void initUpdaters(Building building) throws RemoteException {
//    buildingUpdater.update();

    for (Floor floor : building.getFloors()) {
      this.updates.add(new FloorUpdater(controller, floor));
    }
    for (Elevator elevator : building.getElevators()) {
      this.updates.add(new ElevatorUpdater(controller, elevator, building));
    }
  }

  public void update(Building building) throws RemoteException {
    buildingUpdater.update();

    initUpdaters(building);

    for (var updater : this.updates) {
      updater.update();
    }
  }
}
