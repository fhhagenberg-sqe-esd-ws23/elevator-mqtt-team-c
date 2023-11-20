package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

  public Building(int numberOfFloors, int numberOfElevators) {
    floors = new ArrayList<>();
    elevators = new ArrayList<>();

    for (int i = 0; i < numberOfFloors; i++) {
      Floor floor = new Floor(i);
      floors.add(floor);
    }

    IntStream.range(0, numberOfElevators)
        .mapToObj(Elevator::new)
        .forEachOrdered(elevators::add);
  }

  public Elevator getElevator(int elevatorNumber) {
    if (elevatorNumber >= elevators.size()) {
      return null;
    }

    return elevators.get(elevatorNumber);
  }

  public Floor getFloor(int floorNumber) {
    if (floorNumber >= elevators.size()) {
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
