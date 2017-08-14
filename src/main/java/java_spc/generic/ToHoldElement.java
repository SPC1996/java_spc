package java_spc.generic;

public class ToHoldElement {
    private static class Element {
        @Override
        public String toString() {
            return "ELEMENT";
        }
    }

    private static class God {
        @Override
        public String toString() {
            return "GOD";
        }
    }

    private static class Collection1 {
        private Element e;

        Collection1(Element e) {
            this.e = e;
        }

        Element get() {
            return e;
        }

        void set(Element e) {
            this.e = e;
        }
    }

    private static class Collection2 {
        private Object o;

        Collection2(Object o) {
            this.o = o;
        }

        Object get() {
            return o;
        }

        void set(Object o) {
            this.o = o;
        }
    }

    private static class Collection3<T> {
        private T t;

        Collection3(T t) {
            this.t = t;
        }

        T get() {
            return t;
        }

        void set(T t) {
            this.t = t;
        }
    }

    public static void main(String[] args) {
        System.out.println("Case 1 : -----------------------------------");
        Collection1 c1 = new Collection1(new Element());
        System.out.println("Only can save " + c1.get());
        System.out.println("--------------------------------------------");
        System.out.println("Case 2 : -----------------------------------");
        Collection2 c2 = new Collection2(new Element());
        System.out.println("Can save " + c2.get());
        c2.set(new God());
        System.out.println("Also can save " + c2.get() + " or other");
        System.out.println("--------------------------------------------");
        System.out.println("Case 3 : -----------------------------------");
        Collection3<Element> c3 = new Collection3<>(new Element());
        System.out.println("Can save one type like " + c3.get());
        System.out.println("--------------------------------------------");
    }
}

