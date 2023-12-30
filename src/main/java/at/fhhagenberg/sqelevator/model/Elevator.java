package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.ListProperty;
import at.fhhagenberg.sqelevator.Property;

public class Elevator {

  // public Listener<Elevator,Direction> commitedDirectionListener;
  // public Listener<Elevator,Integer> accelListener;
  // public Listener<Elevator,DoorStatus> doorStatusListener;
  // public Listener<Elevator,Floor> currentFloorListener;
  // public Listener<Elevator,Integer> weightListner;

  private final int elevatorNumber;
  public Property<Elevator, Direction> committedDirection;
  public Property<Elevator, Integer> acceleration;
  public List<ElevatorButton> allButtons;
  public Property<Elevator, DoorStatus> doorStatus;
  public Property<Elevator, Floor> currentFloor;
  /**
   * position in feet
   */
  public Property<Elevator, Integer> currentPosition;
  /**
   * speed in feet per sec
   */
  public Property<Elevator, Integer> currentSpeed;
  /**
   * current passenger weight
   */
  public Property<Elevator, Integer> currentWeight;
  public Property<Elevator, Floor> targetFloor;
  private ListProperty<Elevator,Integer> floorRequests;

  private boolean isFloorServed(Floor f) {
    for (var b : allButtons) {
      if (b.getFloor() == f && b.isServerd())
        return true;
    }
    return false;
  }

  public Elevator(int elevatorNumber, List<Floor> floors) {
    this.elevatorNumber = elevatorNumber;
    this.allButtons = new ArrayList<>();
    this.floorRequests = new ListProperty<>(this);
    this.committedDirection = new Property<>(this);
    this.acceleration = new Property<>(this);
    this.doorStatus = new Property<>(this);
    this.currentFloor = new Property<>(this, (a) -> {
      return isFloorServed(a);
    });
    this.currentPosition = new Property<>(this);
    this.currentSpeed = new Property<>(this);
    this.currentWeight = new Property<>(this);
    this.targetFloor = new Property<>(this, (a) -> {
      return isFloorServed(a);
    });
    // this.committedDirection = new property<>(this);

    updateFloors(floors);
  }

  private void updateFloors(List<Floor> floors) {

    for (Floor floor : floors) {
      allButtons.add(new ElevatorButton(floor, this));
    }
  }

  public int getElevatorNumber() {
    return elevatorNumber;
  }

  // public List<Integer> getFloorRequests() {
  //   return floorRequests;
  // }

  // public void setFloorRequests(List<Integer> floorRequests) {
  //   this.floorRequests = floorRequests;
  // }

  public List<ElevatorButton> getAllElevatorButtons() {
    return allButtons;
  }

  public ElevatorButton getButton(Floor floor) {
    for (var b : getAllElevatorButtons()) {
      if (b.getFloor() == floor)
        return b;
    }
    return null;
  }
}
