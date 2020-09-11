package com.asgarov.elevator_challenge;

import com.asgarov.elevator_challenge.system.ElevatorSystem;

public class App {
    public static void main(String[] args) throws InterruptedException {
        ElevatorSystem elevatorSystem = ElevatorSystem.getInstance();
        elevatorSystem.start();

        elevatorSystem.addRequest(0, 35);
        elevatorSystem.addRequest(55, 10);
        elevatorSystem.addRequest(20, 18);
//        elevatorSystem.addRequest(0, 16);
//        elevatorSystem.addRequest(2, 19);
//        elevatorSystem.addRequest(21, 39);
//        elevatorSystem.addRequest(46, 47);
//        elevatorSystem.addRequest(44, 33);
//        elevatorSystem.addRequest(20, 38);
//        elevatorSystem.addRequest(1, 12);
//        elevatorSystem.addRequest(0, 55);
//        elevatorSystem.addRequest(3, 23);
//        elevatorSystem.addRequest(0, 35);
//        elevatorSystem.addRequest(55, 10);
//        elevatorSystem.addRequest(20, 18);
//        elevatorSystem.addRequest(0, 16);

        Thread.sleep(200);
        System.out.println("\nLittle pause...\n");

        elevatorSystem.addRequest(1, 2);
        elevatorSystem.addRequest(5, 20);
        elevatorSystem.addRequest(22, 19);

        elevatorSystem.shutdown();
    }
}
