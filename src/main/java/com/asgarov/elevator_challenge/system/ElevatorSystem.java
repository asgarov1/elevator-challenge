package com.asgarov.elevator_challenge.system;

import com.asgarov.elevator_challenge.domain.Elevator;
import com.asgarov.elevator_challenge.domain.ManageableElevator;
import com.asgarov.elevator_challenge.domain.Request;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static com.asgarov.elevator_challenge.system.ElevatorSystemProperties.*;

@Getter
@Slf4j
public class ElevatorSystem {

    private final BlockingQueue<Request> requestsInQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<ManageableElevator> freeElevators = new LinkedBlockingQueue<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();

    private static class LazyHolder {
        static final ElevatorSystem instance = new ElevatorSystem();
    }

    private ElevatorSystem() {
        initElevators();
    }

    /**
     * Singleton getter
     *
     * @return only instance of ElevatorSystem
     */
    public static ElevatorSystem getInstance() {
        return LazyHolder.instance;
    }

    /**
     * Initializes the elevators to be used
     */
    private void initElevators() {
        for (int i = 0; i < NUMBER_OF_ELEVATORS; i++) {
            freeElevators.add(new Elevator("L" + (i + 1)));
        }
    }

    /**
     * Adds new request to the queue
     *
     * @param request
     */
    public void addRequest(Request request) {
        requestsInQueue.add(request);
    }


    /**
     * Starts the system by scheduling for elevators to perform on the queue of requests with fixed delay
     */
    public void start() {
        if (scheduledService.isShutdown()) {
            scheduledService = Executors.newSingleThreadScheduledExecutor();
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
        scheduledService.scheduleWithFixedDelay(this::work, 0, DELAY_BETWEEN_NEW_REQUEST_CHECKS_IN_MS, TimeUnit.MILLISECONDS);
        log.info("ElevatorSystem has started...");
    }

    /**
     * Attempts to shutdown the system with fixed delay waiting for the requests queue to be finished
     */
    @SneakyThrows
    public void shutdown() {
        while (!scheduledService.isShutdown()) {
            if (requestsInQueue.isEmpty()) {
                scheduledService.shutdown();
                executorService.shutdown();
                log.info("ElevatorSystem has shutdown.\n");
                break;
            }
            Thread.sleep(SHUTDOWN_ATTEMPTS_DELAY);
        }
    }

    /**
     * For every request from the queue picks one elevator and assigns it to work on a separate thread
     * Elevator is taken out from the queue of free elevators and returned back only after it has finished work
     */
    @SneakyThrows
    private void work() {
        while (!requestsInQueue.isEmpty()) {
            Request request = requestsInQueue.take();
            ManageableElevator elevator = freeElevators.take();
            executorService.submit(() -> freeElevators.offer(elevator.transport(request)));
        }
    }
}
