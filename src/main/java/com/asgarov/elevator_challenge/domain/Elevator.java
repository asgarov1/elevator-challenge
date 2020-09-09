package com.asgarov.elevator_challenge.domain;

import lombok.Getter;

@Getter
public class Elevator {
    private String name;
    private int currentFloor;
    private boolean busy = false;

    public Elevator(String name) {
        this.name = name;
    }

    public void moveTo(int destinationFloorNumber) {
        while (currentFloor != destinationFloorNumber) {
            currentFloor = currentFloor < destinationFloorNumber ? ++currentFloor : --currentFloor;
            System.out.println("Elevator " + name + ", current floor: " + currentFloor);
        }
    }

    /**
     * Convenience method that checks whether elevator is busy
     *
     * @return boolean
     */
    public boolean isFree() {
        return !isBusy();
    }

    public void transport(int floorFrom, int floorTo) {
        busy = true;
        System.out.println("Elevator name " + name);
        moveTo(floorFrom);
//        System.out.println("Elevator " + name + " arrived to floor " + currentFloorNumber + ". Please enter!");
        moveTo(floorTo);
//        System.out.println("Elevator " + name + " arrived to floor " + currentFloorNumber + " Please exit!");

        busy = false;
    }
}
