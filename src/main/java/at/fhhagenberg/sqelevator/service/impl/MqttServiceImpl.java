package at.fhhagenberg.sqelevator.service.impl;

import at.fhhagenberg.sqelevator.property.Listener;
import at.fhhagenberg.sqelevator.service.MqttService;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class MqttServiceImpl implements MqttService {

    private static final Logger logger = LogManager.getLogger(MqttServiceImpl.class);

    private final Mqtt5AsyncClient client;

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
    public void connect() throws CompletionException{

        CompletableFuture<Mqtt5ConnAck> connectionFuture = client.connect();

        connectionFuture.join();
    }


    @Override
    public void disconnect() {
        CompletableFuture<Void> disconnectFuture = client.disconnect();

        // Handle the future
        handleFuture(disconnectFuture);
    }

    @Override
    public void publish(String topic, String payload) {
        handleFuture(client.publishWith()
                .topic(topic)
                .retain(true)
                .payload(payload.getBytes()).send());
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
        CompletableFuture<Mqtt5SubAck> subscribeFuture = client.subscribeWith()
                .topicFilter(topic)
                .qos(MqttQos.EXACTLY_ONCE)
                .callback(publish -> callback.call(topic, publish))
                .send();

        // Handle the future
        handleFuture(subscribeFuture);
    }

    private <T> void handleFuture(CompletableFuture<T> future) {
        try {
            future.join();
        } catch (CompletionException e) {
            logger.error("Mqtt Connection Error : {}", e.getCause().getMessage());
        }
    }
}
