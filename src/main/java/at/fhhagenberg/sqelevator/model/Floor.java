package at.fhhagenberg.sqelevator.model;

public class Floor {
  private final int floorNumber;
  private final Button upButton;
  private final Button downButton;

  public Floor(int floorNumber) {
    this.floorNumber = floorNumber;
    upButton = new Button();
    downButton = new Button();
  }

  public int getFloorNumber() {
    return floorNumber;
  }

  public Button getUpButton() {
    return upButton;
  }

  public void setUpButton(boolean pressed) {
    this.upButton.setPressed(pressed);
  }

  public Button getDownButton() {
    return downButton;
  }

  public void setDownButton(boolean pressed) {
    this.downButton.setPressed(pressed);
  }
}
