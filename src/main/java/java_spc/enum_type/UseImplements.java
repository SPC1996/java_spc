package java_spc.enum_type;

import java.util.Random;

/**
 * 由于enum默认继承Enum类，所以enum不能继承其他类
 * 所以使用继承来赋予其新的特性
 *
 * @author SPC
 */
public class UseImplements {
    public static <T> void printNext(Generator<T> g) {
        System.out.print(g.next() + ", ");
    }

    public static void main(String[] args) {
        Character c = Character.A;
        for (int i = 0; i < 26; i++) {
            printNext(c);
        }
    }
}

enum Character implements Generator<Character> {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

    private Random rand = new Random(2333);

    @Override
    public Character next() {
        return values()[rand.nextInt(values().length)];
    }
}

interface Generator<T> {
    T next();
}