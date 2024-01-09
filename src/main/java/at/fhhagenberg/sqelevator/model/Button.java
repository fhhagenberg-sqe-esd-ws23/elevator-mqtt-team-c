package at.fhhagenberg.sqelevator.model;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.property.Listener;

public class Button<F>  {
  public List<Listener<F,Boolean>> stateListners;
  private boolean pressed;
  private F ref;
  public Button() {
    this.pressed = false;
    stateListners=new ArrayList<>();
  }

  public boolean isPressed() {
    return pressed;
  }

  public boolean setPressed(boolean pressed) {
    if(this.pressed!=pressed)
    {
      for(Listener<F,Boolean> l:stateListners)
      {
        l.call(ref,pressed);
      }
      return true;
    }
    return false;
  }

  // // @Override
  // public void addListner(Listener<F, Boolean> l) {
  //   stateListners.add(l);
  // }

  // // @Override
  // public boolean set(Boolean val) {
  //   return setPressed(val);
  // }

  // // @Override
  // public Boolean get() {
  //   return isPressed();
  // }
}
