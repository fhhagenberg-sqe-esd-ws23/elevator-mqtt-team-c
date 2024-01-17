package at.fhhagenberg.sqelevatorcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.model.DoorStatus;

public class Building {
    private int floorCount;
    private int elevatorCount;

    private final List<List<Boolean>> elevatorButtons =new ArrayList<>();
    private final List<Boolean> upButtons=new ArrayList<>();
    private final List<Boolean> downButtons=new ArrayList<>();
    private final List<Integer> currentFloors=new ArrayList<>();
    private final List<Integer> speeds=new ArrayList<>();
    private final List<Direction> directions=new ArrayList<>();
    private final List<DoorStatus> doorStates =new ArrayList<>();

    private final Queue<Integer> floorRequests=new ConcurrentLinkedDeque<>();
    private final List<Queue<Integer>> elevatorRequests = new ArrayList<>();

    public void setFloorCount(int floorCount) {
        this.floorCount = floorCount;
    }
    public int getFloorCount() {
        return floorCount;
    }
    public void setElevatorCount(int elevatorCount) {
        this.elevatorCount = elevatorCount;
    }
    public int getElevatorCount() {
        return elevatorCount;
    }
    public void setDownButton(int index,Boolean downButtons) {
        while (this.downButtons.size()<=index) {
            this.downButtons.add(false);
        }this.downButtons.set(index, downButtons);
    }
    public Boolean getDownButton(int index) {
        if(downButtons.size()<=index)
            return false;
        return downButtons.get(index);
    }
    public void setUpButton(int index,Boolean upButtons) {
        while (this.upButtons.size()<=index) {
            this.upButtons.add(false);
        }
        this.upButtons.set(index, upButtons);
    }
    public Boolean getUpButton(int index) {
        
        if(upButtons.size()<=index)
            return false;
        return upButtons.get(index);
    }
    public void setDirection(int index,Direction direction) {
        while (this.directions.size()<=index+1) {
            this.directions.add(Direction.UNCOMMITTED);
        }
        this.directions.set(index, direction);
    }
    public Direction getDirection(int index) {
        
        if(directions.size()<=index)
            return Direction.UNCOMMITTED;
        return directions.get(index);
    }
    public Boolean getElevaorButton(int elevator,int floor) {
        if(elevatorButtons.size()<=elevator)
            return false;
        if(elevatorButtons.get(elevator).size()<=floor)
            return false;
        return elevatorButtons.get(elevator).get(floor);
    }
    public void setElevatorButton(int elevator, int floor, boolean state) {
        while (this.elevatorButtons.size()<=elevator+1) {
            this.elevatorButtons.add(new ArrayList<>());
        }
        while (this.elevatorButtons.get(elevator).size()<=floor+1) {
            this.elevatorButtons.get(elevator).add(false);
        }
        this.elevatorButtons.get(elevator).set(floor,state);
    }
    public int getCurrentFloor(int elevator) {
        if(elevator>=elevatorCount)
            throw new ArrayIndexOutOfBoundsException(elevator);
        if(currentFloors.size()<=elevator)
            return 0;
        return currentFloors.get(elevator);
    }
    public void setCurrentFloor(int elevator, int floor) {
        if(elevator>=elevatorCount)
            throw new ArrayIndexOutOfBoundsException(elevator);
        while (this.currentFloors.size()<=elevator) {
            this.currentFloors.add(0);
        }
        this.currentFloors.set(elevator, floor);
    }
    public int getSpeed(int index) {
        if(speeds.size()<=index)
            return 0;
        return speeds.get(index);
    }
    public void setSpeed(int index,int val) {
        while (this.speeds.size()<=index) {
            this.speeds.add(0);
        }
        this.speeds.set(index, val);
    }

    public void enqueElevatorRequest(int elevator,int floor)
    {
        while (this.elevatorRequests.size()<=elevator) {
            this.elevatorRequests.add(new ConcurrentLinkedDeque<>());
        }
        this.elevatorRequests.get(elevator).add(floor);
    }

    public Integer dequeElevatorRequest(int elevator)
    {
        if(elevatorRequests.size()>elevator)
        {
        return elevatorRequests.get(elevator).poll();
        }
        return null;
    }

    public void enqueFloorRequest(int floor){
        floorRequests.add(floor);
    }
    public Integer dequeFloorRequest()
    {
        return floorRequests.poll();
    }
    public DoorStatus getElevatorDoor(int elevatorNumber) {
        if(doorStates.size()<=elevatorNumber)
            return DoorStatus.CLOSED;
        return doorStates.get(elevatorNumber);
    }
    
    public void setElevatorDoor(int index,DoorStatus val) {
        while (this.doorStates.size()<=index) {
            this.doorStates.add(DoorStatus.CLOSED);
        }
        this.doorStates.set(index, val);
    }
}
