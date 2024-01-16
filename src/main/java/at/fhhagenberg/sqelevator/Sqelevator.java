package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.controller.ElevatorController;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.service.ElevatorService;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.impl.MqttServiceImpl;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sqelevator.IElevator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sqelevator {

    public static void main(String[] args) throws Exception {
        Parser p = new Parser();
        var configFile = new File(p.parse(args));
        try {
            if (!configFile.exists()) {
                logger.error("File not found: {}", configFile.getAbsolutePath());
                System.exit(-1);
            }
            p.Parse(new FileInputStream(configFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }
        IElevator controller;
        try {
            controller = (IElevator) Naming.lookup(p.getPlcAddress());

            MqttService mqttService = new MqttServiceImpl(p.getMqttAddress(), 1883);
            mqttService.connect();
            Sqelevator app = new Sqelevator(controller, mqttService);
            app.run(p);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {

            logger.error("Failed to connect RMI: {}", e.getMessage(), e);
            System.exit(-1);
        } catch (RuntimeException e) {
            logger.error("Failed to connect to the MQTT broker: {}", e.getMessage(), e);
        }
    }

    ElevatorService service;
    ElevatorController controller;
    Building building;

    private static AtomicBoolean stopFlag = new AtomicBoolean(false);

    private static final Logger logger = LogManager.getLogger(Sqelevator.class);

    public Sqelevator(IElevator e, MqttService mqttService) throws Exception {

        building = new Building();
        
        service = new ElevatorService(e, new BuildingUpdater(e, building), new ArrayList<>());
        controller = new ElevatorController(e);

        // update building one time for MqttBuildingConnector
        service.update(building);

        //Todo: Does MqttBuildingConnector recognize elevator number changes?
        // bc this is done in Sqelevator ctor only
        MqttBuildingConnector.connect(mqttService, controller, building);
    }

    public void run(Parser config) {
        int interval = config.getInterval();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        try {
            executor.scheduleAtFixedRate(() -> {
                if (stopFlag.get()) {
                    executor.shutdown();
                    return;
                }

                try {
                    service.update(building);
                } catch (RemoteException e) {
                    e.printStackTrace();

                    throw new RuntimeException(e);
                }
            }, 0, interval, TimeUnit.MILLISECONDS);

            // todo: implement stopFlag logic
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
