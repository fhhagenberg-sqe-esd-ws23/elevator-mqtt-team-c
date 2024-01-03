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
  public List<Floor> Floors;
  public ListProperty<Elevator,Boolean> floorsServerd;
  public ListProperty<Elevator,Boolean> floorButtonsState;
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
    int nr=f.getFloorNumber();
    int size=floorsServerd.get().size();
    if(size<=nr) return false;
    return floorsServerd.get(nr);
  }

  public Elevator(int elevatorNumber, List<Floor> floors) {
    this.elevatorNumber = elevatorNumber;
    this.Floors = new ArrayList<>();
    this.floorRequests = new ListProperty<>(this);
    this.floorsServerd = new ListProperty<>(this);
    this.floorButtonsState = new ListProperty<>(this);
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
    Floors.clear();
    floorButtonsState.setSize(floors.size(),false);
    floorsServerd.setSize(floors.size(),false);
    for (Floor floor : floors) {
      Floors.add(floor);
    }
  }

  public int getElevatorNumber() {
    return elevatorNumber;
  }



  public List<Boolean> getAllElevatorButtons() {
    return floorButtonsState.get();
  }

  public Boolean getButton(Floor floor) {
    return floorButtonsState.get(floor.getFloorNumber());  
  }
}
