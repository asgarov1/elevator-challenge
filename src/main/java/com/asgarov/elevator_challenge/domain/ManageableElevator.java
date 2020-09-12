package com.asgarov.elevator_challenge.domain;

public interface ManageableElevator {
    ManageableElevator transport(Request request);
    int getCurrentFloor();
    String getName();
    void returnToGroundFloor();
}
