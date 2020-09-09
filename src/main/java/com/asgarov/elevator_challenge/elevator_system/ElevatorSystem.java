package com.asgarov.elevator_challenge.elevator_system;

import com.asgarov.elevator_challenge.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public class ElevatorSystem {
    private ManageableTower manageableTower;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ConcurrentLinkedQueue<Task> tasksInQueue = new ConcurrentLinkedQueue<>();
    private Map<Integer, List<Future<?>>> currentTasks = new HashMap<>();

    public ElevatorSystem(ManageableTower manageableTower) {
        this.manageableTower = manageableTower;
    }

    public void addRequest(int floorFrom, int floorTo) {
        tasksInQueue.add(new Task(floorFrom, floorTo));
        work();
    }

    public void work() {
        while (!tasksInQueue.isEmpty()) {
            Task task = tasksInQueue.poll();
            if (task == null) {
                return;
            }
            int elevatorNumber = chooseElevator(task.getFloorTo());

            Future<?> future = executorService.submit(() -> {
                manageableTower
                        .getElevator(elevatorNumber)
                        .transport(task.getFloorFrom(), task.getFloorTo());
            });

            currentTasks.putIfAbsent(elevatorNumber, Stream.of(future).collect(Collectors.toList()));
            currentTasks.computeIfPresent(elevatorNumber, (key, values) -> Stream.concat(values.stream(), Stream.of(future)).collect(Collectors.toList()));
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public int chooseElevator(int destinationFloor) {
//        return Arrays.stream(elevators)
//                .sorted((a, b) -> Boolean.compare(a.isFree(), b.isFree()))
//                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - destinationFloor)))
//                .orElseThrow();
        return 1;
    }

}
