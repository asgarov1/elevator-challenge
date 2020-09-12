package com.asgarov.elevator_challenge.domain;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElevatorTest {

    public static final int MAX_FLOOR = 55;

    @Test
    void moveTo() {
        Elevator elevator = new Elevator("Test elevator");

        int destinationFloor = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);
        elevator.moveTo(destinationFloor);

        assertEquals(destinationFloor, elevator.getCurrentFloor());
    }

    @Test
    void transport() throws FileNotFoundException {
        Elevator elevator = new Elevator("Test elevator");

        int floorFrom = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);
        int floorTo = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);

        Request request = new Request(floorFrom, floorTo);
        elevator.transport(request);
        assertEquals(floorTo, elevator.getCurrentFloor());
    }

    @Test
    void returnToGroundFloor() {
        Elevator elevator = new Elevator("Test elevator");

        int destinationFloor = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);
        elevator.moveTo(destinationFloor);

        assertTrue(elevator.getCurrentFloor() != 0);
        elevator.returnToGroundFloor();
        assertEquals(0, elevator.getCurrentFloor());
    }
}