package at.fhhagenberg.sqelevator;

import java.util.ArrayList;
import java.util.List;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

public class MqttParser {

    public static class Ret<T,F>{
        public T value;
        public F[] topics;
    }
    public interface IvalueOf<T> {
        T valueOf(String t);
    }
    public static <T,F> Ret parse(String topic,Mqtt5Publish publish,IvalueOf<T> t,IvalueOf<F> f)
    {
        Ret<T,F> r=new Ret();
        List<F> topics=new ArrayList<>();
        String[] received=publish.getTopic().toString().split("/");
        String[] listened=topic.split("/");
        int i=0;
        for(;i<listened.length;i++)
        {
            if(listened[i].equals("+")||listened[i].equals("#"))
            {
                topics.add(f.valueOf(received[i]));
            }
        }
        for(;i<received.length;i++)
        {
            topics.add(f.valueOf(received[i]));
        }
        r.value=t.valueOf(new String(publish.getPayloadAsBytes()));
        r.topics=(F[]) topics.toArray();
        return r;
    }
}
