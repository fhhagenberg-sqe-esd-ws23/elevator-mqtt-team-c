package at.fhhagenberg.sqelevator.service.impl;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import at.fhhagenberg.sqelevator.property.Listener;
import at.fhhagenberg.sqelevator.service.MqttService;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class MqttServiceImpl implements MqttService {
  private Mqtt5AsyncClient client;

  public MqttServiceImpl(Mqtt5AsyncClient client) {
    this.client = client;
  }

  public MqttServiceImpl(String brokerUrl, int port) {
    this.client = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost(brokerUrl)
            .serverPort(port)
            .buildAsync();
  }

  @Override
  public void connect() throws Exception {

    CompletableFuture<Mqtt5ConnAck> connectionFuture = client.connect();

    // Handle the future
    handleFuture(connectionFuture);
  }


  @Override
  public void disconnect() throws Exception {
    CompletableFuture<Void> disconnectFuture = client.disconnect();

    // Handle the future
    handleFuture(disconnectFuture);
  }

  @Override
  public void publish(String topic, String payload) {
      try {
          handleFuture(client.publishWith()
              .topic(topic)
              .payload(payload.getBytes()).send());
      } catch (Exception e) {
        System.err.println("Publish failed: " + e.getMessage());

        throw new RuntimeException(e);
      }
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
  public void subscribe(String topic, Listener<String, Mqtt5Publish> callback) throws Exception {
    CompletableFuture<Mqtt5SubAck> subscribeFuture = client.subscribeWith()
            .topicFilter(topic)
            .qos(MqttQos.EXACTLY_ONCE)
            .callback((publish) -> callback.call(topic, publish))
            .send();

    // Handle the future
    handleFuture(subscribeFuture);
  }

  private <T> T handleFuture(CompletableFuture<T> future) throws Exception {
    try {
      return future.join(); // or use get() for throwing the original exception
    } catch (CompletionException e) {
      // Log the exception or handle it as needed
      System.err.println("Mqtt Connection Error : " + e.getCause().getMessage());
      throw (Exception) e.getCause(); // Rethrow the cause of the exception
    }
  }
}
