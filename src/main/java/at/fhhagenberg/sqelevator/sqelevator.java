package at.fhhagenberg.sqelevator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.service.ElevatorService;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;

public class sqelevator {

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
            sqelevator app = new sqelevator(p, controller, new MqttClient() {
            });
            app.run();
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }

    }

    Parser config;
    IElevator controller;
    MqttClient client;
    ElevatorService service;
    Building building;

    public sqelevator(Parser p, IElevator e, MqttClient r) {

        building = new Building();
        service = new ElevatorService(e, new BuildingUpdater(e, building), null);
    }

    public void run() {
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