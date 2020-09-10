package com.asgarov.elevator_challenge.elevator_system;

import com.asgarov.elevator_challenge.domain.Elevator;
import com.asgarov.elevator_challenge.domain.ManageableElevator;
import com.asgarov.elevator_challenge.domain.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Getter
@AllArgsConstructor
@Slf4j
public class ElevatorSystem {
    private static final int NUMBER_OF_ELEVATORS = 7;
    private static final int NUMBER_OF_ELEVATOR_SYSTEMS = 1;
    public static final int DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS = 20;

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
    private BlockingQueue<Request> requestsInQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<ManageableElevator> elevators = new LinkedBlockingQueue<>();

    @Getter
    private static final ElevatorSystem instance = new ElevatorSystem();

    private ElevatorSystem() {
        initElevators();
    }

    @SneakyThrows
    private void initElevators() {
        for (int i = 0; i < NUMBER_OF_ELEVATORS; i++) {
            elevators.add(new Elevator(i + 1 + ""));
        }
    }

    public void addRequest(int floorFrom, int floorTo) throws InterruptedException {
        requestsInQueue.add(new Request(floorFrom, floorTo));
    }

    public void start() {
        scheduledService.scheduleWithFixedDelay(this::work, 0, DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        this.shutdown();
    }

    private void work() {
        executorService.submit(() -> {
            try {
                Request request = requestsInQueue.take();
                elevators.put(chooseElevator().transport(request));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    public ManageableElevator chooseElevator() {
        ManageableElevator elevator = null;
        try {
            elevator = elevators.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return elevator;
    }

}
