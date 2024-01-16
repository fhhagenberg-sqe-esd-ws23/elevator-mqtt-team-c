package at.fhhagenberg.sqelevator.service;

import at.fhhagenberg.sqelevator.property.Listener;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.util.concurrent.CompletionException;

public interface MqttService {
  void connect() throws CompletionException;
  void disconnect();
  void publish(String topic, String payload);

  void publish(String topic, int payload);

  void publish(String topic, boolean payload);

  void subscribe(String topic, Listener<String, Mqtt5Publish> callback);
}
