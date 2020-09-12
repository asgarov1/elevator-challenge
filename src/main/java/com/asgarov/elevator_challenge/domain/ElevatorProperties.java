package com.asgarov.elevator_challenge.domain;


import static com.asgarov.elevator_challenge.util.PropertiesReader.getProperties;

public class ElevatorProperties {

    public static final String ELEVATOR_PICKUP_MESSAGE = getProperties().getProperty("elevator.message.pickup");
    public static final String ELEVATOR_ARRIVED_MESSAGE = getProperties().getProperty("elevator.message.arrived");

}
