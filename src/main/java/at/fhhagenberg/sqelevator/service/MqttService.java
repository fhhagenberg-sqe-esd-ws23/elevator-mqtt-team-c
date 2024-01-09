package at.fhhagenberg.sqelevator.service;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import at.fhhagenberg.sqelevator.property.Listener;

import java.util.concurrent.ExecutionException;

public interface MqttService {
  void connect() throws Exception;
  void disconnect() throws Exception;
  void publish(String topic, String payload);

  void publish(String topic, int payload);

  void publish(String topic, boolean payload);

  void subscribe(String topic, Listener<String, Mqtt5Publish> callback) throws Exception;
}
