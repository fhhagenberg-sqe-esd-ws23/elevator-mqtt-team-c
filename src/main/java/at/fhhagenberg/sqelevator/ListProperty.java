package at.fhhagenberg.sqelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ListProperty<F, T extends Comparable<? super T> > {
    List<ListListener<F, T>> listner = new ArrayList<>();
    List<T> value = new ArrayList<>();
    F ref;
    Predicate<T> pred;

    public ListProperty(F f, Predicate<T> p) {
        ref = f;
        pred = p;
    }

    public ListProperty(F f) {
        ref = f;
    }

    public void addListner(ListListener<F, T> l) {
        listner.add(l);
    }

    public void emmit(F a, int i, T b) {
        for (ListListener<F, T> l : listner) {
            l.call(a, i, b);
        }
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
    public T get(int index) {
        return value.get(index);
    }
    public List<T> get(){return value;}
    public boolean set(T val, int index) {
        if (index < value.size()) {

            if (value.get(index) != val && validate(val)) {
                value.set(index, val);
                emmit(ref, index, val);
                return true;
            }
        }
        return false;
    }

    public void setSize(int size,T init) {
        
        value.clear();
        for(int i=0;i<size;i++)
        {
            value.add(init);
            emmit(ref, i, value.get(i));
        }
    }
}
