package at.fhhagenberg;

import at.fhhagenberg.sqelevator.Parser;
import at.fhhagenberg.sqelevator.Sqelevator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.ElevatorService;
import at.fhhagenberg.sqelevator.service.impl.MqttServiceImpl;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;
import at.fhhagenberg.sqelevatorcontroller.Building;
import at.fhhagenberg.sqelevatorcontroller.Controller;
import at.fhhagenberg.sqelevatorcontroller.ControllerRunner;
import at.fhhagenberg.sqelevatorcontroller.MqttConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.hivemq.HiveMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import sqelevator.IElevator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Testcontainers
@ExtendWith(MockitoExtension.class)
class GeneralApplicationTest {

    @Container
    private final HiveMQContainer mqttBroker = new HiveMQContainer(DockerImageName.parse("hivemq/hivemq-ce:latest"))
            .withExposedPorts(1883);
    @Mock
    private IElevator plc;

    @Mock
    private Parser parser;

    private MqttServiceImpl mqttService;
    private MqttServiceImpl mqttServiceController;

    private Building building;
    private at.fhhagenberg.sqelevator.model.Building buildingModel;
    private MqttConnector mqttConnector;

    @BeforeEach
    void setUp() {
        mqttBroker.start();
        String brokerUrl = mqttBroker.getHost();
        int port = mqttBroker.getFirstMappedPort();

        mqttService = new MqttServiceImpl(brokerUrl, port);
        mqttServiceController = new MqttServiceImpl(brokerUrl, port);
        mqttService.connect();
        mqttServiceController.connect();

        mqttConnector = new MqttConnector(mqttServiceController);
        building = new Building();
        buildingModel = new at.fhhagenberg.sqelevator.model.Building();
        mqttConnector.connect(building);
    }

    @AfterEach
    void tearDown() {
        mqttBroker.stop();
    }

    private int getInt() {
        return 123;
    }

    @Test
    void testScenario() throws Exception {

        Mockito.when(plc.getFloorNum()).thenReturn(10);
        Mockito.when(plc.getElevatorNum()).thenReturn(2);
        Mockito.when(plc.getFloorButtonDown(Mockito.anyInt())).thenReturn(false);
        Mockito.when(plc.getFloorButtonDown(5)).thenReturn(false).thenReturn(true);
        Mockito.when(plc.getFloorButtonDown(8)).thenReturn(false).thenReturn(true);

        // create ElevatorService, Building and Algorithm instance
        Sqelevator app = new Sqelevator(plc, mqttService);
        ControllerRunner runner = new ControllerRunner();

        Controller controller = new Controller(0, building, mqttConnector);
        ElevatorService elevatorService = new ElevatorService(plc, new BuildingUpdater(plc, buildingModel), new ArrayList<>());

        controller.run();
        elevatorService.update(buildingModel);
        Assertions.assertEquals(2, plc.getElevatorNum());
    }
}
