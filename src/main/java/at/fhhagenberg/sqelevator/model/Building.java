package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Building {
  private List<Elevator> elevators;
  private List<Floor> floors;

  public Building(int numberOfFloors, int numberOfElevators) {
    floors = new ArrayList<>();
    elevators = new ArrayList<>();

    IntStream.range(0, numberOfFloors)
        .mapToObj(Floor::new)
        .forEachOrdered(floor -> floors.add(floor));

    IntStream.range(0, numberOfElevators)
        .mapToObj(i -> new Elevator())
        .forEachOrdered(elevator -> elevators.add(elevator));
  }
}
