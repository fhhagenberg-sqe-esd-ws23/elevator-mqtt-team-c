package at.fhhagenberg.sqelevator.model;

import java.util.List;

public class Elevator {

  private final int elevatorNumber;
  private Direction committedDirection;
  private int acceleration;
  private List<ElevatorButton> buttons;
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

  public Elevator(int elevatorNumber) {
    this.elevatorNumber = elevatorNumber;
  }

  public void setServicesFloors(List<Floor> servicesFloors) {
    this.servicesFloors = servicesFloors;
  }

  public void addServedFloor(Floor floor) {
    this.servicesFloors.add(floor);
  }

  public void addServedFloorButton(ElevatorButton button) {
    this.buttons.add(button);
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

  public List<ElevatorButton> getButtons() {
    return buttons;
  }

  public void setButtons(List<ElevatorButton> buttons) {
    this.buttons = buttons;
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
}
