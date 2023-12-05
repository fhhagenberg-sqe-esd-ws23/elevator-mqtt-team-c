package at.fhhagenberg.sqelevator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void Test1() throws IllegalArgumentException, IOException{
        Parser uut=new Parser();
        InputStream is=new ByteArrayInputStream("plc=rmi://localhost/ElevatorSim\ninterval=20\nmqtt_prefix=tst\nmqtt_address=localhost\n".getBytes());
        uut.Parse(is);
        assertEquals("rmi://localhost/ElevatorSim", uut.getPlcAddress());
        assertEquals("localhost", uut.getMqttAddress());
        assertEquals(20, uut.getInterval());
        assertEquals("tst", uut.getMqttPrefix());
    }
    @Test
    public void Test2() throws IllegalArgumentException, IOException{
        Parser uut=new Parser();
        InputStream is=new ByteArrayInputStream("plc=rmi://localhost/ElevatorSim\n".getBytes());
        
        assertThrows(IllegalArgumentException.class, ()->uut.Parse(is));
        assertEquals(Parser.defaultInterval, uut.getInterval());
        assertEquals(Parser.defaultMqttPrefix, uut.getMqttPrefix());
        
    }@Test
    public void Test3() throws IllegalArgumentException, IOException{
        Parser uut=new Parser();
        InputStream is=new ByteArrayInputStream("".getBytes());
        
        assertThrows(IllegalArgumentException.class, ()->uut.Parse(is));
        
    }
    @Test
    public void TestParseString(){
        Parser uut=new Parser();
        String[] args={"-config","test"};
        
        assertEquals(args[1], uut.parse(args));
    }
    @Test
    public void TestParseString2(){
        Parser uut=new Parser();
        String[] args={};
        
        assertEquals(Parser.defaultConfigFile, uut.parse(args));
    }
    @Test
    public void TestParseString3(){
        Parser uut=new Parser();
        String[] args={"-verion","schmarrn"};
        
        assertEquals(Parser.defaultConfigFile, uut.parse(args));
    }
    
}
