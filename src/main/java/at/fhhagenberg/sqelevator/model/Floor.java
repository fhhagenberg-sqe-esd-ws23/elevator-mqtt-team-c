package at.fhhagenberg.sqelevator.model;

public class Floor {
  private final int floorNumber;
  private Boolean upButton=false;
  private Boolean downButton=false;

  public Floor(int floorNumber) {
    this.floorNumber = floorNumber;
  }

  public int getFloorNumber() {
    return floorNumber;
  }

  public Boolean getUpButton() {
    return upButton;
  }

  public void setUpButton(boolean pressed) {
    this.upButton=pressed;
  }

  public Boolean getDownButton() {
    return downButton;
  }

  public void setDownButton(boolean pressed) {
    this.downButton =pressed;
  }
}
