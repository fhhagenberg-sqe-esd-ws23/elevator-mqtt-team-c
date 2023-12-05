package at.fhhagenberg.sqelevator;

public interface Listener<F,T> {
    void call(F obj,T Val);
}
