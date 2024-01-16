package at.fhhagenberg.sqelevatorController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.fhhagenberg.sqelevator.Parser;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.MqttService;
import at.fhhagenberg.sqelevator.service.impl.MqttServiceImpl;

public class Controller {

    private final int ElevatorNumber;
    Building building;
    MqttConnector conn;
    

    Controller(int rlnr,Building b,MqttConnector c) {
        ElevatorNumber=rlnr;
        building=b;
        conn=c;
    }

   
    void run(){
        if(building.getSpeed(ElevatorNumber)==0)
            {if(building.getDirection(ElevatorNumber)==Direction.UP)
            {
                int nextFloor=building.getCurrentFloor(ElevatorNumber);
                do{
                    nextFloor++;
                }while (nextFloor<building.getFloorCount()&&!(building.getUpButton(nextFloor)||building.getElevaorButton(ElevatorNumber, nextFloor)));
                if(nextFloor!=building.getFloorCount())
                {
                    conn.setTargetFloor(ElevatorNumber,nextFloor);
                    if(building.getElevaorButton(ElevatorNumber, nextFloor)) conn.setElevatorButton(ElevatorNumber, nextFloor, false);
                    if(building.getUpButton(nextFloor)) conn.setUpButton(nextFloor, false);
                }
                else
                {
                    conn.setDirection(ElevatorNumber,Direction.DOWN);
                }
            }
            else
            {
                int nextFloor=building.getCurrentFloor(ElevatorNumber);
                do{
                    nextFloor--;
                }while (nextFloor>0&&!(building.getDownButton(nextFloor)||building.getElevaorButton(ElevatorNumber, nextFloor)));
                if(nextFloor!=0)
                {
                    conn.setTargetFloor(ElevatorNumber,nextFloor);
                    if(building.getElevaorButton(ElevatorNumber, nextFloor)) conn.setElevatorButton(ElevatorNumber, nextFloor, false);
                    if(building.getDownButton(nextFloor)) conn.setDownButton(nextFloor, false);
                }
                else
                {
                    conn.setDirection(ElevatorNumber,Direction.UP);
                }
            }
        }
    }

    private static final Logger logger = LogManager.getLogger(Controller.class);
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
        List<Controller> controllers=new ArrayList<>();
        MqttService mqttService=new MqttServiceImpl(p.getMqttAddress(),p.getMqttPort());
        
        }
}
