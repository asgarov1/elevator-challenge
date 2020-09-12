package com.asgarov.elevator_challenge.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Elevator implements ManageableElevator {

    private final String name;
    private int currentFloor;

    public Elevator(String name) {
        this.name = name;
    }

    /**
     * Moves the elevator by increasing/decreasing the current floor until detination is reached
     * @param destinationFloorNumber
     */
    public void moveTo(int destinationFloorNumber) {
        while (currentFloor != destinationFloorNumber) {
            currentFloor = currentFloor < destinationFloorNumber ? ++currentFloor : --currentFloor;
        }
    }

    /**
     * Transport method receives the request and first moves the elevator to the floor it was called from
     * and afterwards moves elevator to the destination floor
     * @param request
     * @return instance of itself to be put back into the queue of free elevators once it is done
     */
    public Elevator transport(Request request) {
        moveTo(request.getFloorFrom());
        log.info(name + " was called to floor " + currentFloor + " Please enter!");
        moveTo(request.getFloorTo());
        log.info(name + " arrived to floor " + currentFloor + " Please exit!");
        return this;
    }

    @Override
    public void returnToGroundFloor() {
        moveTo(0);
    }
}
