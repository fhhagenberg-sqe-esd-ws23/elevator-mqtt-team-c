package at.fhhagenberg.sqelevator;


import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.service.MqttService;

public class MqttBuildingConnector {
    public MqttBuildingConnector(MqttService mqttService,Building building)
    {
        for (Elevator  elevator : building.getElevators()) {
            elevator.accelListener = (id, value) -> mqttService.publish("elevator/" + id.getElevatorNumber() + "/accel", value);
          }  
    }
}
