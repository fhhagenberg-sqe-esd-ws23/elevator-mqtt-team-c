package at.fhhagenberg.sqelevator;

public interface ListListener<F,T> {
    void call(F obj,int index,T Val);
}
