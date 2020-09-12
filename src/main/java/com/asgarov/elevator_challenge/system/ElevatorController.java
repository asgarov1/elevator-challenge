package com.asgarov.elevator_challenge.system;

import com.asgarov.elevator_challenge.domain.ManageableElevator;
import com.asgarov.elevator_challenge.domain.Request;

import java.util.Collection;

public interface ElevatorController {
    void start();
    void shutdown();
    void addRequest(Request request);
    Collection<ManageableElevator> getFreeElevators();
}
