package at.fhhagenberg.sqelevator.update;

import java.rmi.RemoteException;

public interface IUpdater {
  boolean update() throws RemoteException;
}
