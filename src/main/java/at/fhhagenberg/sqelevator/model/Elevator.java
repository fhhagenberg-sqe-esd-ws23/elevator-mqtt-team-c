package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
  private int currentFloor;
  private int targetFloor;
  private boolean moving;
  private List<Integer> floorRequests;

  public Elevator() {
    this(0, 0, false);
  }

  public Elevator(int currentFloor, int targetFloor, boolean moving) {
    this.currentFloor = currentFloor;
    this.targetFloor = targetFloor;
    this.moving = moving;
    this.floorRequests = new ArrayList<>();
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

  public void setCurrentFloor(int currentFloor) {
    this.currentFloor = currentFloor;
  }

  public int getTargetFloor() {
    return targetFloor;
  }

  public void setTargetFloor(int targetFloor) {
    this.targetFloor = targetFloor;
  }

  public boolean isMoving() {
    return moving;
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
  }

  public List<Integer> getFloorRequests() {
    return floorRequests;
  }

  public void setFloorRequests(List<Integer> floorRequests) {
    this.floorRequests = floorRequests;
  }
}
