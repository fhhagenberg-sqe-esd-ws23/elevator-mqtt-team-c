package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.ElevatorButton;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.update.IUpdate;

public class ElevatorUpdater implements IUpdate {
  private final IElevator controller;
  private Elevator elevator;
  private Building building;

  public ElevatorUpdater(IElevator controller, Elevator elevator, Building building) {
    this.controller = controller;
    this.elevator = elevator;
    this.building = building;
  }

  @Override
  public boolean update() throws RemoteException {
    boolean isUpdated = false;
    // todo: isUpdate logic missing
    int elevatorId = elevator.getElevatorNumber();

    for (Floor floor : building.getFloors()) {
      if (controller.getServicesFloors(elevatorId, floor.getFloorNumber())) {
        elevator.addServedFloor(floor);

        // todo: check if just served buttons are used
        elevator.addServedFloorButton(new ElevatorButton(floor));
      } else {
        elevator.removeServedFloor(floor);
//        elevator.removeServedFloorButton(floor.);
      }
    }

    int committedDirection = controller.getCommittedDirection(elevatorId);
    elevator.setCommittedDirection(Direction.values()[committedDirection]);

    int acceleration = controller.getElevatorAccel(elevatorId);
    elevator.setAcceleration(acceleration);

    for (ElevatorButton button : elevator.getServedButtons()) {
      boolean buttonPressed = controller.getElevatorButton(elevatorId, button.getFloor().getFloorNumber());
      button.setPressed(buttonPressed);
    }
    int elevatorDoorStatus = controller.getElevatorDoorStatus(elevatorId);
    elevator.setDoorStatus(DoorStatus.values()[elevatorDoorStatus]);

    int elevatorFloor = controller.getElevatorFloor(elevatorId);
    elevator.setCurrentFloor(building.getFloor(elevatorFloor));

    int elevatorPosition = controller.getElevatorPosition(elevatorId);
    elevator.setCurrentPosition(elevatorPosition);

    int elevatorSpeed = controller.getElevatorSpeed(elevatorId);
    elevator.setCurrentSpeed(elevatorSpeed);

    int elevatorWeight = controller.getElevatorWeight(elevatorId);
    elevator.setCurrentWeight(elevatorWeight);

    int elevatorTarget = controller.getTarget(elevatorId);
    elevator.setTargetFloor(building.getFloor(elevatorTarget));

    return isUpdated;
  }
}
