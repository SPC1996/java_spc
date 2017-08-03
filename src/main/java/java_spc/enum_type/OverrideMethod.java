package java_spc.enum_type;

/**
 * 覆盖enum的方法
 *
 * @author SPC
 */
public enum OverrideMethod {
    LOW, MIDDLE, HIGH;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public static void main(String[] args) {
        for (OverrideMethod o : OverrideMethod.values()) {
            System.out.println(o);
        }
    }
}
