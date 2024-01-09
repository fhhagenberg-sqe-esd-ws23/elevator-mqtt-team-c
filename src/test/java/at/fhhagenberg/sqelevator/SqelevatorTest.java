package at.fhhagenberg.sqelevator;


import org.junit.jupiter.api.Test;

public class SqelevatorTest {
    @Test
    public void testT(){
        ListProperty<Integer,Integer> l=new ListProperty<Integer,Integer>(1);
        l.set(123, 3);
    }
//     @Mock
//   private IElevator controller;
//   @Mock
//   private Parser parser;
//   @Mock
//   private MqttService mservice;
  
  
//     @Test
//     void Test(){
        
//         Sqelevator service=new Sqelevator(parser, e, null);
//         service.run();
//     }
}