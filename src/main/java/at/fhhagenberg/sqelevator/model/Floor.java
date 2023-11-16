package at.fhhagenberg.sqelevator.model;

public class Floor {
  private int floorNumber;
  private boolean upButtonPressed;
  private boolean downButtonPressed;

  public Floor(int floorNumber) {
    this(floorNumber, false, false);
  }

  public Floor(int floorNumber, boolean upButtonPressed, boolean downButtonPressed) {
    this.floorNumber = floorNumber;
    this.upButtonPressed = upButtonPressed;
    this.downButtonPressed = downButtonPressed;
  }

  public int getFloorNumber() {
    return floorNumber;
  }

  public void setFloorNumber(int floorNumber) {
    this.floorNumber = floorNumber;
  }

  public boolean isUpButtonPressed() {
    return upButtonPressed;
  }

  public void setUpButtonPressed(boolean upButtonPressed) {
    this.upButtonPressed = upButtonPressed;
  }

  public boolean isDownButtonPressed() {
    return downButtonPressed;
  }

  public void setDownButtonPressed(boolean downButtonPressed) {
    this.downButtonPressed = downButtonPressed;
  }
}
