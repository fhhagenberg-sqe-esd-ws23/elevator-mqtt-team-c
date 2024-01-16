package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.controller.ElevatorController;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.service.ElevatorService;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.ScheduleService;
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
import java.util.TimerTask;

public class Sqelevator {
    public static void main(String[] args) {
        Parser p = new Parser();
        var configFile = new File(p.parseArguments(args));
        try {
            if (!configFile.exists()) {
                logger.error("File not found: {}", configFile.getAbsolutePath());
                System.exit(-1);
            }
            p.parseFile(new FileInputStream(configFile));
        } catch (IOException e) {
            logger.error("Error parsing file: {}", configFile.getAbsolutePath(), e);
            System.exit(-1);
        }
        try {
            IElevator controller = (IElevator) Naming.lookup(p.getPlcAddress());

            MqttService mqttService = new MqttServiceImpl(p.getMqttAddress(), p.getMqttPort());
            mqttService.connect();
            Sqelevator app = new Sqelevator(controller, mqttService);
            app.run(p);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            logger.error("Failed to connect RMI: {}", e.getMessage(), e);
            System.exit(-1);
        } catch (RuntimeException e) {
            logger.error("Failed to connect to the MQTT broker: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("unknown error",e.getMessage(),e);
        }
    }

    ElevatorService service;
    ElevatorController elevatorController;

    ScheduleService scheduler;
    Building building;

    private static final Logger logger = LogManager.getLogger(Sqelevator.class);

    public Sqelevator(IElevator e, MqttService mqttService) throws Exception {

        building = new Building();
        
        service = new ElevatorService(e, new BuildingUpdater(e, building), new ArrayList<>());
        elevatorController = new ElevatorController(e);
        scheduler = new ScheduleService();

        MqttBuildingConnector.preConnect(mqttService, elevatorController, building);
        // update building one time for MqttBuildingConnector
        service.update(building);

        MqttBuildingConnector.connect(mqttService, elevatorController, building, scheduler);
    }

    public void run(Parser config) {
        int interval = config.getInterval();

        scheduler.start(new TimerTask() {
            public void run() {
                try {
                    service.update(building);
                } catch (RemoteException e) {
                    logger.error("Error updating PLC ", e);
                    scheduler.stop();
                }
            }
        }, interval);
    }
}
