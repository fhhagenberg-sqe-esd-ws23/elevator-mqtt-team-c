package at.fhhagenberg.sqelevator;


import at.fhhagenberg.sqelevator.controller.ElevatorController;
import at.fhhagenberg.sqelevator.converter.TypeConverter;
import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.Elevator;
import at.fhhagenberg.sqelevator.model.Floor;
import at.fhhagenberg.sqelevator.service.MqttService;

import java.nio.ByteBuffer;

public class MqttBuildingConnector {
    public MqttBuildingConnector(MqttService mqttService, ElevatorController controller, Building building)
    {
        for (Elevator  elevator : building.getElevators()) {
            // ================= publish =================
            elevator.acceleration.addListner((id, value) -> mqttService
                    .publish("elevator/" + id.getElevatorNumber() + "/accel", value));
            elevator.committedDirection.addListner((id, value) -> mqttService
                    .publish("elevator/" + id.getElevatorNumber() + "/direction", String.valueOf(value)));



            // ================= subscribe =================
            mqttService.subscribe("elevator/" + elevator.getElevatorNumber() + "/accel", (topic, publish) -> {
                System.out.println("Accel: " + new String(publish.getPayloadAsBytes()));
            });
            mqttService.subscribe("elevator/" + elevator.getElevatorNumber() + "/direction", (topic, publish) -> {

                String direction = new String(publish.getPayloadAsBytes());
                System.out.println("Elevator " + elevator.getElevatorNumber() + " Direction: " + direction);
//                controller.setCommittedDirection(elevator.getElevatorNumber(), TypeConverter.convert(publish, Direction.class));
//                controller.setCommittedDirection(elevator.getElevatorNumber(), Direction.valueOf(direction));
            });
        }

        for (Floor floor: building.getFloors()) {
            // ================= publish =================
            floor.upButton.addListner((id, value) -> mqttService
                    .publish("floor/" + id.getFloorNumber() + "/upButton", value));
            floor.downButton.addListner((id, value) -> mqttService
                    .publish("floor/" + id.getFloorNumber() + "/downButton", value));


            // ================= subscribe =================

            mqttService.subscribe("floor/" + floor.getFloorNumber() + "/upButton", (topic, publish) -> {
                System.out.println("Floor " + floor.getFloorNumber() + " UP Pressed: " + new String(publish.getPayloadAsBytes()));
                controller.setTarget(0, floor.getFloorNumber());
            });
            mqttService.subscribe("floor/" + floor.getFloorNumber() + "/downButton", (topic, publish) -> {
                System.out.println("Floor " + floor.getFloorNumber() + " DOWN Pressed: " + new String(publish.getPayloadAsBytes()));
                controller.setTarget(0, floor.getFloorNumber());
            });
        }
    }
}
