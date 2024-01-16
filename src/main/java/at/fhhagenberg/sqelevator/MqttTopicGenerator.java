package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;

public class MqttTopicGenerator {
    public static String elPath(Elevator id,String p)
    {
        return "elevator/" + id.getElevatorNumber()+"/"+p;
    }
    public static String elPath(int id,String p)
    {
        return "elevator/" + id +"/"+p;
    }
    public static String elPath(char id,String p)
    {
        return "elevator/" + id +"/"+p;
    }
    public static String flPath(Floor id,String p)
    {
        return flPath(id.getFloorNumber() ,p);
    }
    public static String flPath(char id,String p)
    {
        return "floor/" + id +"/"+p;
    }
    private static String flPath(String id, String p)
    {
        return "floor/" + id +"/"+p;
    }
    public static String flPath(Integer id,String p)
    {
        return flPath(String.valueOf(id) ,p);
    }
    public static final String acceleration="accel";
    public static final String direction="direction";
    public static final String currentFloor="floor";
    public static final String currentPosition="curentPos";
    public static final String speed="curentspeed";
    public static final String weight="weight";
    public static final String doorStatus="doorState";
    public static final String floorBtn="Button";
    public static final String servedFloor="served";
    public static final String targetFloor="targetfloor";
    public static final String btnUp="btn/up";
    public static final String btnDown="btn/down";
    public static final String elevators = "elevators";
    public static final String floors = "elevators";

}
