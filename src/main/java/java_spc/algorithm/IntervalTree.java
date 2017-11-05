package java_spc.algorithm;

public class IntervalTree {
    public static class BalancedLineup {
        public static class Node {
            public int left, right;
            public int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        }

        public static int MAX = Integer.MIN_VALUE;
        public static int MIN = Integer.MAX_VALUE;

        private int[] elements;
        private Node[] nodes;

        public BalancedLineup(int[] elements) {
            this.elements = elements;
            nodes = new Node[elements.length * 4];
            for (int i = 0; i < elements.length * 4; i++) {
                nodes[i] = new Node();
            }
            build(0, 0, elements.length - 1);
        }

        public void build(int root, int left, int right) {
            nodes[root].left = left;
            nodes[root].right = right;
            if (left == right) {
                nodes[root].max = elements[left];
                nodes[root].min = elements[right];
                return;
            }
            int mid = (left + right) / 2;
            build(left(root), left, mid);
            build(right(root), mid + 1, right);
            nodes[root].max = Math.max(nodes[left(root)].max, nodes[right(root)].max);
            nodes[root].min = Math.min(nodes[left(root)].min, nodes[right(root)].min);
        }

        public void query(int root, int left, int right) {
            if (nodes[root].left == left && nodes[root].right == right) {
                if (MAX < nodes[root].max) {
                    MAX = nodes[root].max;
                }
                if (MIN > nodes[root].min) {
                    MIN = nodes[root].min;
                }
                return;
            }
            int mid = (nodes[root].left + nodes[root].right) / 2;
            if (left > mid) {
                query(right(root), left, right);
            } else if (right <= mid) {
                query(left(root), left, right);
            } else {
                query(left(root), left, mid);
                query(right(root), mid + 1, right);
            }
        }

        public int max() {
            int res = MAX;
            MAX = Integer.MIN_VALUE;
            return res;
        }

        public int min() {
            int res = MIN;
            MIN = Integer.MAX_VALUE;
            return res;
        }

        public static void play() {
            int[] elements = {1, 7, 3, 4, 2, 5};
            int left = 2, right = 4;
            BalancedLineup bl = new BalancedLineup(elements);
            bl.query(0, left, right);
            System.out.println(bl.max());
            System.out.println(bl.min());
        }
    }

    public static class SomeOperate {
        public static class Node {
            public int left;
            public int right;
            public int sum;
        }

        private int[] elements;
        private Node[] nodes;

        public SomeOperate(int[] elements) {
            this.elements = elements;
            nodes = new Node[elements.length * 4];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node();
            }
            build(0, 0, elements.length - 1);
        }

        public void build(int root, int left, int right) {
            nodes[root].left = left;
            nodes[root].right = right;
            if (left == right) {
                nodes[root].sum = elements[left];
                return;
            }
            int mid = (left + right) / 2;
            build(left(root), left, mid);
            build(right(root), mid + 1, right);
            nodes[root].sum = nodes[left(root)].sum + nodes[right(root)].sum;
        }

        public void update(int root, int left, int right, int pos, int delta) {
            if (nodes[root].left > pos || nodes[root].right < pos) {
                return;
            }
            if (nodes[root].left == nodes[root].right) {
                nodes[root].sum += delta;
                return;
            }
            int mid = (nodes[root].left + nodes[root].right) / 2;
            if (left > mid) {
                update(right(root), left, right, pos, delta);
            } else if (right <= mid) {
                update(left(root), left, right, pos, delta);
            } else {
                update(left(root), left, mid, pos, delta);
                update(right(root), mid + 1, right, pos, delta);
            }
            nodes[root].sum = nodes[left(root)].sum + nodes[right(root)].sum;
        }

        public int query(int root, int left, int right) {
            if (nodes[root].left == left && nodes[root].right == right) {
                return nodes[root].sum;
            }
            int mid = (nodes[root].left + nodes[root].right) / 2;
            if (left > mid) {
                return query(right(root), left, right);
            } else if (right <= mid) {
                return query(left(root), left, right);
            } else {
                return query(left(root), left, mid) + query(right(root), mid + 1, right);
            }
        }

        public static void play() {
            int[] elements = {4, 5, 6, 2, 1, 3, 4};
            SomeOperate so = new SomeOperate(elements);
            System.out.println(so.query(0, 1, 4));
            so.update(0, 1, 4, 2, 5);
            System.out.println(so.query(0, 1, 4));
        }
    }

    private static int left(int root) {
        return root * 2 + 1;
    }

    private static int right(int root) {
        return root * 2 + 2;
    }

    public static void main(String[] args) {
        SomeOperate.play();
    }
}
