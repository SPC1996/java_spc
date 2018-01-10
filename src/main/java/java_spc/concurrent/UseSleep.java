package java_spc.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UseSleep {
    public static class SleepingTask implements Runnable {
        int countDown = 10;
        private static int taskCount = 0;
        private final int id = taskCount++;

        SleepingTask() {
        }

        public SleepingTask(int countDown) {
            this.countDown = countDown;
        }

        public String status() {
            return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + ").";
        }

        @Override
        public void run() {
            try {
                while (countDown-- > 0) {
                    System.out.print(status());
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
    }

    public static class RandomSleepingTask implements Runnable {
        private static int taskCount = 0;
        private int randomSeconds = new Random().nextInt(10) + 1;

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(randomSeconds);
                System.out.println("Thread " + taskCount++ +" sleep "+ randomSeconds + " seconds");
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
//            service.execute(new SleepingTask());
            service.execute(new RandomSleepingTask());
        }
        service.shutdown();
    }
}
