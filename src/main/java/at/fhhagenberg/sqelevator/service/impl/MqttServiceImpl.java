package at.fhhagenberg.sqelevator.service.impl;

import at.fhhagenberg.sqelevator.Listener;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import at.fhhagenberg.sqelevator.service.MqttService;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.util.UUID;

public class MqttServiceImpl implements MqttService {
  private Mqtt5AsyncClient client;

  @Override
  public void connect(String brokerUrl, int port) {
    client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost(brokerUrl)
            .buildAsync();

    client.connect();
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
