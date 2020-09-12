package com.asgarov.elevator_challenge.system;

import static com.asgarov.elevator_challenge.util.PropertiesReader.getProperties;

public class ElevatorSystemProperties {

    public static final int NUMBER_OF_ELEVATORS;
    public static final int DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS;
    public static final int SHUTDOWN_ATTEMPTS_DELAY;

    static {
        NUMBER_OF_ELEVATORS = Integer.parseInt(getProperties().getProperty("elevators.amount"));
        DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS = Integer.parseInt(getProperties().getProperty("request.checks.delay"));
        SHUTDOWN_ATTEMPTS_DELAY = Integer.parseInt(getProperties().getProperty("shutdown.attempts.delay"));
    }
}
