package at.fhhagenberg.sqelevator.service;

import java.rmi.RemoteException;
import java.util.List;

import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdater;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;
import at.fhhagenberg.sqelevator.update.impl.ElevatorUpdater;
import at.fhhagenberg.sqelevator.update.impl.FloorUpdater;
import sqelevator.IElevator;

public class ElevatorService {
  private final IElevator controller;
  private final BuildingUpdater buildingUpdater;
  private final List<IUpdater> updaters;
  
  public ElevatorService(IElevator controller, BuildingUpdater buildingUpdater, List<IUpdater> updaters) {
    this.controller = controller;
    this.buildingUpdater = buildingUpdater;
    this.updaters = updaters;
  }

  private void initUpdaters(Building building) {
    updaters.clear();

    for (Floor floor : building.getFloors()) {
      this.updaters.add(new FloorUpdater(controller, floor));
    }
    for (Elevator elevator : building.getElevators()) {
      this.updaters.add(new ElevatorUpdater(controller, elevator));
    }
  }

  public void update(Building building) throws RemoteException {
    if (buildingUpdater.update()) {
      initUpdaters(building);
    }

    for (var updater : this.updaters) {
      updater.update();
    }
  }
}
