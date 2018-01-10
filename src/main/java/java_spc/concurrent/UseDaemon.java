package java_spc.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseDaemon {
    public static class SimpleDaemons implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println(Thread.currentThread() + " " + this);
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            for (int i = 0; i < 10; i++) {
                Thread daemon = new Thread(new SimpleDaemons());
                daemon.setDaemon(true);
                daemon.start();
            }
            System.out.println("all daemon threads start!");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    public static class DaemonThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }

    public static class DaemonFromFactory implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(101);
                    System.out.println(Thread.currentThread() + " " + this);
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ExecutorService service = Executors.newCachedThreadPool(new DaemonThreadFactory());
            for (int i = 0; i < 10; i++) {
                service.execute(new DaemonFromFactory());
            }
            System.out.println("all daemon threads start!");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    public static class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
        public DaemonThreadPoolExecutor() {
            super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                    new SynchronousQueue<>(),
                    new DaemonThreadFactory());
        }
    }

    public static class Daemon implements Runnable {
        private static class DaemonSpawn implements Runnable {
            @Override
            public void run() {
                while (true) {
                    Thread.yield();
                }
            }
        }

        private Thread[] threads = new Thread[10];

        @Override
        public void run() {
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(new DaemonSpawn());
                threads[i].start();
                System.out.println("DaemonSpawn " + i + " started.");
            }
            for (int i = 0; i < threads.length; i++) {
                System.out.println("threads[" + i + "].isDaemon() = " + threads[i].isDaemon() + ".");
            }
            while (true) {
                Thread.yield();
            }
        }
    }

    /**
     * 后台线程创建的线程仍为后台线程
     */
    public static class Daemons {
        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread(new Daemon());
            thread.setDaemon(true);
            thread.start();
            System.out.println("threads.isDaemon() = " + thread.isDaemon() + ".");
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static class ADaemon implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("starting ADaemon");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            } finally {
                System.out.println("this should always run?");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread(new ADaemon());
            thread.setDaemon(true);
            thread.start();
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
