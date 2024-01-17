package at.fhhagenberg.sqelevator;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.util.ArrayList;
import java.util.List;

public class MqttParser {

    private MqttParser(){ }
    public static class Ret<T,F>{
        public T value;
        public List<F> topics;
    }
    public interface IvalueOf<T> {
        T valueOf(String t);
    }
    public static <T,F> Ret<T, F> parse(String topic,Mqtt5Publish publish,IvalueOf<T> t,IvalueOf<F> f)
    {
        Ret<T,F> r=new Ret<>();
        r.topics=new ArrayList<>();
        String[] received=publish.getTopic().toString().split("/");
        String[] listened=topic.split("/");
        int i=0;
        for(;i<listened.length;i++)
        {
            if(listened[i].equals("+")||listened[i].equals("#"))
            {
                r.topics.add(f.valueOf(received[i]));
            }
        }
        for(;i<received.length;i++)
        {
            r.topics.add(f.valueOf(received[i]));
        }
        r.value=t.valueOf(new String(publish.getPayloadAsBytes()));

        return r;
    }
}
