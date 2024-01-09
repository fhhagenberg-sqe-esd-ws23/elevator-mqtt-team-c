package at.fhhagenberg.sqelevator.property;

public interface Listener<F,T> {
    void call(F obj,T Val);
}
