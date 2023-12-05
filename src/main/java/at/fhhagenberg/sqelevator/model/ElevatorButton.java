package at.fhhagenberg.sqelevator.model;

import at.fhhagenberg.sqelevator.listner;

public class ElevatorButton extends Button {
  private boolean serverd;
  private final Floor floor;
  public listner stateListner;
  public listner serverListner;

  public ElevatorButton(Floor floor) {
    this.floor = floor;
  }

  public Floor getFloor() {
    return floor;
  }

  public boolean isServerd() {
    return serverd;
  }

  public void setServed(boolean b) {
    if (serverd != b) {
      serverd = b;
      if (serverListner != null)
        serverListner.call(0, floor);
    }
  }
}
