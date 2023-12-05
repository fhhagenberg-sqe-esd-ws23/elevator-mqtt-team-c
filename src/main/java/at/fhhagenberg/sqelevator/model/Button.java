package at.fhhagenberg.sqelevator.model;

import at.fhhagenberg.sqelevator.Listener;

public class Button {
  public Listener<Button,Boolean> stateListner;
  private boolean pressed;

  public Button() {
    this.pressed = false;
  }

  public boolean isPressed() {
    return pressed;
  }

  public void setPressed(boolean pressed) {
    if(this.pressed!=pressed)
    {
      this.pressed = pressed;
      if(stateListner!=null) stateListner.call(this,pressed);
    }
  }
}
