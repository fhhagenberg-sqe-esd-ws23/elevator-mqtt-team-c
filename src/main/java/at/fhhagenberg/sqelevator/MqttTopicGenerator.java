package at.fhhagenberg.sqelevator;

import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;

public class MqttTopicGenerator {

    private MqttTopicGenerator() {
        //utility class pattern
    }

    public static final String ELEVATOR_PRE = "elevator/";
    public static final String STOP_PATH ="system/stop";
    public static String elPath(Elevator id,String p)
    {
        return ELEVATOR_PRE + id.getElevatorNumber()+"/"+p;
    }
    public static String elPath(int id,String p)
    {
        return ELEVATOR_PRE + id +"/"+p;
    }
    public static String elPath(char id,String p)
    {
        return ELEVATOR_PRE + id +"/"+p;
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
    public static final String ACCELERATION ="accel";
    public static final String DIRECTION ="direction";
    public static final String CURRENT_FLOOR ="floor";
    public static final String CURRENT_POSITION ="curentPos";
    public static final String SPEED ="curentspeed";
    public static final String WEIGHT ="weight";
    public static final String DOOR_STATE ="doorState";
    public static final String FLOOR_BTN ="Button";
    public static final String SERVED_FLOOR ="served";
    public static final String TARGET_FLOOR ="targetfloor";
    public static final String BTN_UP ="btn/up";
    public static final String BTN_DOWN ="btn/down";
    public static final String ELEVATORS = "elevators";
    public static final String FLOORS = "floors";

}
