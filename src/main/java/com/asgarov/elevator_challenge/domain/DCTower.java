package com.asgarov.elevator_challenge.domain;

import com.asgarov.elevator_challenge.elevator_system.ManageableTower;

import java.util.Arrays;
import java.util.Comparator;

public class DCTower implements ManageableTower {
    private static final int NUMBER_OF_ELEVATORS = 7;

    private final Elevator[] elevators;
    private static final DCTower instance = new DCTower();

    private DCTower() {
        this.elevators = new Elevator[NUMBER_OF_ELEVATORS];
        initElevators();
    }

    private void initElevators() {
        for (int i = 0; i < elevators.length; i++) {
            elevators[i] = new Elevator(i + 1 + "");
        }
    }

    public boolean hasElevatorOnFloor(int floorNumber) {
        for (Elevator elevator : elevators) {
            if (elevator.getCurrentFloor() == floorNumber) {
                return true;
            }
        }
        return false;
    }

    public static DCTower getInstance() {
        return instance;
    }

    public Elevator[] getElevators() {
        return elevators;
    }

    @Override
    public Elevator getElevator(int number) {
        return elevators[number];
    }
}
