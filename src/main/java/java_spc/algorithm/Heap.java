package java_spc.algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Heap<T extends Comparable<T>> {
    private int size;
    private int capacity;
    private T[] elements;
    private Class<T> type;

    @SuppressWarnings("unchecked")
    public Heap(Class<T> type) {
        this(10, (T[]) Array.newInstance(type, 10), type);
    }

    @SuppressWarnings("unchecked")
    public Heap(int capacity, Class<T> type) {
        this(capacity, (T[]) Array.newInstance(type, capacity), type);
    }

    public Heap(int capacity, T[] elements, Class<T> type) {
        this.size = 0;
        this.capacity = capacity;
        this.elements = elements;
        this.type = type;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void siftDown(int start) {
        int i = start, j;
        T tmp = elements[i];
        for (j = 2 * i + 1; j < size; j = 2 * j + 1) {
            if (j < size - 1 && elements[j].compareTo(elements[j + 1]) > 0) {
                j++;
            }
            if (tmp.compareTo(elements[j]) <= 0) {
                break;
            } else {
                elements[i] = elements[j];
                i = j;
            }
        }
        elements[i] = tmp;
    }

    public void siftUp(int start) {
        int j = start;
        int i = (j - 1) / 2;
        T tmp = elements[start];
        while (j > 0) {
            if (elements[i].compareTo(tmp) <= 0) {
                break;
            } else {
                elements[j] = elements[i];
                j = i;
                i = (i - 1) / 2;
            }
        }
        elements[j] = tmp;
    }

    @SuppressWarnings("unchecked")
    public void push(T x) {
        if (size == capacity) {
            T[] old = elements;
            elements = (T[]) Array.newInstance(type, capacity * 2);
            System.arraycopy(old, 0, elements, 0, old.length);
            old = null;
            capacity *= 2;
        }
        elements[size] = x;
        size++;
        siftUp(size - 1);
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        } else {
            T res = elements[0];
            elements[0] = elements[size - 1];
            size--;
            siftDown(0);
            return res;
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return elements[0];
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; i < size - 1; i++) {
            builder.append(elements[i]);
            builder.append(',');
        }
        builder.append(elements[size - 1]);
        builder.append(']');
        return builder.toString();
    }

    /**
     * 有两个长度为n的序列a和b，在a，b中各取一个数得到n*n个和，求其中最小的n个
     *
     * @param a 序列 a 长度为n
     * @param b 序列 b 长度为n
     * @return 最小的n个
     */
    public static int[] minNSum(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        PriorityQueue<Node> queue = new PriorityQueue<>();
        int[] res = new int[a.length];
        Node node;
        for (int eleA : a) {
            node = new Node();
            node.sum = eleA + b[0];
            node.b = 0;
            queue.add(node);
        }
        for (int i = 0; i < a.length; i++) {
            node = queue.poll();
            res[i] = node.sum;
            node.sum = node.sum - b[node.b] + b[node.b + 1];
            node.b++;
            queue.add(node);

        }
        return res;
    }

    public static class Node implements Comparable<Node> {
        public int sum;
        public int b;

        @Override
        public int compareTo(Node node) {
            return Integer.compare(sum, node.sum);
        }
    }
}
