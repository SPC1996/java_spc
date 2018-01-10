package java_spc.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UseExecutor {
    public static class LiftOff implements Runnable {
        int countDown = 10;
        private static int taskCount = 1;
        private final int id = taskCount++;

        LiftOff() {
        }

        public LiftOff(int countDown) {
            this.countDown = countDown;
        }

        public String status() {
            return Thread.currentThread().getName() + "--#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + ").";
        }

        @Override
        public void run() {
            while (countDown-- > 0) {
                System.out.println(status());
                Thread.yield();
            }
        }
    }

    private static void cachedThreadPool() {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            service.execute(new LiftOff(3));
        }
        service.shutdown();
    }

    private static void fixedThreadPool() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 3; i++) {
            service.execute(new LiftOff(3));
        }
        service.shutdown();
    }

    private static void singleThread() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 3; i++) {
            service.execute(new LiftOff(3));
        }
        service.shutdown();
    }

    public static void main(String[] args) {
        cachedThreadPool();
        fixedThreadPool();
        singleThread();
    }
}
