package com.asgarov.elevator_challenge.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElevatorTest {

    public static final int MAX_FLOOR = 55;

    @Test
    @DisplayName("Method moveTo() should work as expected")
    void moveTo() {
        Elevator elevator = new Elevator("Test elevator");

        int destinationFloor = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);
        elevator.moveTo(destinationFloor);

        assertEquals(destinationFloor, elevator.getCurrentFloor());
    }

    @Test
    @DisplayName("Method transport() should work as expected")
    void transport() {
        Elevator elevator = new Elevator("Test elevator");

        int floorFrom = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);
        int floorTo = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);

        Request request = new Request(floorFrom, floorTo);
        elevator.transport(request);
        assertEquals(floorTo, elevator.getCurrentFloor());
    }

    @Test
    @DisplayName("Method returnToGroundFloor() should work as expected")
    void returnToGroundFloor() {
        Elevator elevator = new Elevator("Test elevator");

        int destinationFloor = ThreadLocalRandom.current().nextInt(1, MAX_FLOOR + 1);
        elevator.moveTo(destinationFloor);

        assertTrue(elevator.getCurrentFloor() != 0);
        elevator.returnToGroundFloor();
        assertEquals(0, elevator.getCurrentFloor());
    }
}
