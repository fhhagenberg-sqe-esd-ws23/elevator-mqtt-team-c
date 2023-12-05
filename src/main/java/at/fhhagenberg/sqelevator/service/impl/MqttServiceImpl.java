package at.fhhagenberg.sqelevator.service.impl;

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import at.fhhagenberg.sqelevator.service.MqttService;

public class MqttServiceImpl implements MqttService {
  private Mqtt5BlockingClient client;

  @Override
  public void connect(String brokerUrl, int port) {
    client = Mqtt5Client.builder()
        .serverPort(port)
        .identifier("publisher")
        .buildBlocking();

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
}
