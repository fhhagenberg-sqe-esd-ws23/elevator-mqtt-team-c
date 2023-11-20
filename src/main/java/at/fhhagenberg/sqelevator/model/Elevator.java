package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

  private final int elevatorNumber;
  private Direction committedDirection;
  private int acceleration;
  private List<ElevatorButton> allButtons;
  private List<ElevatorButton> servedButtons;
  private DoorStatus doorStatus;
  private Floor currentFloor;
  /** position in feet */
  private int currentPosition;
  /** speed in feet per sec */
  private int currentSpeed;
  /** current passenger weight */
  private int currentWeight;
  private List<Floor> servicesFloors;
  private Floor targetFloor;
  private List<Integer> floorRequests;

  public Elevator(int elevatorNumber,List<Floor>floors) {
    this.elevatorNumber = elevatorNumber;
    this.allButtons= new ArrayList<>();
    this.servedButtons = new ArrayList<>();
    this.servicesFloors = new ArrayList<>();
    this.floorRequests = new ArrayList<>();
    updateFloors(floors);
  }

  public void updateFloors(List<Floor> floors)
  {
    servedButtons.clear();
    servicesFloors.clear();
    allButtons.clear();
    for (Floor floor : floors) {
      allButtons.add(new ElevatorButton(floor));
    }

  }
  public void setServicesFloors(List<Floor> servicesFloors) {
    this.servicesFloors = servicesFloors;
  }

  public void addServedFloor(Floor floor) {
    this.servicesFloors.add(floor);
  }

  public boolean removeServedFloor(Floor floor) {
    return this.servicesFloors.remove(floor);
  }

  public void addServedFloorButton(ElevatorButton button) {
    this.servedButtons.add(button);
  }

  public boolean removeServedFloorButton(ElevatorButton button) {
    return this.servedButtons.remove(button);
  }

  public int getElevatorNumber() {
    return elevatorNumber;
  }

  public Direction getCommittedDirection() {
    return committedDirection;
  }

  public void setCommittedDirection(Direction committedDirection) {
    this.committedDirection = committedDirection;
  }

  public int getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(int acceleration) {
    this.acceleration = acceleration;
  }

  public List<ElevatorButton> getServedButtons() {
    return servedButtons;
  }

  public void setServedButtons(List<ElevatorButton> servedButtons) {
    this.servedButtons = servedButtons;
  }

  public DoorStatus getDoorStatus() {
    return doorStatus;
  }

  public void setDoorStatus(DoorStatus doorStatus) {
    this.doorStatus = doorStatus;
  }

  public Floor getCurrentFloor() {
    return currentFloor;
  }

  public void setCurrentFloor(Floor currentFloor) {
    this.currentFloor = currentFloor;
  }

  public int getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(int currentPosition) {
    this.currentPosition = currentPosition;
  }

  public int getCurrentSpeed() {
    return currentSpeed;
  }

  public void setCurrentSpeed(int currentSpeed) {
    this.currentSpeed = currentSpeed;
  }

  public int getCurrentWeight() {
    return currentWeight;
  }

  public void setCurrentWeight(int currentWeight) {
    this.currentWeight = currentWeight;
  }

  public List<Floor> getServicesFloors() {
    return servicesFloors;
  }

  public Floor getTargetFloor() {
    return targetFloor;
  }

  public void setTargetFloor(Floor targetFloor) {
    this.targetFloor = targetFloor;
  }

  public List<Integer> getFloorRequests() {
    return floorRequests;
  }

  public void setFloorRequests(List<Integer> floorRequests) {
    this.floorRequests = floorRequests;
  }

public List<ElevatorButton> getAllElevatorButtons() {
    return allButtons;
}
}
