package at.fhhagenberg.sqelevator.property;

public interface ListListener<F,T> {
    void call(F obj,int index,T Val);
}
