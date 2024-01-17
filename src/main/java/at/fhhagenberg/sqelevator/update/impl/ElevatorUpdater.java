package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.update.IUpdater;
import sqelevator.IElevator;

public class ElevatorUpdater implements IUpdater {
  private final IElevator controller;
  private final Elevator elevator;

  public ElevatorUpdater(IElevator controller, Elevator elevator) {
    this.controller = controller;
    this.elevator = elevator;
  }

  @Override
  public boolean update() throws RemoteException {
    boolean isUpdated = false;

    int elevatorId = elevator.getElevatorNumber();

    for (int index = 0; index < elevator.getAllElevatorButtons().size(); index++) {
      elevator.setFloorsServerdValue(controller.getServicesFloors(elevatorId, index), index);
    }

    int committedDirection = controller.getCommittedDirection(elevatorId);
    if (committedDirection < Direction.values().length && committedDirection >= 0)
      elevator.setCommittedDirectionValue(Direction.values()[committedDirection]);

    int acceleration = controller.getElevatorAccel(elevatorId);
    elevator.setAccelerationValue(acceleration);

    for (int index = 0; index < elevator.getAllElevatorButtons().size(); index++) {
      elevator.setFloorButtonsStateValue(controller.getElevatorButton(elevatorId, index), index);
    }

    int elevatorDoorStatus = controller.getElevatorDoorStatus(elevatorId);
    if (elevatorDoorStatus <= DoorStatus.values().length && elevatorDoorStatus > 0) {
      elevator.setDoorStatusValue(DoorStatus.values()[elevatorDoorStatus-1]);
    }
    int elevatorFloor = controller.getElevatorFloor(elevatorId);
    if (elevatorFloor >= 0 && elevatorFloor < elevator.getAllElevatorButtons().size()) {
      elevator.setCurrentFloorValue(elevator.getFloors().get(elevatorFloor));
    }
    int elevatorPosition = controller.getElevatorPosition(elevatorId);
    elevator.setCurrentPositionValue(elevatorPosition);

    int elevatorSpeed = controller.getElevatorSpeed(elevatorId);
    elevator.setCurrentSpeedValue(elevatorSpeed);

    int elevatorWeight = controller.getElevatorWeight(elevatorId);
    elevator.setCurrentWeightValue(elevatorWeight);

    int elevatorTarget = controller.getTarget(elevatorId);
    if (elevatorTarget >= 0 && elevatorTarget < elevator.getAllElevatorButtons().size())
      elevator.setTargetFloorValue(elevator.getFloors().get(elevatorTarget));

    return isUpdated;
  }
}
