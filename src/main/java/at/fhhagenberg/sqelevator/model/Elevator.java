package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.Listener;

public class Elevator {

  public Listener<Direction> commitedDirectionListener;
  public Listener<Integer> accelListener;
  public Listener<DoorStatus> doorStatusListener;
  public Listener<Floor> currentFloorListener;


  private final int elevatorNumber;
  private Direction committedDirection;
  private int acceleration;
  private List<ElevatorButton> allButtons;
  private DoorStatus doorStatus;
  private Floor currentFloor;
  /**
   * position in feet
   */
  private int currentPosition;
  /**
   * speed in feet per sec
   */
  private int currentSpeed;
  /**
   * current passenger weight
   */
  private int currentWeight;
  private Floor targetFloor;
  private List<Integer> floorRequests;

  public Elevator(int elevatorNumber, List<Floor> floors) {
    this.elevatorNumber = elevatorNumber;
    this.allButtons = new ArrayList<>();
    this.floorRequests = new ArrayList<>();
    updateFloors(floors);
  }

  private void updateFloors(List<Floor> floors) {

    for (Floor floor : floors) {
      allButtons.add(new ElevatorButton(floor));
    }
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
    if (this.acceleration != acceleration) {
      this.acceleration = acceleration;

      if (accelListener != null) {
        accelListener.call(elevatorNumber, acceleration);
      }
//      mqttService.publish("elevator/" + elevatorNumber + "/accelertation/", String.valueOf(acceleration));
    }
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
    for (var b : allButtons) {
      if (b.getFloor() == currentFloor && b.isServerd())
        this.currentFloor = currentFloor;
    }
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

  public Floor getTargetFloor() {
    return targetFloor;
  }

  public void setTargetFloor(Floor targetFloor) {
    for (var b : allButtons) {
      if (b.getFloor() == targetFloor && b.isServerd())
        this.targetFloor = targetFloor;
    }
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

public ElevatorButton getButton(Floor floor) {
  for(var b:allButtons)
  {
    if(b.getFloor()==floor)
      return b;
  }
  return null;
}
}
