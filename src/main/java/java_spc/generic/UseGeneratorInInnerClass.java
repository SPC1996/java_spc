package java_spc.generic;

import java_spc.util.Generator;

import java.util.*;

/**
 * 在匿名内部类中使用泛型
 */
public class UseGeneratorInInnerClass {
    public static void serve(Teller t, Customer c) {
        System.out.println(t + " serve " + c);
    }

    public static <T> void fill(Collection<T> coll, Generator<T> gen, int count) {
        for (int i = 0; i < count; i++) {
            coll.add(gen.next());
        }
    }

    public static void main(String[] args) {
        Random rand = new Random(2333);
        Queue<Customer> queue = new LinkedList<>();
        fill(queue, Customer.generator(), 15);
        List<Teller> list = new ArrayList<>();
        fill(list, Teller.generator(), 3);
        for (Customer c : queue) {
            serve(list.get(rand.nextInt(list.size())), c);
        }
    }
}

class Customer {
    private static long counter = 1;
    private final long id = counter++;

    private Customer() {
    }

    @Override
    public String toString() {
        return "Customer " + id;
    }

    public static Generator<Customer> generator() {
        return Customer::new;
    }
}

class Teller {
    private static long counter = 1;
    private final long id = counter++;

    private Teller() {
    }

    @Override
    public String toString() {
        return "Teller " + id;
    }

    public static Generator<Teller> generator() {
        return Teller::new;
    }
}
