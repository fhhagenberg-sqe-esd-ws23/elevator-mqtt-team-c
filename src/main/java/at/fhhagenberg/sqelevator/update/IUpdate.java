package at.fhhagenberg.sqelevator.update;

import java.rmi.RemoteException;

public interface IUpdate<T> {
  boolean update() throws RemoteException;
}
