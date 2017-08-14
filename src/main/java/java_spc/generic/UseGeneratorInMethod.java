package java_spc.generic;

import java_spc.util.Generator;

import java.util.*;

/**
 * 在方法中使用泛型
 */
public class UseGeneratorInMethod {
    public static <T> void showClassName(T t) {
        System.out.println(t.getClass().getName());
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<>();
    }

    public static void showMap(Map<String, List<? extends Coffee>> map) {
        System.out.println(map.getClass().getName());
    }

    @SafeVarargs
    public static <T> List<T> makeList(T... args) {
        List<T> result = new ArrayList<>();
        Collections.addAll(result, args);
        return result;
    }

    public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {
        for (int i = 0; i < n; i++) {
            coll.add(gen.next());
        }
        return coll;
    }

    public static void main(String[] args) {
        System.out.println("showClassName()--------------------------------------------");
        showClassName(1);
        showClassName(1L);
        showClassName(true);
        System.out.println("-----------------------------------------------------------");
        System.out.println("showMap()--------------------------------------------------");
        //普通的赋值操作和将一个泛型方法调用的结果作为参数传递给另一个参数都会执行类型推断(jdk8)
        showMap(new HashMap<>());
        showMap(map());
        System.out.println("-----------------------------------------------------------");
        System.out.println("makeList()-------------------------------------------------");
        List<Object> list = makeList("A", 1, true);
        for (Object o : list) {
            showClassName(o);
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("fill()-----------------------------------------------------");
        Collection<Coffee> coffees = fill(new ArrayList<>(), new CoffeeGenerator(), 5);
        System.out.println(coffees);
        System.out.println("-----------------------------------------------------------");
    }
}

