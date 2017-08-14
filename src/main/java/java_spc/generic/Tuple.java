package java_spc.generic;

/**
 * 元组
 * 将一组对象直接打包存储为一个对象
 */
public class Tuple {
    private static class TwoTuple<A, B> {
        public final A a;
        public final B b;

        TwoTuple(A a, B b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ")";
        }
    }

    private static class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
        public final C c;

        ThreeTuple(A a, B b, C c) {
            super(a, b);
            this.c = c;
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ", " + c + ")";
        }
    }

    private static class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
        public final D d;

        FourTuple(A a, B b, C c, D d) {
            super(a, b, c);
            this.d = d;
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ", " + c + ", " + d + ")";
        }
    }

    private static class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {
        public final E e;

        FiveTuple(A a, B b, C c, D d, E e) {
            super(a, b, c, d);
            this.e = e;
        }

        @Override
        public String toString() {
            return "(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ")";
        }
    }

    public static TwoTuple<String, Integer> testTwoTuple() {
        return new TwoTuple<>("one", 2);
    }

    public static ThreeTuple<String, Integer, Long> testThreeTuple() {
        return new ThreeTuple<>("one", 2, 3L);
    }

    public static FourTuple<String, Integer, Long, Character> testFourTuple() {
        return new FourTuple<>("one", 2, 3L, '4');
    }

    public static FiveTuple<String, Integer, Long, Character, Double> testFiveTuple() {
        return new FiveTuple<>("one", 2, 3L, '4', 5.0);
    }

    public static void main(String[] args) {
        System.out.println(testTwoTuple());
        System.out.println(testThreeTuple());
        System.out.println(testFourTuple());
        System.out.println(testFiveTuple());
    }
}
