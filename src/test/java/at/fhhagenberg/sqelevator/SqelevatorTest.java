package at.fhhagenberg.sqelevator;


import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqelevator.property.ListProperty;

class SqelevatorTest {
    @Test
    void testT(){
        ListProperty<Integer,Integer> l=new ListProperty<Integer,Integer>(1);
        l.set(123, 3);
    }
}