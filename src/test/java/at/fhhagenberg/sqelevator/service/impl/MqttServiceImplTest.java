package at.fhhagenberg.sqelevator.service.impl;

import at.fhhagenberg.sqelevator.property.Listener;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.hivemq.HiveMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;


@Testcontainers
class MqttServiceImplTest {
    @Container
    private final HiveMQContainer mqttBroker = new HiveMQContainer(DockerImageName.parse("hivemq/hivemq-ce:latest"))
            .withExposedPorts(1883);

    private MqttServiceImpl mqttService;

    @BeforeEach
    void setUp() {
        mqttBroker.start();
        String brokerUrl = mqttBroker.getHost();
        int port = mqttBroker.getFirstMappedPort();
        mqttService = new MqttServiceImpl(brokerUrl, port);
    }

    @AfterEach
    void tearDown() {
        mqttBroker.stop();
    }

    @Test
    void testConnect_containerUnavailable() {
        MqttServiceImpl clientFailure = new MqttServiceImpl("169.221.211.1", 55555);
        Assertions.assertThrows(CompletionException.class, clientFailure::connect);
    }


    @Test
    void testConnect() {
        assertDoesNotThrow(() -> mqttService.connect());
    }

    @Test
    void testDisconnect() {
        mqttService.connect();
        assertDoesNotThrow(() -> mqttService.disconnect());
    }

    @Test
    void testPublish() {
        mqttService.connect();
        assertDoesNotThrow(() -> mqttService.publish("test/topic", "Hello"));
    }

    @Test
    void testSubscribe() {
        AtomicBoolean messageReceived = new AtomicBoolean(false);
        Listener<String, Mqtt5Publish> listener = (topic, msg) -> messageReceived.set(true);

        mqttService.connect();
        mqttService.subscribe("test/topic", listener);

        assertFalse(messageReceived.get());

        mqttService.publish("test/topic", "Hello");

        await().atMost(Duration.ofMillis(500)).until(messageReceived::get);
        assertTrue(messageReceived.get());
    }

}
