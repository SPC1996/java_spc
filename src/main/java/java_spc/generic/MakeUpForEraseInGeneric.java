package java_spc.generic;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 对java泛型中擦除机制导致的影响进行补偿
 */
public class MakeUpForEraseInGeneric {
    public static void main(String[] args) {
        ClassTypeCapture.play();
        InstantiateGenericType.play();
        FactoryConstant.play();
        CreatorGeneric.play();
        GenericArray.play();
        GenericArrayWithType.play();
    }
}

class ClassTypeCapture<T> {
    public static class Building {
    }

    public static class Horse extends Building {
    }

    private Map<String, Class<?>> map = new HashMap<>();
    private Class<T> kind;

    public ClassTypeCapture(Class<T> kind) {
        this.kind = kind;
    }

    public void addType(String typeName, Class<?> kind) {
        map.put(typeName, kind);
    }

    public Object createNew(String typeName) {
        if (map.containsKey(typeName)) {
            try {
                return map.get(typeName).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("failed");
            throw new RuntimeException();
        }
    }

    public boolean f(Object obj) {
        return kind.isInstance(obj);
    }

    public static void play() {
        ClassTypeCapture<Building> ctcb = new ClassTypeCapture<>(Building.class);
        System.out.println("Building is instance of Building? " + ctcb.f(new Building()));
        System.out.println("Horse is instance of Building? " + ctcb.f(new Horse()));
        ClassTypeCapture<Horse> ctch = new ClassTypeCapture<>(Horse.class);
        ctch.addType("horse", Horse.class);
        ctch.addType("building", Building.class);
        System.out.println("Building is instance of Horse? " + ctch.f(ctch.createNew("building")));
        System.out.println("Horse is instance of Horse? " + ctch.f(ctch.createNew("horse")));
    }
}

class InstantiateGenericType {
    public static class ClassAsFactory<T> {
        private T t;

        public ClassAsFactory(Class<T> kind) {
            try {
                t = kind.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Emplyee {
    }

    public static void play() {
        ClassAsFactory<Emplyee> fe = new ClassAsFactory<>(Emplyee.class);
        System.out.println("Employee factory succeed");
        try {
            //Integer 没有默认的构造器
            ClassAsFactory<Integer> fi = new ClassAsFactory<>(Integer.class);
        } catch (Exception e) {
            System.out.println("Integer factory failed");
        }
    }
}

class FactoryConstant<T> {
    interface Factory<T> {
        T create();
    }

    public static class IntegerFactory implements Factory<Integer> {
        @Override
        public Integer create() {
            return 0;
        }
    }

    public static class Widget {
        class WidgetFactory implements Factory<Widget> {
            @Override
            public Widget create() {
                return new Widget();
            }
        }
    }


    private T t;

    public <F extends Factory<T>> FactoryConstant(F factory) {
        t = factory.create();
    }

    public static void play() {
        new FactoryConstant<>(new IntegerFactory());
        System.out.println("Integer factory succeed");
        new FactoryConstant<>(new Widget().new WidgetFactory());
        System.out.println("Widget factory succeed");
    }
}

class CreatorGeneric<T> {
    static abstract class GenericWithCreate<T> {
        final T element;

        GenericWithCreate() {
            element = create();
        }

        abstract T create();
    }

    static class X {
    }

    static class Creator extends GenericWithCreate<X> {
        @Override
        X create() {
            return new X();
        }

        void f() {
            System.out.println(element.getClass().getSimpleName());
        }
    }

    public static void play() {
        Creator c = new Creator();
        c.f();
    }
}

class GenericArray<T> {
    private Object[] array;

    public GenericArray(int size) {
        array = new Object[size];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) array[index];
    }

    @SuppressWarnings("unchecked")
    public T[] rep() {
        return (T[]) array;
    }

    public static void play() {
        GenericArray<Integer> gai = new GenericArray<>(10);
        for (int i = 0; i < 10; i++) {
            gai.put(i, 2 * i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(gai.get(i) + " ");
        }
        System.out.println();
        try {
            Integer[] ia = gai.rep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class GenericArrayWithType<T> {
    private T[] array;

    @SuppressWarnings("unchecked")
    public GenericArrayWithType(Class<T> type, int size) {
        array = (T[]) Array.newInstance(type, size);
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] rep() {
        return array;
    }

    public static void play() {
        GenericArrayWithType<Integer> gia = new GenericArrayWithType<>(Integer.class, 10);
        Integer[] ia = gia.rep();
        System.out.println(ia.getClass());
    }
}