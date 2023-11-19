package at.fhhagenberg.sqelevator.model;

public class ElevatorButton extends Button{
  private final Floor floor;

  public ElevatorButton(Floor floor) {
    this.floor = floor;
  }

  public Floor getFloor() {
    return floor;
  }
}
