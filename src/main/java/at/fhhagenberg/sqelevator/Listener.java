package at.fhhagenberg.sqelevator;

public interface Listener<T> {
    void call(int id,T Val);
}
