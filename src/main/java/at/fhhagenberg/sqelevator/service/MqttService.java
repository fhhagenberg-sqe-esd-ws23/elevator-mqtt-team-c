package at.fhhagenberg.sqelevator.service;

import at.fhhagenberg.sqelevator.Listener;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

public interface MqttService {
  void connect(String brokerUrl, int port) throws RuntimeException ;
  void disconnect();
  void publish(String topic, String payload);

  void publish(String topic, int payload);

  void publish(String topic, boolean payload);

  void subscribe(String topic, Listener<String, Mqtt5Publish> callback);
}
