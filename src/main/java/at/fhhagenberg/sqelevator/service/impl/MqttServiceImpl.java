package at.fhhagenberg.sqelevator.service.impl;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import at.fhhagenberg.sqelevator.property.Listener;
import at.fhhagenberg.sqelevator.service.MqttService;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.util.UUID;

public class MqttServiceImpl implements MqttService {
  private Mqtt5AsyncClient client;

  @Override
  public void connect(String brokerUrl, int port) throws RuntimeException {
    client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost(brokerUrl)
            .buildAsync();

    client.connect().whenComplete((connAck, throwable) -> {
      if (throwable != null) {
        // Handle connection error
        System.err.println("Failed to connect to the MQTT broker: " + throwable.getMessage());

        throw new RuntimeException("Failed to connect to the MQTT broker");
      } else {
        // Connection established successfully
        System.out.println("Connected to the MQTT broker successfully.");
      }
    });
  }

  @Override
  public void disconnect() {
    client.disconnect();
  }

  @Override
  public void publish(String topic, String payload) {
    client.publishWith()
        .topic(topic)
        .payload(payload.getBytes()).send();
  }

  @Override
  public void publish(String topic, int payload) {
    publish(topic, String.valueOf(payload));
  }

  @Override
  public void publish(String topic, boolean payload) {
    publish(topic, String.valueOf(payload));
  }

  @Override
  public void subscribe(String topic, Listener<String, Mqtt5Publish> callback) {
    client.subscribeWith()
            .topicFilter(topic)
            .qos(MqttQos.EXACTLY_ONCE)
            .callback((publish) -> callback.call(topic, publish))
            .send();

  }
}
