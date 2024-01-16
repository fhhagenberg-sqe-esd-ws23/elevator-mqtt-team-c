package at.fhhagenberg.sqelevator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void Test1() throws IllegalArgumentException, IOException{
        Parser uut=new Parser();
        InputStream is=new ByteArrayInputStream("plc=rmi://localhost/ElevatorSim\ninterval=20\nmqtt_prefix=tst\nmqtt_address=localhost\n".getBytes());
        uut.parseFile(is);
        assertEquals("rmi://localhost/ElevatorSim", uut.getPlcAddress());
        assertEquals("localhost", uut.getMqttAddress());
        assertEquals(20, uut.getInterval());
        assertEquals("tst", uut.getMqttPrefix());
    }
    @Test
    void Test2() throws IllegalArgumentException {
        Parser uut=new Parser();
        InputStream is=new ByteArrayInputStream("plc=rmi://localhost/ElevatorSim\n".getBytes());
        
        assertThrows(IllegalArgumentException.class, ()->uut.parseFile(is));
        assertEquals(Parser.DEFAULT_INTERVAL, uut.getInterval());
        assertEquals(Parser.DEFAULT_MQTT_PREFIX, uut.getMqttPrefix());
        
    }@Test
    void Test3() throws IllegalArgumentException {
        Parser uut=new Parser();
        InputStream is=new ByteArrayInputStream("".getBytes());
        
        assertThrows(IllegalArgumentException.class, ()->uut.parseFile(is));
        
    }
    @Test
    void TestParseString(){
        Parser uut=new Parser();
        String[] args={"-config","test"};
        
        assertEquals(args[1], uut.parseArguments(args));
    }
    @Test
    void TestParseString2(){
        Parser uut=new Parser();
        String[] args={};
        
        assertEquals(Parser.DEFAULT_CONFIG_FILE, uut.parseArguments(args));
    }
    @Test
    void TestParseString3(){
        Parser uut=new Parser();
        String[] args={"-verion","schmarrn"};
        
        assertEquals(Parser.DEFAULT_CONFIG_FILE, uut.parseArguments(args));
    }
    
}
