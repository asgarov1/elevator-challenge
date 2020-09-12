package com.asgarov.elevator_challenge.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.asgarov.elevator_challenge.domain.ElevatorProperties.ELEVATOR_ARRIVED_MESSAGE;
import static com.asgarov.elevator_challenge.domain.ElevatorProperties.ELEVATOR_PICKUP_MESSAGE;
import static java.text.MessageFormat.format;

@Slf4j
@Getter
public class Elevator implements ManageableElevator {

    private final String name;
    private int currentFloor;

    public Elevator(String name) {
        this.name = name;
    }

    /**
     * Moves the elevator by increasing/decreasing the current floor until destination is reached
     *
     * @param destinationFloor specifies destination floor number
     */
    public void moveTo(int destinationFloor) {
        while (currentFloor != destinationFloor) {
            currentFloor = currentFloor < destinationFloor ? ++currentFloor : --currentFloor;
        }
    }

    /**
     * Transport method receives the request
     * - first moves the elevator to the floor it was called from
     * - afterwards moves the elevator to the destination floor
     *
     * @param request includes information regarding pickup and destination floors
     * @return instance of itself to be put back into the queue of free elevators once it is done
     */
    public Elevator transport(Request request) {
        moveTo(request.getFloorFrom());
        Direction direction = Direction.calculate(request.getFloorFrom(), request.getFloorTo());
        log.info(format(ELEVATOR_PICKUP_MESSAGE, name, currentFloor, request.getFloorTo(), direction));

        moveTo(request.getFloorTo());
        log.info(format(ELEVATOR_ARRIVED_MESSAGE, name, currentFloor));
        return this;
    }

    @Override
    public void returnToGroundFloor() {
        moveTo(0);
    }
}
