package com.asgarov.elevator_challenge.elevator_system;

public class Panel {
    private ElevatorSystem elevatorSystem = ElevatorSystem.getInstance();

    public Panel() {
        elevatorSystem.start();
    }

    public void addRequest(int floorFrom, int floorTo) {
        try {
            elevatorSystem.addRequest(floorFrom, floorTo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
