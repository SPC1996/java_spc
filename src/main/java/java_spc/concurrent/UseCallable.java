package java_spc.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UseCallable {
    private static class TaskWithResult implements Callable<String> {
        private int id;

        TaskWithResult(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            return "result of TaskWithResult " + id;
        }
    }

    private static class CalculateFibonacci implements Callable<Integer> {
        private int n;

        CalculateFibonacci(int n) {
            this.n = n;
        }

        @Override
        public Integer call() throws Exception {
            return calculate(n);
        }

        private int calculate(int n) {
            if (n <= 2) {
                return 1;
            } else {
                return calculate(n - 1) + calculate(n - 2);
            }
        }
    }

    private static void execute() {
        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<String>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            result.add(service.submit(new TaskWithResult(i)));
        }
        for (Future<String> fs : result) {
            try {
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                return;
            } catch (ExecutionException e) {
                System.out.println(e.getMessage());
            } finally {
                service.shutdown();
            }
        }
    }

    private static void calculate(int n) {
        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            result.add(service.submit(new CalculateFibonacci(i)));
        }
        int sum = 0;
        for (Future<Integer> fi : result) {
            try {
                sum += fi.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                service.shutdown();
            }
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        execute();
        calculate(3);
    }
}
