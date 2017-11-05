package java_spc.algorithm;

import java.util.Arrays;
import java.util.Scanner;

public class DisjointSet {
    private int[] parents;
    private int size;

    public DisjointSet(int size) {
        this.parents = new int[size];
        this.size = size;
        Arrays.fill(parents, -1);
    }

    public int findRecursion(int x) {
        if (parents[x] < 0) {
            return x;
        }
        return parents[x] = findRecursion(parents[x]);
    }

    public int findIterative(int x) {
        int oldX = x;
        while (parents[x] >= 0) {
            x = parents[x];
        }
        while (oldX != x) {
            int temp = parents[oldX];
            parents[oldX] = x;
            oldX = temp;
        }
        return x;
    }

    public int findNative(int x) {
        while (parents[x] >= 0) {
            x = parents[x];
        }
        return x;
    }

    public void union(int x, int y) {
        int rx = findRecursion(x);
        int ry = findRecursion(y);
        if (rx == ry) {
            return;
        }
        parents[rx] += parents[ry];
        parents[ry] = rx;
    }

    public int size(int x) {
        int rx = findRecursion(x);
        return -parents[rx];
    }

    /**
     * 一个学校有n个社团，一个学生能同时加入不同的社团。由于社团的同学交往频繁，
     * 如果一个学生感染了病毒，该社团的所有学生都会感染病毒。现在0号学生感染了病毒，
     * 问一共有多少人感染了病毒。
     */
    public static void findInfected() {
        Scanner scanner = new Scanner(System.in);
        int n, m, k;
        while (scanner.hasNextInt()) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            if (n == 0 && m == 0) {
                break;
            }
            DisjointSet set = new DisjointSet(30000);
            while (m-- > 0) {
                int x, y, rx, ry;
                k = scanner.nextInt();
                k--;
                x = scanner.nextInt();
                rx = set.findRecursion(x);
                while (k-- > 0) {
                    y = scanner.nextInt();
                    ry = set.findRecursion(y);
                    set.union(rx, ry);
                }
            }
            System.out.println(set.size(0));
        }
    }
}

class ImproveDisjointSet {
    public static class UnionFindSet {
        public int[] parents;
        public int[] dist;
    }

    public static UnionFindSet create(int n) {
        UnionFindSet ufs = new UnionFindSet();
        ufs.parents = new int[n];
        ufs.dist = new int[n];
        for (int i = 0; i < n; i++) {
            ufs.parents[i] = -1;
            ufs.dist[i] = 0;
        }
        return ufs;
    }

    public static int find(UnionFindSet ufs, int x, int type) {
        if (ufs.parents[x] < 0) {
            return x;
        }
        int parent = ufs.parents[x];
        ufs.parents[x] = find(ufs, ufs.parents[x], type);
        ufs.dist[x] = (ufs.dist[x] + ufs.dist[parent]) % type;
        return ufs.parents[x];
    }

    public static void union(UnionFindSet ufs, int r1, int r2) {
        if (r1 == r2) {
            return;
        }
        ufs.parents[r1] += ufs.parents[r2];
        ufs.parents[r2] = r1;
    }

    public static void addOpponent(UnionFindSet ufs, int x, int y) {
        int rx = find(ufs, x, 2);
        int ry = find(ufs, y, 2);
        union(ufs, rx, ry);
        ufs.dist[ry] = (ufs.dist[y] + 1 + ufs.dist[x]) % 2;
    }

    public static void addRelation(UnionFindSet ufs, int x, int y, int relation) {
        int rx = find(ufs, x, 3);
        int ry = find(ufs, y, 3);
        union(ufs, ry, rx);
        ufs.dist[rx] = (ufs.dist[y] - ufs.dist[x] + 3 + relation) % 3;
    }

    /**
     * 两个黑帮，n个黑帮分子，从1到n编号，每个人至少属于一个帮派。每个
     * 帮派至少有一个人。给m条信息，有以下两类：
     * D a b a和b属于不同帮派
     * A a b a和b是否属于不同帮派
     * <p>
     * 输入：
     * 第一行是一个整数t，t组测试用例。每组测试用例的第一行是两个整数
     * n和m，接下来是m行，每行包含一条信息。
     * 输出：
     * 对每条消息A a b，基于当前获得的信息，输出判断（In the same gang
     * , In different gangs, Not sure yet）
     */
    public static void twoGangster() {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            UnionFindSet ufs = create(1000000);
            int n, m, x, y, rx, ry;
            char c;
            n = scanner.nextInt();
            m = scanner.nextInt();
            for (int j = 0; j < m; j++) {
                c = scanner.next().charAt(0);
                x = scanner.nextInt();
                y = scanner.nextInt();
                rx = find(ufs, x, 2);
                ry = find(ufs, y, 2);
                if (c == 'A') {
                    if (rx == ry) {
                        if (ufs.dist[x] != ufs.dist[y]) {
                            System.out.println("In different gangs.");
                        } else {
                            System.out.println("In the same gang.");
                        }
                    } else {
                        System.out.println("Not sure yet.");
                    }
                } else if (c == 'D') {
                    addOpponent(ufs, x, y);
                }
            }
        }
    }

    /**
     * 动物王国中有三类动物A,B,C，这三类动物的食物链构成了有趣的环形。A吃B， B吃C，C吃A。
     * 现有N个动物，以1－N编号。每个动物都是A,B,C中的一种，但是我们并不知道它到底是哪一种。
     * 有人用两种说法对这N个动物所构成的食物链关系进行描述：
     * 第一种说法是"1 X Y"，表示X和Y是同类。
     * 第二种说法是"2 X Y"，表示X吃Y。
     * 此人对N个动物，用上述两种说法，一句接一句地说出K句话，这K句话有的是真的，有的是假的。当一句话满足下列三条之一时，这句话就是假话，否则就是真话。
     * 1）	当前的话与前面的某些真的话冲突，就是假话；
     * 2）	当前的话中X或Y比N大，就是假话；
     * 3）	当前的话表示X吃X，就是假话。
     * 你的任务是根据给定的N（1 <= N <= 50,000）和K句话（0 <= K <= 100,000），输出假话的总数。
     * input:
     * 第一行是两个整数N和K，以一个空格分隔。
     * 以下K行每行是三个正整数 D，X，Y，两数之间用一个空格隔开，其中D表示说法的种类。
     * 若D=1，则表示X和Y是同类。
     * 若D=2，则表示X吃Y。
     * output:
     * 只有一个整数，表示假话的数目。
     */
    public static void foodChain() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int result = 0;
        UnionFindSet ufs = create(50000);
        for (int i = 0; i < k; i++) {
            int d = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if (x > n || y > n || (d == 2 && x == y)) {
                result++;
            } else {
                int rx = find(ufs, x, 3);
                int ry = find(ufs, y, 3);
                if (rx == ry) {
                    if ((ufs.dist[x] - ufs.dist[y] + 3) % 3 != d - 1) {
                        result++;
                    }
                } else {
                    addRelation(ufs, x, y, d - 1);
                }
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        foodChain();
    }
}