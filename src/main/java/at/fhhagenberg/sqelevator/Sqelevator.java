package at.fhhagenberg.sqelevator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;


import at.fhhagenberg.sqelevator.controller.ElevatorController;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.service.ElevatorService;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.impl.MqttServiceImpl;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;
import sqelevator.IElevator;

public class Sqelevator {

    public static void main(String[] args) {
        // Options options = new Options();
        // options.addOption("config",true,"configfile");
        // CommandLineParser parser = new DefaultParser();
        // CommandLine cmd = parser.parse(options, args);
        Parser p = new Parser();
        var configFile = new File(p.parse(args));
        try {
            if (!configFile.exists()) {
                System.err.print(configFile.getAbsolutePath() + " not found");
                System.exit(-1);
            }
            p.Parse(new FileInputStream(configFile));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        IElevator controller;
        try {
            controller = (IElevator) Naming.lookup(p.getPlcAddress());

            MqttService mqttService = new MqttServiceImpl();
            mqttService.connect(p.getMqttAddress(), 1883);
            Sqelevator app = new Sqelevator(p, controller, mqttService);
            app.run(p);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        } catch (RuntimeException e) {
            System.err.println("Failed to connect to the MQTT broker: " + e.getMessage());
        }

    }

//    Parser config;
//    IElevator controller;
    MqttClient client;
    ElevatorService service;
    ElevatorController controller;
    Building building;

    public Sqelevator(Parser p, IElevator e, MqttService mqttService) throws RemoteException {

        building = new Building();
        
        service = new ElevatorService(e, new BuildingUpdater(e, building), new ArrayList<>());
        controller = new ElevatorController(e);

        // update building one time for MqttBuildingConnector
        service.update(building);

        //Todo: Does MqttBuildingConnector recognize elevator number changes?
        // bc this is done in Sqelevator ctor only
        new MqttBuildingConnector(mqttService, controller, building);
    }

    public void run(Parser config) {
        int interval = config.getInterval();
        while (true) {

            try {
                service.update(building);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}