package java_spc.concurrent;

public class BasicThread {
    public static class LiftOff implements Runnable {
        int countDown = 10;
        private static int taskCount = 0;
        private final int id = taskCount++;

        LiftOff() {
        }

        public LiftOff(int countDown) {
            this.countDown = countDown;
        }

        public String status() {
            return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + ").";
        }

        @Override
        public void run() {
            while (countDown-- > 0) {
                System.out.print(status());
                Thread.yield();
            }
        }
    }

    public static void methodRun() {
        LiftOff liftOff = new LiftOff();
        liftOff.run();
    }

    public static void threadRun() {
        Thread thread = new Thread(new LiftOff());
        thread.start();
        System.out.println("waiting...");
    }

    private static void moreThreadRun() {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("waiting...");
    }

    public static void main(String[] args) {
//            main中直接调用run方法
//            结果：#0(9).#0(8).#0(7).#0(6).#0(5).#0(4).#0(3).#0(2).#0(1).#0(Liftoff!).
//            methodRun();
//            线程中调用run
//            结果：waiting...
//                  #0(9).#0(8).#0(7).#0(6).#0(5).#0(4).#0(3).#0(2).#0(1).#0(Liftoff!).
//            主线程先结束
//            threadRun();
        moreThreadRun();
    }
}
