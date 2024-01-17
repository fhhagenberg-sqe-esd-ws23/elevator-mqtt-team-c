package at.fhhagenberg.sqelevator;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

public class MqttParserTest {
    private String s;
    static MqttParserTest valueOf(String s){
        MqttParserTest a=new MqttParserTest();
        a.s=s;
        return a;
    }
    @Test
    void singleWildcardTest(){
        String ListhenTopic="Hallo/A/+/b";
        String recivedTopic="Hallo/A/5/b";
        String valueString="TEASD";

        Mqtt5Publish p=Mqtt5Publish.builder().topic(recivedTopic).payload(ByteBuffer.wrap(valueString.getBytes())).build();
        MqttParser.Ret x=MqttParser.parse(ListhenTopic,p,MqttParserTest::valueOf,Integer::valueOf);
        assertEquals(5, x.topics.get(0));
        assertEquals(1, x.topics.size());
        assertEquals(valueString, ((MqttParserTest)x.value).s);

    }
    @Test
    void doubleWildecardTest(){
        String ListhenTopic="Hallo/A/+/b/+/s";
        String recivedTopic="Hallo/A/5/b/1/s";
        String valueString="TEASD";

        Mqtt5Publish p=Mqtt5Publish.builder().topic(recivedTopic).payload(ByteBuffer.wrap(valueString.getBytes())).build();
        MqttParser.Ret x=MqttParser.parse(ListhenTopic,p,MqttParserTest::valueOf,Integer::valueOf);
        assertEquals(5, x.topics.get(0));
        assertEquals(1, x.topics.get(1));
        assertEquals(2, x.topics.size());
        assertEquals(valueString, ((MqttParserTest)x.value).s);
    }

    @Test
    void WildcardTest(){
        String ListhenTopic="Hallo/A/#";
        String recivedTopic="Hallo/A/5/b/1/s";
        String valueString="TEASD";

        Mqtt5Publish p=Mqtt5Publish.builder().topic(recivedTopic).payload(ByteBuffer.wrap(valueString.getBytes())).build();
        MqttParser.Ret x=MqttParser.parse(ListhenTopic,p,MqttParserTest::valueOf,String::valueOf);
        assertEquals("5", x.topics.get(0));
        assertEquals("b", x.topics.get(1));
        assertEquals("1", x.topics.get(2));
        assertEquals("s", x.topics.get(3));
        assertEquals(4, x.topics.size());
        assertEquals(valueString, ((MqttParserTest)x.value).s);
    }
}
