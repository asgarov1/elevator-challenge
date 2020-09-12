package com.asgarov.elevator_challenge.system;

import com.asgarov.elevator_challenge.domain.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ElevatorSystemTest {

    ElevatorSystem elevatorSystem = ElevatorSystem.getInstance();

    @BeforeEach
    public void reset() {
        returnElevatorsToGroundFloor();
        System.out.println("\n===New Test===\n");
    }

    @Test
    @DisplayName("After requests are performed same amount of elevators should be on the destination floors")
    void addRequestWorksCorrectly() {
        elevatorSystem.start();

        List<Request> requests = List.of(new Request(0, 35),
                new Request(55, 10),
                new Request(20, 18));

        requests.forEach(elevatorSystem::addRequest);

        elevatorSystem.shutdown();

        long elevatorsMoved = elevatorSystem.getFreeElevators()
                .stream()
                .filter(elevator -> requests.stream()
                        .mapToInt(Request::getFloorTo)
                        .anyMatch(i -> i == elevator.getCurrentFloor()))
                .count();

        assertEquals(requests.size(), elevatorsMoved);
    }

    @Test
    @DisplayName("Should perform as expected even with requests being added concurrently")
    void addRequestWorksCorrectlyInParallel() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        elevatorSystem.start();

        List<Request> requests = Stream.of(new Request(0, 35),
                new Request(55, 10),
                new Request(20, 18),
                new Request(2, 4),
                new Request(0, 5))
                .collect(Collectors.toList());

        requests.forEach(request -> executorService.submit(() -> elevatorSystem.addRequest(request)));

        elevatorSystem.shutdown();

        long elevatorsMoved = elevatorSystem.getFreeElevators()
                .stream()
                .filter(elevator -> requests.stream()
                        .mapToInt(Request::getFloorTo)
                        .anyMatch(destinationFloor -> destinationFloor == elevator.getCurrentFloor()))
                .count();

        assertEquals(requests.size(), elevatorsMoved);
    }

    private void returnElevatorsToGroundFloor() {
        elevatorSystem.getFreeElevators().forEach(elevator -> elevator.transport(new Request(0, 0)));
    }
}