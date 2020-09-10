package com.asgarov.elevator_challenge.elevator_system;

import org.junit.jupiter.api.Test;

class ElevatorSystemTest {

    @Test
    void addRequest() throws InterruptedException {
        ElevatorSystem elevatorSystem = ElevatorSystem.getInstance();
        elevatorSystem.start();
//        Panel panel = new Panel();

        elevatorSystem.addRequest(0, 35);
        elevatorSystem.addRequest(55, 10);
        elevatorSystem.addRequest(20, 18);
        elevatorSystem.addRequest(0, 16);
        elevatorSystem.addRequest(2, 19);
        elevatorSystem.addRequest(21, 39);
        elevatorSystem.addRequest(46, 47);
        elevatorSystem.addRequest(44, 33);
        elevatorSystem.addRequest(20, 38);
        elevatorSystem.addRequest(1, 12);
        elevatorSystem.addRequest(0, 55);
        elevatorSystem.addRequest(3, 23);
    }
}