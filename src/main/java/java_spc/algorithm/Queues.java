package java_spc.algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class Queues {
    public static void yanghuiTriangle(int n) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        for (int i = 0; i <= n; i++) {
            int s = 0;
            queue.add(s);
            for (int j = 0; j < i + 2; j++) {
                int t, tmp;
                t = queue.poll();
                tmp = s + t;
                queue.add(tmp);
                s = t;
                if (j != i + 1) {
                    System.out.printf("%d ", s);
                }
            }
            System.out.println();
        }
    }
}
