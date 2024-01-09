package at.fhhagenberg.sqelevator;

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
    }

    public void emmit(F a, T b) {
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
        if (value != val && validate(val)) {
            value = val;
            emmit(ref, value);
        }
        return false;
    }

    public T get() {
        return value;
    }
}