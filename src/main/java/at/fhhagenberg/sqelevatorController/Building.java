package at.fhhagenberg.sqelevatorController;

import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqelevator.model.Direction;

public class Building {
    private int floorCount;
    private int elevatorCount;

    private List<List<Boolean>> elevaorButtons=new ArrayList<>();
    private List<Boolean> upButtons=new ArrayList<>();
    private List<Boolean> downButtons=new ArrayList<>();
    private List<Integer> currentFloors=new ArrayList<>();
    private List<Integer> speeds=new ArrayList<>();
    private List<Direction> directions=new ArrayList<>();

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
        if(elevaorButtons.size()<=elevator)
            return false;
        if(elevaorButtons.get(elevator).size()<=floor)
            return false;
        return elevaorButtons.get(elevator).get(floor);
    }
    public void setElevatorButton(int elevator, int floor, boolean state) {
        while (this.elevaorButtons.size()<=elevator+1) {
            this.elevaorButtons.add(new ArrayList<>());
        }
        while (this.elevaorButtons.get(elevator).size()<=floor+1) {
            this.elevaorButtons.get(elevator).add(false);
        }
        this.elevaorButtons.get(elevator).set(floor,state);
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
}
