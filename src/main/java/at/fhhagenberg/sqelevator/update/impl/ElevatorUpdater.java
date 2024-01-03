package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.update.IUpdater;

public class ElevatorUpdater implements IUpdater {
  private final IElevator controller;
  private final Elevator elevator;
  // private final Building building;

  public ElevatorUpdater(IElevator controller, Elevator elevator) {
    this.controller = controller;
    this.elevator = elevator;
    // this.building = building;
  }

  @Override
  public boolean update() throws RemoteException {
    boolean isUpdated = false;
    // todo: isUpdate logic missing
    int elevatorId = elevator.getElevatorNumber();

    int index = 0;
    for (Boolean state : elevator.getAllElevatorButtons()) {
      elevator.floorsServerd.set(controller.getServicesFloors(elevatorId, index), index);
      index++;
    }

    int committedDirection = controller.getCommittedDirection(elevatorId);
    if (committedDirection < Direction.values().length && committedDirection >= 0)
      elevator.committedDirection.set(Direction.values()[committedDirection]);

    int acceleration = controller.getElevatorAccel(elevatorId);
    elevator.acceleration.set(acceleration);

    index = 0;
    for (Boolean state : elevator.getAllElevatorButtons()) {
      elevator.floorButtonsState.set(controller.getElevatorButton(elevatorId, index), index);
      index++;
    }

    int elevatorDoorStatus = controller.getElevatorDoorStatus(elevatorId);
    if (elevatorDoorStatus < DoorStatus.values().length && elevatorDoorStatus >= 0) {
      elevator.doorStatus.set(DoorStatus.values()[elevatorDoorStatus]);
    }
    int elevatorFloor = controller.getElevatorFloor(elevatorId);
    if (elevatorFloor >= 0 && elevatorFloor < elevator.getAllElevatorButtons().size()) {
      elevator.currentFloor.set(elevator.Floors.get(elevatorFloor));
    }
    int elevatorPosition = controller.getElevatorPosition(elevatorId);
    elevator.currentPosition.set(elevatorPosition);

    int elevatorSpeed = controller.getElevatorSpeed(elevatorId);
    elevator.currentSpeed.set(elevatorSpeed);

    int elevatorWeight = controller.getElevatorWeight(elevatorId);
    elevator.currentWeight.set(elevatorWeight);

    int elevatorTarget = controller.getTarget(elevatorId);
    if (elevatorTarget >= 0 && elevatorTarget < elevator.getAllElevatorButtons().size())
      elevator.targetFloor.set(elevator.Floors.get(elevatorTarget));

    return isUpdated;
  }
}
