package at.fhhagenberg.sqelevator.converter;

import at.fhhagenberg.sqelevator.model.Direction;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.nio.ByteBuffer;

public class TypeConverter {
    public static int convert(Enum<?> enumData) {
        return enumData.ordinal();
    }

    public static <E extends Enum<E>> E convert(Mqtt5Publish mqtt5Publish, Class<E> enumClass) {
//        try {
            E[] enumConstants = enumClass.getEnumConstants();
            int enumIndex = ByteBuffer.wrap(mqtt5Publish.getPayloadAsBytes()).getInt();
            return enumConstants[enumIndex];
//        } catch (Exception e) {
//            // Handle exception - maybe return null or a default enum constant
//            e.printStackTrace();
//            return null; // or a default value
//        }
    }
}
