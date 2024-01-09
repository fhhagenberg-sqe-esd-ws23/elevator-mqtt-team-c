package at.fhhagenberg.sqelevator.service.impl;

import at.fhhagenberg.sqelevator.property.Listener;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
public class MqttServiceImplTest {
    @Container
    private GenericContainer<?> mqttBroker = new GenericContainer<>(DockerImageName.parse("hivemq/hivemq-ce:latest"))
            .withExposedPorts(1883);

    private MqttServiceImpl mqttService;

    @BeforeEach
    public void setUp() {
        mqttBroker.start();
        String brokerUrl = mqttBroker.getHost();
        int port = mqttBroker.getFirstMappedPort();
        mqttService = new MqttServiceImpl(brokerUrl, port);
    }

    @AfterEach
    public void tearDown() {
        mqttBroker.stop();
    }

    @Test
    public void testConnect_containerUnavailable() {
        MqttServiceImpl clientFailure = new MqttServiceImpl("169.221.211.1", 55555);
        Assertions.assertThrows(RuntimeException.class, clientFailure::connect);
    }


    @Test
    public void testConnect() throws Exception {
        assertDoesNotThrow(() -> mqttService.connect());
    }

    @Test
    public void testDisconnect() throws Exception {
        mqttService.connect();
        assertDoesNotThrow(() -> mqttService.disconnect());
    }

    @Test
    public void testPublish() throws Exception {
        mqttService.connect();
        assertDoesNotThrow(() -> mqttService.publish("test/topic", "Hello"));
    }

    @Test
    public void testSubscribe() throws Exception {
        AtomicBoolean messageReceived = new AtomicBoolean(false);
        Listener<String, Mqtt5Publish> listener = (topic, msg) -> messageReceived.set(true);

        mqttService.connect();
        mqttService.subscribe("test/topic", listener);

        mqttService.publish("test/topic", "Hello");
        // Add a small delay to allow message processing
        Thread.sleep(500);

        assertTrue(messageReceived.get());
    }

}
