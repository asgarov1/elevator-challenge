package com.asgarov.elevator_challenge.elevator_system;

import com.asgarov.elevator_challenge.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@Getter
@AllArgsConstructor
public class ElevatorSystem {
    private ManageableTower manageableTower;
    private ExecutorService executorService;
    private ConcurrentLinkedQueue<Task> tasks;

    public ElevatorSystem(ManageableTower manageableTower) {
        this.manageableTower = manageableTower;
        executorService = Executors.newSingleThreadExecutor();
        tasks = new ConcurrentLinkedQueue<>();
    }

    public void addRequest(int floorFrom, int floorTo) {
        tasks.add(new Task(floorFrom, floorTo));
        work();
    }

    public void work() {
        while (!tasks.isEmpty()) {
            executorService.execute(() -> {
                try {
                    Task task = tasks.poll();
                    if (task == null) {
                        return;
                    }
                    manageableTower
                            .getNextElevator(task.getFloorTo())
                            .transport(task.getFloorFrom(), task.getFloorTo());
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void shutdown(){
        executorService.shutdown();
    }

}
