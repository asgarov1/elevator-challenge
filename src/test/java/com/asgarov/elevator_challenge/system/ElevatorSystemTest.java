package com.asgarov.elevator_challenge.system;

import com.asgarov.elevator_challenge.domain.Request;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class ElevatorSystemTest {

    /**
     * I kept the amount low so that you didn't have to wait too long :)
     */
    public static final int NUMBER_OF_REPETITIONS = 10;

    ElevatorController elevatorController = ElevatorSystem.getInstance();

    @BeforeEach
    public void reset() {
        elevatorController.returnElevatorsToGroundFloor();
        log.info("===New Test===");
    }

    @RepeatedTest(NUMBER_OF_REPETITIONS)
    @DisplayName("After requests are performed elevators should be on the destination floors")
    void addRequestWorksCorrectly() {
        elevatorController.start();
        List<Request> requests = List.of(new Request(0, 35),
                new Request(55, 0),
                new Request(20, 0),
                new Request(45, 0));

        requests.forEach(elevatorController::addRequest);
        elevatorController.shutdown();

        long elevatorsOnDestinationFloors = countElevatorsOnDestinationFloors(requests);
        assertTrue(elevatorsOnDestinationFloors >= requests.size());
    }

    @RepeatedTest(NUMBER_OF_REPETITIONS)
    @DisplayName("Should perform as expected with requests being submitted concurrently")
    void addRequestWorksCorrectlyInParallel() {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        elevatorController.start();

        List<Request> requests = List.of(new Request(0, 35),
                new Request(55, 0),
                new Request(0, 18),
                new Request(2, 0),
                new Request(0, 5),
                new Request(0, 26),
                new Request(0, 45));

        requests.forEach(request -> executorService.submit(() -> elevatorController.addRequest(request)));

        elevatorController.shutdown();

        long elevatorsOnDestinationFloors = countElevatorsOnDestinationFloors(requests);
        assertEquals(requests.size(), elevatorsOnDestinationFloors);
    }

    /**
     * Helper method that checks how many elevators are on destination floors
     *
     * @param requests used to get destination floors
     * @return long amount of elevators
     */
    private long countElevatorsOnDestinationFloors(List<Request> requests) {
        return elevatorController.getFreeElevators()
                .stream()
                .filter(elevator -> requests.stream()
                        .anyMatch(request -> request.getDestination() == elevator.getCurrentFloor()))
                .count();
    }
}
