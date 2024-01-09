package at.fhhagenberg.sqelevator.model;

import at.fhhagenberg.sqelevator.Property;

public class Floor {
  private final int floorNumber;
  public Property<Floor, Boolean> upButton = new Property<>(this);
  public Property<Floor, Boolean> downButton = new Property<>(this);


  public Floor(int floorNumber) {
    this.floorNumber = floorNumber;
  }

  public int getFloorNumber() {
    return floorNumber;
  }
}
