package java_spc.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型中通配符的使用
 */
public class WildcardInGeneric {
    public static void main(String[] args) {
        CovariantArrays.play();
        GenericsAndCovariance.play();
        CompilerIntelligence.play();
        GenericWritingAndReading.play1();
        GenericWritingAndReading.play2();
        Wildcards.play();
    }
}

class Fruit {
}

class Apple extends Fruit {
}

class Orange extends Fruit {
}

class Jonathon extends Apple {
}

class CovariantArrays {
    public static void play() {
        Fruit[] fruit = new Apple[10];
        fruit[0] = new Apple();
        fruit[1] = new Jonathon();
        try {
            fruit[0] = new Fruit();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            fruit[0] = new Orange();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class GenericsAndCovariance {
    public static void play() {
        List<? extends Fruit> fruits = new ArrayList<Apple>();
        //error
        //fruits.add(new Apple());
        //fruits.add(new Orange());
        fruits.add(null);
        System.out.println(fruits);
    }
}

class CompilerIntelligence {
    public static void play() {
        List<? extends Fruit> fruits = Arrays.asList(new Apple(), new Orange());
        Apple apple = (Apple) fruits.get(0);
        System.out.println(fruits.contains(apple));
        System.out.println(fruits.indexOf(apple));
    }
}

class GenericWritingAndReading {
    private static List<Apple> apples = new ArrayList<>();
    private static List<Fruit> fruits = new ArrayList<>();

    public static <T> void writeExact(List<T> list, T item) {
        list.add(item);
    }

    public static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }

    public static <T> T readExact(List<T> list, int index) {
        return index >= list.size() ? null : list.get(index);
    }

    public static <T> T readWithWildcard(List<? extends T> list, int index) {
        return index >= list.size() ? null : list.get(index);
    }

    public static void play1() {
        writeExact(apples, new Apple());
        writeExact(fruits, new Apple());
        Apple apple = (Apple) readExact(fruits, 0);
        Fruit fruit = readExact(apples, 0);
    }

    public static void play2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruits, new Apple());
        readWithWildcard(fruits, 0);
        readWithWildcard(apples, 0);
    }
}

class Holder<T> {
    private T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj);
    }
}

class Wildcards {
    public static void rawArgs(Holder holder, Object arg) {
//        Unchecked call to setValue(T) as a member of the raw type Holder
//        holder.setValue(arg);
//        holder.setValue(new Wildcards());
        Object object = holder.getValue();
    }

    public static void unboundedArgs(Holder<?> holder, Object arg) {
//        error
//        holder.setValue(arg);
        Object object = holder.getValue();
    }

    public static <T> T exact1(Holder<T> holder) {
        T t = holder.getValue();
        return t;
    }

    public static <T> T exact2(Holder<T> holder, T arg) {
        holder.setValue(arg);
        T t = holder.getValue();
        return t;
    }

    public static <T> T wildSubType(Holder<? extends T> holder, T arg) {
//        error
//        setValue(capture<? extends T>) in Holder cannot be applied to (T)
//        holder.setValue(arg);
        T t = holder.getValue();
        return t;
    }

    public static <T> void wildSupertype(Holder<? super T> holder, T arg) {
        holder.setValue(arg);
//        error
//        Incompatible types.
//        Required: T
//        Found: capture<? super T>
//        T t=holder.getValue();
        Object object = holder.getValue();
    }

    public static void play() {
        Holder raw = new Holder();
        Holder<Long> qualified = new Holder<>();
        Holder<?> unbounded = new Holder<>();
        Holder<? extends Long> bounded = new Holder<>();
        Long lng = 1L;

        rawArgs(raw, lng);
        rawArgs(qualified, lng);
        rawArgs(unbounded, lng);
        rawArgs(bounded, lng);

        unboundedArgs(raw, lng);
        unboundedArgs(raw, lng);
        unboundedArgs(unbounded, lng);
        unboundedArgs(bounded, lng);

//        Unchecked assignment: 'java_spc.generic.Holder' to 'java_spc.generic.Holder<java.lang.Object>
//        Object r1=exact1(raw);
        Long r2 = exact1(qualified);
        Object r3 = exact1(unbounded);
        Long r4 = exact1(bounded);

//        Unchecked assignment: 'java_spc.generic.Holder' to 'java_spc.generic.Holder<java.lang.Long>
//        Long r5=exact2(raw, lng);
        Long r6 = exact2(qualified, lng);
//        error
//        exact2(java_spc.generic.Holder<T>, T) in Wildcards cannot be applied to (java_spc.generic.Holder<capture<?>>, java.lang.Long)
//        Long r7=exact2(unbounded, lng);
//        error
//        exact2(java_spc.generic.Holder<T>, T) in Wildcards cannot be applied to (java_spc.generic.Holder<capture<? extends java.lang.Long>>, java.lang.Long)
//        Long r8=exact2(bounded, lng);

//        Unchecked assignment: 'java_spc.generic.Holder' to 'java_spc.generic.Holder<? extends java.lang.Long>
//        Long r9=wildSubType(raw, lng);
        Long r10 = wildSubType(qualified, lng);
        Object r11 = wildSubType(unbounded, lng);
        Long r12 = wildSubType(bounded, lng);

//        Unchecked assignment: 'java_spc.generic.Holder' to 'java_spc.generic.Holder<java.lang.Long>
//        wildSupertype(raw, lng);
        wildSupertype(qualified, lng);
//        error
//        wildSupertype(java_spc.generic.Holder<? super T>, T) in Wildcards cannot be applied to (java_spc.generic.Holder<capture<?>>, java.lang.Long)
//        wildSupertype(unbounded, lng);
//        error
//        wildSupertype(java_spc.generic.Holder<? super T>, T) in Wildcards cannot be applied to (java_spc.generic.Holder<capture<? extends java.lang.Long>>, java.lang.Long)
//        wildSupertype(bounded, lng);
    }
}

class CaptureConversion {
    public static <T> void f1(Holder<T> holder) {
        T t = holder.getValue();
        System.out.println(t.getClass().getSimpleName());
    }

    public static void f2(Holder<?> holder) {
        f1(holder);
    }

    public static void play() {
        Holder raw = new Holder<>(1);
//        Unchecked assignment: 'java_spc.generic.Holder' to 'java_spc.generic.Holder<java.lang.Object>
//        f1(raw);
        f2(raw);
        Holder rawBasic = new Holder();
//        Unchecked call to 'setValue(T)' as a member of raw type 'java_spc.generic.Holder'/
//        rawBasic.setValue(new Object());
        f2(rawBasic);
        Holder<?> wildcarded = new Holder<>(1.0);
        f2(wildcarded);
    }
}