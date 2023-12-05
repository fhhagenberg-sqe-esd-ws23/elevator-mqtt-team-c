package at.fhhagenberg.sqelevator.update.impl;

import java.rmi.RemoteException;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.ElevatorButton;
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


    for (ElevatorButton btn : elevator.getAllElevatorButtons()) {
        elevator.getButton(btn.getFloor()).setServed(controller.getServicesFloors(elevatorId, btn.getFloor().getFloorNumber()));
    }

    int committedDirection = controller.getCommittedDirection(elevatorId);
    if(committedDirection< Direction.values().length && committedDirection>=0)
    elevator.setCommittedDirection(Direction.values()[committedDirection]);

    int acceleration = controller.getElevatorAccel(elevatorId);
    elevator.setAcceleration(acceleration);

    for (ElevatorButton button : elevator.getAllElevatorButtons()) {
      boolean buttonPressed = controller.getElevatorButton(elevatorId, button.getFloor().getFloorNumber());
      button.setPressed(buttonPressed);
    }

    int elevatorDoorStatus = controller.getElevatorDoorStatus(elevatorId);
    if(elevatorDoorStatus< DoorStatus.values().length && elevatorDoorStatus>=0)
{    elevator.setDoorStatus(DoorStatus.values()[elevatorDoorStatus]);
}
    int elevatorFloor = controller.getElevatorFloor(elevatorId);
if(elevatorFloor>=0&&elevatorFloor<elevator.getAllElevatorButtons().size())
{    elevator.setCurrentFloor(elevator.getAllElevatorButtons().get(elevatorFloor).getFloor());
}
    int elevatorPosition = controller.getElevatorPosition(elevatorId);
    elevator.setCurrentPosition(elevatorPosition);

    int elevatorSpeed = controller.getElevatorSpeed(elevatorId);
    elevator.setCurrentSpeed(elevatorSpeed);

    int elevatorWeight = controller.getElevatorWeight(elevatorId);
    elevator.setCurrentWeight(elevatorWeight);

    int elevatorTarget = controller.getTarget(elevatorId);
    if(elevatorTarget>=0&&elevatorTarget<elevator.getAllElevatorButtons().size())
    elevator.setTargetFloor(elevator.getAllElevatorButtons().get(elevatorTarget).getFloor());

    return isUpdated;
  }
}
