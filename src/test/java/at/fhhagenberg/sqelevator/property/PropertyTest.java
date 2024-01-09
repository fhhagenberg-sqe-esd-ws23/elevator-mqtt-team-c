package at.fhhagenberg.sqelevator.property;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.function.Predicate;

import org.junit.Test;
import org.mockito.Mockito;

public class PropertyTest {
    @Test
    public void TestCtorInitial() {
        Object o=new Object();
        Integer val1=13;
        Property<Object,Integer> uut = new Property<Object,Integer>(o,val1);
        Integer val2=12;
        assertEquals(val1,uut.get());
        uut.set(val2);
        assertEquals(val2,uut.get());
    }
    @Test
    public void TestCtorInitialPredFalse() {
        Object o=new Object();
        Integer val1=13;
        Predicate<Integer> f=Mockito.mock(Predicate.class);
        Property<Object,Integer> uut = new Property<Object,Integer>(o,val1,f);
        Integer val2=12;
        Mockito.when(f.test(anyInt())).thenReturn(false);
        assertEquals(val1,uut.get());
        uut.set(val2);
        assertEquals(val1,uut.get());
        Mockito.verify(f, Mockito.times(1)).test(12);
    }
    @Test
    public void TestSetGet() {
        Object o=new Object();
        Property<Object,Integer> uut = new Property<Object,Integer>(o);
        Integer val=12;
        assertEquals(null,uut.get());
        uut.set(val);
        assertEquals(val,uut.get());
    }
    @Test
    public void TestSetGetListner() {
        Object o=new Object();
        Property<Object,Integer> uut = new Property<Object,Integer>(o);
        Integer val=12;
        uut.addListner((a,b)->{
            assertEquals(o,a);
            assertEquals(val, b);
        });
        uut.set(val);
        assertEquals(val,uut.get());
        
    }

    @Test
    public void TestSetGetPredicatTrue() {
        Object o=new Object();
        Predicate<Integer> f=Mockito.mock(Predicate.class);
        Listener<Object,Integer> l = Mockito.mock(Listener.class);
        Property<Object,Integer> uut = new Property<Object,Integer>(o,f);
        Integer val=12;
        uut.addListner(l);
        Mockito.when(f.test(val)).thenReturn(true);
        uut.set(val);
        assertEquals(val,uut.get());
        Mockito.verify(f, Mockito.times(1)).test(12);        
        Mockito.verify(l, Mockito.times(1)).call(o,12);        
    }
    @Test
    public void TestSetGetPredicatFalse() {
        Object o=new Object();
        Predicate<Integer> f=Mockito.mock(Predicate.class);
        Listener<Object,Integer> l = Mockito.mock(Listener.class);
        Property<Object,Integer> uut = new Property<Object,Integer>(o,f);
        Integer val=12;
        uut.addListner(l);
        Mockito.when(f.test(val)).thenReturn(false);
        uut.set(val);
        assertEquals(null,uut.get());
        Mockito.verify(f, Mockito.times(1)).test(12);        
        Mockito.verify(l, Mockito.times(0)).call(o,12);        
    }
}
