package java_spc.generic;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 关于泛型中擦除的研究
 * java泛型是使用擦除实现的，是一种伪泛型，
 * 当使用泛型时，任何具体的类型信息都被擦除了
 */
public class EraseInGeneric {
    private static class Frob {
    }

    private static class Fnorkle {
    }

    private static class Quark<Q> {
    }

    private static class Particle<P, M> {
    }

    private static class HasF {
        public void f() {
            System.out.println("HasF.f()");
        }
    }

    /**
     * c++实现了真正的泛型，这段代码可以这样写
     * *******************************************
     * <pre>
     *     #include<iostream>
     *     using namespace std;
     *
     *     template<class T> class Manipulator {
     *         T obj;
     *         public :
     *              Manipulator(T obj) {
     *                  this.obj=obj;
     *              }
     *              void manipulate() {
     *                  //重点：此时obj可以直接调用HasF中的f(), 但是没有java中T extends HasF
     *                  obj.f();
     *              }
     *     };
     *
     *     class HasF {
     *         void f() {
     *             cout<<"HasF::f()"<<endl;
     *         }
     *     }
     *
     *     int main() {
     *         HasF hf;
     *         Manipulator<HasF> manipulator(hf);
     *         manipulator.manipulate();
     *     }
     * </pre>
     * *******************************************
     */
    private static class Manipulator<T extends HasF> {
        private T obj;

        public Manipulator(T obj) {
            this.obj = obj;
        }

        public void manipulate() {
            obj.f();
        }
    }

    private static class ArrayMaker<T> {
        private Class<T> kind;

        public ArrayMaker(Class<T> kind) {
            this.kind = kind;
        }

        @SuppressWarnings("unchecked")
        public T[] create(int size) {
            return (T[]) Array.newInstance(kind, size);
        }
    }

    private static class ListMaker<T> {
        List<T> create(T t) {
            return Arrays.asList(t, t, t, t);
        }
    }

    public static void eraseType() {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2);
    }

    public static void loseInformation() {
        List<Frob> list = new ArrayList<>();
        Map<Frob, Fnorkle> map = new HashMap<>();
        Quark<Fnorkle> quark = new Quark<>();
        Particle<Long, Double> particle = new Particle<>();
        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(particle.getClass().getTypeParameters()));

    }

    public static void manipulation() {
        HasF hf = new HasF();
        Manipulator<HasF> manipulator = new Manipulator<>(hf);
        manipulator.manipulate();
    }

    public static void actionInBound() {
        ArrayMaker<String> arrayMaker = new ArrayMaker<>(String.class);
        String[] stringArray = arrayMaker.create(9);
        System.out.println(Arrays.toString(stringArray));
        ListMaker<String> listMaker = new ListMaker<>();
        List<String> stringList = listMaker.create("spc");
        System.out.println(stringList);
    }

    public static void main(String[] args) {
        eraseType();
        loseInformation();
        manipulation();
        actionInBound();
    }
}

