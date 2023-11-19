package at.fhhagenberg.sqelevator.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.ElevatorButton;
import at.fhhagenberg.sqelevator.model.Floor;

public class ElevatorService {
  private Building building;
  private final IElevator controller;

  public ElevatorService(IElevator controller) throws RemoteException {
    this.controller = controller;

    init();
  }

  private void init() throws RemoteException {

    List<Elevator> elevators = new ArrayList<>();
    List<Floor> floors = new ArrayList<>();

    int numberOfElevators = controller.getElevatorNum();
    int numberOfFloors = controller.getFloorNum();

    IntStream.range(0, numberOfFloors)
        .mapToObj(Floor::new)
        .forEachOrdered(floors::add);

    for (int i = 0; i < numberOfElevators; i++) {
      Elevator elevator = new Elevator(i);

      for (Floor floor : floors) {
        if (controller.getServicesFloors(i, floor.getFloorNumber())) {
          elevator.addServedFloor(floor);

          // todo: check if just served buttons are used
          elevator.addServedFloorButton(new ElevatorButton(floor));
        }
      }
      elevators.add(elevator);
    }

    this.building = new Building(elevators, floors);
  }

  public void update() throws RemoteException {
    for (Elevator elevator : building.getElevators()) {
      int elevatorId = elevator.getElevatorNumber();

      int committedDirection = controller.getCommittedDirection(elevatorId);
      elevator.setCommittedDirection(Direction.values()[committedDirection]);

      int acceleration = controller.getElevatorAccel(elevatorId);
      elevator.setAcceleration(acceleration);

      for (ElevatorButton button : elevator.getButtons()) {
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
    }

    for (Floor floor : building.getFloors()) {
      int floorId = floor.getFloorNumber();

      boolean buttonDown = controller.getFloorButtonDown(floorId);
      floor.setDownButton(buttonDown);

      boolean buttonUp = controller.getFloorButtonUp(floorId);
      floor.setUpButton(buttonUp);
    }
  }
}
