package java_spc.generic;

/**
 * 自己实现内部链式存储机制， 实现栈的功能
 * 使用静态内部类
 */
public class LinkedStack<T> {
    private static class Node<U> {
        U item;
        Node<U> next;

        Node() {
            item = null;
            next = null;
        }

        Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    private Node<T> top = new Node<>();

    public void push(T item) {
        top = new Node<>(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    public boolean isEmpty() {
        return top.end();
    }

    public static void main(String[] args) {
        LinkedStack<String> stack = new LinkedStack<>();
        LinkedStackAnother<String> another = new LinkedStackAnother<>();
        for (String s : "Push some element".split(" ")) {
            stack.push(s);
            another.push(s);
        }
        String s;
        System.out.println("Use static class");
        while ((s = stack.pop()) != null) {
            System.out.println(s);
        }
        System.out.println("is empty " + stack.isEmpty());
        System.out.println("Use general class");
        while ((s = another.pop()) != null) {
            System.out.println(s);
        }
        System.out.println("is empty " + another.isEmpty());
    }
}

class LinkedStackAnother<T> {
    private class Node {
        T item;
        Node next;

        Node() {
            item = null;
            next = null;
        }

        Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    private Node top = new Node();

    public void push(T item) {
        top = new Node(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    public boolean isEmpty() {
        return top.end();
    }
}
