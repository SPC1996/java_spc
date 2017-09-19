package java_spc.algorithm;

import java.util.Stack;

public class Stacks {
    public static void hanoi(int n, char x, char y, char z) {
        if (n == 1) {
            System.out.printf("%d from %c to %c\n", n, x, z);
        } else {
            hanoi(n - 1, x, z, y);
            System.out.printf("%d from %c to %c\n", n, x, z);
            hanoi(n - 1, y, x, z);
        }
    }

    public static void convertBase(int n, int d) {
        Stack<Integer> stack = new Stack<>();
        int e;
        while (n != 0) {
            e = n % d;
            stack.push(e);
            n /= d;
        }
        while (!stack.isEmpty()) {
            e = stack.pop();
            System.out.printf("%X", e);
        }
        System.out.println();
    }

    public static int restore(char[] str, int d) {
        int result = 0;
        int one;
        for (char c : str) {
            if (c >= '0' && c <= '9') {
                one = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                one = c - 'A' + 10;
            } else {
                one = c - 'a' + 10;
            }
            result = result * d + one;
        }
        return result;
    }
}
