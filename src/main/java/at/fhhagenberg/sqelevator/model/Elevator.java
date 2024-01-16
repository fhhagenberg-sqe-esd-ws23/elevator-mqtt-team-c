package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.property.ListListener;
import at.fhhagenberg.sqelevator.property.ListProperty;
import at.fhhagenberg.sqelevator.property.Listener;
import at.fhhagenberg.sqelevator.property.Property;

public class Elevator {
  private final int elevatorNumber;

  private List<Floor> floors;
  private final Property<Elevator, Direction> committedDirection;
  private final Property<Elevator, Integer> acceleration;
  private final ListProperty<Elevator,Boolean> floorsServerd;
  private final ListProperty<Elevator,Boolean> floorButtonsState;
  private final Property<Elevator, DoorStatus> doorStatus;
  private final Property<Elevator, Floor> currentFloor;
  private final Property<Elevator, Integer> currentPosition;
  private final Property<Elevator, Integer> currentSpeed;
  private final Property<Elevator, Integer> currentWeight;
  private final Property<Elevator, Floor> targetFloor;

  private boolean isFloorServed(Floor f) {
    int nr=f.getFloorNumber();
    int size=floorsServerd.get().size();
    if(size<=nr) return false;
    return floorsServerd.get(nr);
  }

  public Elevator(int elevatorNumber, List<Floor> floors) {
    this.elevatorNumber = elevatorNumber;
    this.floors = new ArrayList<>();
    this.floorsServerd = new ListProperty<>(this);
    this.floorButtonsState = new ListProperty<>(this);
    this.committedDirection = new Property<>(this);
    this.acceleration = new Property<>(this);
    this.doorStatus = new Property<>(this);
    this.currentFloor = new Property<>(this, this::isFloorServed);
    this.currentPosition = new Property<>(this);
    this.currentSpeed = new Property<>(this);
    this.currentWeight = new Property<>(this,0);
    this.targetFloor = new Property<>(this, this::isFloorServed);

    updateFloors(floors);
  }

  private void updateFloors(List<Floor> floors) {
    this.floors.clear();
    floorButtonsState.setSize(floors.size(),false);
    floorsServerd.setSize(floors.size(),false);
      this.floors.addAll(floors);
  }

  public int getElevatorNumber() {
    return elevatorNumber;
  }

  public List<Boolean> getAllElevatorButtons() {
    return floorButtonsState.get();
  }

  public Direction getCommittedDirectionValue() {
    return committedDirection.get();
  }

  public Integer getAccelerationValue() {
    return acceleration.get();
  }

  public List<Boolean> getFloorsServerdValue() {
    return floorsServerd.get();
  }

  public List<Boolean> getFloorButtonsStateValue() {
    return floorButtonsState.get();
  }

  public DoorStatus getDoorStatusValue() {
    return doorStatus.get();
  }

  public Floor getCurrentFloorValue() {
    return currentFloor.get();
  }

  public Integer getCurrentPositionValue() {
    return currentPosition.get();
  }

  public Integer getCurrentSpeedValue() {
    return currentSpeed.get();
  }

  public Integer getCurrentWeightValue() {
    return currentWeight.get();
  }

  public Floor getTargetFloorValue() {
    return targetFloor.get();
  }

  public void setCommittedDirectionValue(Direction value) {
    this.committedDirection.set(value);
  }

  public void setAccelerationValue(Integer value) {
    this.acceleration.set(value);
  }

 public void setFloorsServerdValue(Boolean value, int index) {
    this.floorsServerd.set(value, index);
  }

  public void setFloorButtonsStateValue(Boolean value, int index) {
    this.floorButtonsState.set(value, index);
  }

  public void setDoorStatusValue(DoorStatus value) {
    this.doorStatus.set(value);
  }

  public void setCurrentFloorValue(Floor value) {
    this.currentFloor.set(value);
  }

  public void setCurrentPositionValue(Integer value) {
    this.currentPosition.set(value);
  }

  public void setCurrentSpeedValue(Integer value) {
    this.currentSpeed.set(value);
  }

  public void setCurrentWeightValue(Integer value) {
    this.currentWeight.set(value);
  }

  public void setTargetFloorValue(Floor value) {
    this.targetFloor.set(value);
  }

  public void addCommittedDirectionListener(Listener<Elevator, Direction> listener) {
    this.committedDirection.addListner(listener);
  }

  public void addAccelerationListener(Listener<Elevator, Integer> listener) {
    this.acceleration.addListner(listener);
  }

  public void addFloorsServerdListener(ListListener<Elevator, Boolean> listener) {
    this.floorsServerd.addListner(listener);
  }

  public void addFloorButtonsStateListener(ListListener<Elevator, Boolean> listener) {
    this.floorButtonsState.addListner(listener);
  }

  public void addDoorStatusListener(Listener<Elevator, DoorStatus> listener) {
    this.doorStatus.addListner(listener);
  }

  public void addCurrentFloorListener(Listener<Elevator, Floor> listener) {
    this.currentFloor.addListner(listener);
  }

  public void addCurrentPositionListener(Listener<Elevator, Integer> listener) {
    this.currentPosition.addListner(listener);
  }

  public void addCurrentSpeedListener(Listener<Elevator, Integer> listener) {
    this.currentSpeed.addListner(listener);
  }

  public void addCurrentWeightListener(Listener<Elevator, Integer> listener) {
    this.currentWeight.addListner(listener);
  }

  public void addTargetFloorListener(Listener<Elevator, Floor> listener) {
    this.targetFloor.addListner(listener);
  }

  public List<Floor> getFloors() {
    return floors;
  }

  public void setFloors(List<Floor> floors) {
    this.floors = floors;
  }
}
