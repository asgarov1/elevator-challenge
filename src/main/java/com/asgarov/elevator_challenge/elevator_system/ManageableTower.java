package com.asgarov.elevator_challenge.elevator_system;

import com.asgarov.elevator_challenge.domain.Elevator;

import java.util.concurrent.TimeoutException;

public interface ManageableTower {
    Elevator getNextElevator(int destinationFloor) throws TimeoutException;
}
