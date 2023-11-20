package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.ElevatorButton;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdate;

public class BuildingUpdater implements IUpdate {
  private final IElevator controller;
  private final Building building;

  public BuildingUpdater(IElevator controller, Building building) {
    this.controller = controller;
    this.building = building;
  }

  @Override
  public boolean update() throws RemoteException {
    boolean isUpdated = false;

    List<Floor> floors = new ArrayList<>();
    List<Elevator> elevators = new ArrayList<>();

    int floorNum = controller.getFloorNum();
    int elevatorNum = controller.getElevatorNum();

    if (building.getFloorNum() != floorNum || building.getElevatorNum() != elevatorNum) {
      for (int i = 0; i < floorNum; i++) {
        floors.add(new Floor(i));
      }

      for (int i = 0; i < elevatorNum; i++) {
        Elevator elevator = new Elevator(i);

        elevators.add(elevator);
      }

      isUpdated = true;

      //    this.building = new Building(elevators, floors);
      this.building.setFloors(floors);
      this.building.setElevators(elevators);
    }

    return isUpdated;
  }
}
