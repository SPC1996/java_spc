package java_spc.util;

import java.util.Random;

/**
 * 实现从enum实例中随机选择
 *
 * @author SPC
 */
public class Enums {
    private static Random rand=new Random(23333);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
}
