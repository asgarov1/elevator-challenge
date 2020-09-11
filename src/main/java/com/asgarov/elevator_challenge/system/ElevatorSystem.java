package com.asgarov.elevator_challenge.system;

import com.asgarov.elevator_challenge.domain.Elevator;
import com.asgarov.elevator_challenge.domain.ManageableElevator;
import com.asgarov.elevator_challenge.domain.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static com.asgarov.elevator_challenge.util.PropertiesReader.getProperties;

@Getter
@AllArgsConstructor
@Slf4j
public class ElevatorSystem {
    private static final int NUMBER_OF_ELEVATORS;
    private static final int DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS;

    static {
        NUMBER_OF_ELEVATORS = Integer.parseInt(getProperties().getProperty("elevators.amount"));
        DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS = Integer.parseInt(getProperties().getProperty("request-checks.delay"));
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
    private BlockingQueue<Request> requestsInQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<ManageableElevator> elevators = new LinkedBlockingQueue<>();

    private static class LazyHolder {
        static final ElevatorSystem instance = new ElevatorSystem();
    }

    private ElevatorSystem() {
        initElevators();
    }

    private void initElevators() {
        for (int i = 0; i < NUMBER_OF_ELEVATORS; i++) {
            elevators.add(new Elevator(i + 1 + ""));
        }
    }

    public void addRequest(int floorFrom, int floorTo) {
        requestsInQueue.add(new Request(floorFrom, floorTo));
    }

    public void start() {
        scheduledService.scheduleWithFixedDelay(this::work, 0, DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    public void shutdown() {
        while (!scheduledService.isShutdown()) {
            if (requestsInQueue.isEmpty()) {
                scheduledService.shutdown();
                executorService.shutdown();
                break;
            }
            Thread.sleep(100);
        }
    }

    private void work() {
        while (!requestsInQueue.isEmpty()) {
            executorService.submit(() -> {
                try {
                    Request request = requestsInQueue.poll(100, TimeUnit.MILLISECONDS);
                    ManageableElevator elevator = elevators.poll(100, TimeUnit.MILLISECONDS);
                    if (elevator != null) {
                        elevators.put(elevator.transport(request));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    public static ElevatorSystem getInstance() {
        return LazyHolder.instance;
    }
}
