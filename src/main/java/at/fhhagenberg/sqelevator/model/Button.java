package at.fhhagenberg.sqelevator.model;

public class Button {
  private boolean pressed;

  public Button() {
    this.pressed = false;
  }

  public boolean isPressed() {
    return pressed;
  }

  public void setPressed(boolean pressed) {
    this.pressed = pressed;
  }
}
