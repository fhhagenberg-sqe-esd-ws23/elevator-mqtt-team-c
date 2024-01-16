package at.fhhagenberg.sqelevator.model;


import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.property.Listener;
import at.fhhagenberg.sqelevator.property.Property;

public class Building {
  private List<Elevator> elevators;
  private List<Floor> floors;
  private final Property<Building,Integer> floorCount=new Property<Building,Integer>(this);
  private final Property<Building,Integer> elevatorCount=new Property<Building,Integer>(this);

  public Building() {
    elevators = new ArrayList<>();
    floors = new ArrayList<>();
  }

  public Building(List<Elevator> elevators, List<Floor> floors) {
    this.elevators = elevators;
    this.floors = floors;
  }

  public Elevator getElevator(int elevatorNumber) {
    if (elevatorNumber >= elevators.size() || elevatorNumber<0) {
      return null;
    }

    return elevators.get(elevatorNumber);
  }

  public Floor getFloor(int floorNumber) {
    if (floorNumber >= floors.size() || floorNumber<0) {
      return null;
    }

    return floors.get(floorNumber);
  }

  public void setElevators(List<Elevator> elevators) {
    this.elevators = elevators;
    elevatorCount.set(elevators.size());
  }

  public void setFloors(List<Floor> floors) {
    this.floors = floors;
    floorCount.set(floors.size());
  }

  public List<Elevator> getElevators() {
    return elevators;
  }

  public List<Floor> getFloors() {
    return floors;
  }

  public int getFloorNum() {
    return floors.size();
  }

  public int getElevatorNum() {
    return elevators.size();
  }

  public void addElevatorCountListner(Listener<Building,Integer> l)
  {
    elevatorCount.addListner(l);
  }
  public void addFloorCountListner(Listener<Building,Integer> l)
  {
    floorCount.addListner(l);
  }
}
