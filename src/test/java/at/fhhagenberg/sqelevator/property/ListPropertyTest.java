package at.fhhagenberg.sqelevator.property;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListPropertyTest {
    @Test
    void TestSetGet() {
        Object o=new Object();
        ListProperty<Object,Integer> uut = new ListProperty<Object,Integer>(o);
        Integer val=13;
        assertThrows(Exception.class, ()->{
            uut.get(1);
        });
        assertEquals(List.of(),uut.get());
        uut.setSize(2, 12);
        assertEquals(List.of(12,12),uut.get());
        uut.set(val,0);
        assertEquals(val, uut.get(0));
    }
    @Test
    void TestSetGetListner() {
        Object o=new Object();
        ListListener<Object,Integer> l = Mockito.mock(ListListener.class);
        ListProperty<Object,Integer> uut = new ListProperty<Object,Integer>(o);
        Integer val=13;
        uut.addListner(l);
        assertThrows(Exception.class, ()->{
            uut.get(1);
        });
        assertEquals(List.of(),uut.get());
        uut.setSize(2, 12);
        assertEquals(List.of(12,12),uut.get());
        
        uut.set(val,0);
        assertEquals(val, uut.get(0));
        Mockito.verify(l,Mockito.times(1)).call(o, 0, 12);
        Mockito.verify(l,Mockito.times(1)).call(o, 1, 12);
        Mockito.verify(l,Mockito.times(1)).call(o, 0, val);
    }

    @Test
    void TestSetGetPredicatTrue() {
        Predicate<Integer> f=Mockito.mock(Predicate.class);
        Object o=new Object();
        ListListener<Object,Integer> l = Mockito.mock(ListListener.class);
        ListProperty<Object,Integer> uut = new ListProperty<Object,Integer>(o,f);
        Integer val=13;
        uut.addListner(l);
        assertThrows(Exception.class, ()->{
            uut.get(1);
        });
        Mockito.when(f.test(val)).thenReturn(true);
        assertEquals(List.of(),uut.get());
        uut.setSize(2, 12);
        assertEquals(List.of(12,12),uut.get());
        
        uut.set(val,0);
        assertEquals(val, uut.get(0));
        Mockito.verify(l,Mockito.times(1)).call(o, 0, 12);
        Mockito.verify(l,Mockito.times(1)).call(o, 1, 12);
        Mockito.verify(l,Mockito.times(1)).call(o, 0, val);
        Mockito.verify(f, Mockito.times(1)).test(13);        
    }
    @Test
    void TestSetGetPredicatFalse() {
        Predicate<Integer> f=Mockito.mock(Predicate.class);
        Object o=new Object();
        ListListener<Object,Integer> l = Mockito.mock(ListListener.class);
        ListProperty<Object,Integer> uut = new ListProperty<Object,Integer>(o,f);
        Integer val=12;
        uut.addListner(l);
        assertThrows(Exception.class, ()->{
            uut.get(1);
        });
        assertEquals(List.of(),uut.get());
        uut.setSize(2, val);
        assertEquals(List.of(val,val),uut.get());
        
        uut.set(13,0);
        assertEquals(val, uut.get(0));
        Mockito.verify(l,Mockito.times(1)).call(o, 0, val);
        Mockito.verify(l,Mockito.times(1)).call(o, 1, val);
        Mockito.verify(l,Mockito.times(0)).call(o, 0, 13);
        Mockito.verify(f, Mockito.times(1)).test(13);       
    }
}
