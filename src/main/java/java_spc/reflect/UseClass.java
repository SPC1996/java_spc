package java_spc.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UseClass {
    public static void main(String[] args) throws Exception {
        SweetShop.play();
        ToyTest.play();
        ClassInitialization.play();
        FilledList.play();
    }
}

class Format {
    public static void before(Class c) {
        System.out.println("before " + c.getSimpleName() + " playing ---------------------------------------------");
    }

    public static void after(Class c) {
        System.out.println("after " + c.getSimpleName() + " playing ----------------------------------------------");
    }
}

class SweetShop {
    static class Candy {
        static {
            System.out.println("loading Candy");
        }
    }

    static class Gum {
        static {
            System.out.println("Loading Gum");
        }
    }

    static class Cookie {
        static {
            System.out.println("Loading Cookie");
        }
    }

    public static void play() {
        Format.before(SweetShop.class);
        System.out.println("inside main");
        new Candy();
        System.out.println("after creating Candy");
        try {
            Class.forName("Gum");
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find Gum");
        }
        System.out.println("after Class.forName(\"Gum\")");
        new Cookie();
        System.out.println("after creating Cookie");
        Format.after(SweetShop.class);
    }
}

class ToyTest {
    interface HasBatteries {
    }

    interface WaterProof {
    }

    interface Shoots {
    }

    static class Toy {
        private int i;

        public Toy() {
        }

        public Toy(int i) {
            this.i = i;
        }
    }

    static class FancyToy extends Toy implements HasBatteries, WaterProof, Shoots {
        public FancyToy(int i) {
            super(i);
        }
    }

    public static void printInfo(Class cc) {
        System.out.println("Class name: " + cc.getName() + " is interface? [" + cc.isInterface() + "]");
        System.out.println("Simple name: " + cc.getSimpleName());
        System.out.println("Canonical name: " + cc.getCanonicalName());
    }

    public static void play() {
        Format.before(ToyTest.class);
        Class c = null;
        try {
            c = Class.forName("java_spc.reflect.ToyTest$FancyToy");
        } catch (ClassNotFoundException e) {
            System.out.println("can't find FancyToy");
            System.exit(1);
        }
        printInfo(c);
        for (Class face : c.getInterfaces()) {
            printInfo(face);
        }
        Class up = c.getSuperclass();
        Object object = null;
        try {
            object = up.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("cannot access");
            System.exit(1);
        }
        printInfo(object.getClass());
        Format.after(ToyTest.class);
    }
}

class ClassInitialization {
    private static Random random = new Random(2333);

    static class Initable {
        static final int staticFinal = 47;
        static final int staticFinal2 = random.nextInt(1001);

        //3
        static {
            System.out.println("Initializing Initable");
        }
    }

    static class Initable2 {
        static int staticNoFinal = 74;

        //5
        static {
            System.out.println("Initializing Initable2");
        }
    }

    static class Initable3 {
        static int staticNoFinal = 47;

        //7
        static {
            System.out.println("Initializing Initable3");
        }
    }

    public static void play() throws ClassNotFoundException {
        Format.before(ClassInitialization.class);
        //Initable.class不会立刻对类进行初始化
        Class initable = Initable.class;
        //1
        System.out.println("After creating Initable ref");
        //2 编译期常量 不需要对类进行初始化就可读取
        System.out.println(Initable.staticFinal);
        //4
        System.out.println(Initable.staticFinal2);
        //6
        System.out.println(Initable2.staticNoFinal);
        Class initable3 = Class.forName("java_spc.reflect.ClassInitialization$Initable3");
        //8
        System.out.println("After creating Initable3 ref");
        //9
        System.out.println(Initable3.staticNoFinal);
        Format.after(ClassInitialization.class);
    }
}

class FilledList<T> {
    private Class<T> type;

    static class CountedInteger {
        private static long counter;
        private final long id = counter++;

        @Override
        public String toString() {
            return Long.toString(id);
        }
    }

    public FilledList(Class<T> type) {
        this.type = type;
    }

    public List<T> create(int n) {
        List<T> result = new ArrayList<>();
        try {
            for (int i = 0; i < n; i++) {
                result.add(type.newInstance());
            }
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println(e);
        }
        return result;
    }

    public static void play() {
        Format.before(FilledList.class);
        FilledList<CountedInteger> filledList = new FilledList<>(CountedInteger.class);
        System.out.println(filledList.create(10));
        Format.after(FilledList.class);
    }
}

