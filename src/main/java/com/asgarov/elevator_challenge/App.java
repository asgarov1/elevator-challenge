package com.asgarov.elevator_challenge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
//        new Thread(new CalculateAverage(new double[]{1, 2, 3, 4, 5})).start();

        Runnable task1 = () -> System.out.println("Printing zoo inventory");
        Runnable task2 = () -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Printing record: " + i);
            }
        };

        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            System.out.println("===begin===");
            service.execute(task1);
            service.execute(task2);
            service.execute(task1);
            System.out.println("===end===");
        } finally {
            service.shutdown();
        }
    }
}
