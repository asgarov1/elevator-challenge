package com.asgarov.elevator_challenge.elevator_system;

import com.asgarov.elevator_challenge.domain.DCTower;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorSystemTest {

    DCTower dcTower = DCTower.getInstance();
    ElevatorSystem elevatorSystem = new ElevatorSystem(dcTower);

    @Test
    void addRequest() {
        elevatorSystem.addRequest(0, 35);
        elevatorSystem.addRequest(10, 35);
        elevatorSystem.addRequest(34, 20);
    }
}