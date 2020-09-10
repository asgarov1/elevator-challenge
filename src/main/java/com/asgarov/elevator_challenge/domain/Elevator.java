package com.asgarov.elevator_challenge.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@ToString
@Slf4j
public class Elevator implements ManageableElevator {
    private String name;
    private AtomicInteger currentFloor = new AtomicInteger(0);

    public Elevator(String name) {
        this.name = name;
    }

    public void moveTo(int destinationFloorNumber) {
        while (currentFloor.get() != destinationFloorNumber) {
            currentFloor.set(currentFloor.get() < destinationFloorNumber ? currentFloor.incrementAndGet() : currentFloor.decrementAndGet());
        }
    }

    public Elevator transport(Request request) {
        moveTo(request.getFloorFrom());
        log.info("Elevator " + name + " arrived to floor " + currentFloor.get() + " Please enter!");
        moveTo(request.getFloorTo());
        log.info("Elevator " + name + " arrived to floor " + currentFloor.get() + " Please exit!");
        return this;
    }

    public int getCurrentFloor() {
        return currentFloor.get();
    }

}
