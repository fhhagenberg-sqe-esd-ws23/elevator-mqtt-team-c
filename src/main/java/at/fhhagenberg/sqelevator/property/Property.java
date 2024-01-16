package at.fhhagenberg.sqelevator.property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Property<F, T> {
    List<Listener<F, T>> listner = new ArrayList<>();
    T value;
    F ref;
    Predicate<T> pred;

    public Property(F f) {
        ref = f;
    }

    public Property(F f, Predicate<T> p) {
        ref = f;
        pred = p;
    } public Property(F f,T initial) {
        ref = f;
        value=initial;
    }

    public Property(F f,T initial, Predicate<T> p) {
        ref = f;
        pred = p;
        value=initial;
    }


    private void emmit(F a, T b) {
        for (Listener<F, T> l : listner) {
            l.call(a, b);
        }
    }

    public void addListner(Listener<F, T> l) {
        listner.add(l);
    }
    private boolean validate(T val)
    {
        if(pred==null)
        {
            return true;
        }
        else
        {
            return pred.test(val);
        }
    }
    public boolean set(T val) {
        if (validate(val) &&(value==null || !value.equals(val)) ) {
            value = val;
            emmit(ref, value);
        }
        return false;
    }

    public T get() {
        return value;
    }
}