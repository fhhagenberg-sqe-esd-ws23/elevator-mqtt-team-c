# sqelevator-proj
Group assignment SQElevator Team C

## Requirements
- Java Runtime (version 17)
- MQTT broker
- Docker for testing

## Mqtt topics:

The following properties can be subscribed:
select the floor/elevator using its id: {id}

### elevator
- elevator/{id}/accel
- elevator/{id}/direction
- elevator/{id}/floor
- elevator/{id}/curentPos
- elevator/{id}/curentspeed
- elevator/{id}/weight
- elevator/{id}/doorState
- elevator/{id}/Button
- elevator/{id}/served
- elevator/{id}/targetfloor

### floor
- floor/{id}/btn/up
- floor/{id}/btn/down

### count instances
- elevators
- floors

## Prerequisites
- Elevator Simulator
- Local MQTT-Broker
- sqelevator.conf file is needed in root directory

## Run Code in IDE
- elevator control: run static main method in: at.fhhagenberg.sqelevator.Sqelevator
- algorithm: run static main method in: at.fhhagenberg.sqelevatorcontroller.ControllerRunner

## Run packages
- sqelevator.conf file is needed in root directory
- java -jar mqtt-elevator-c-Connector.jar
- java -jar mqtt-elevator-c-Controller.jar

