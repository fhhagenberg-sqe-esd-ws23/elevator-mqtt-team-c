package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Building {
  private List<Elevator> elevators;
  private List<Floor> floors;

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
  }

  public void setFloors(List<Floor> floors) {
    this.floors = floors;
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

}
