package java_spc.enum_type;

/**
 * 展示Enum的基本属性与方法
 *
 * @author SPC
 */
public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values()) {
            System.out.println("toString : " + s.toString());
            System.out.println("name : " + s.name());
            System.out.println("ordinal : " + s.ordinal());
            System.out.println("hashCode : " + s.hashCode());
            System.out.println("getClass : " + s.getClass());
            System.out.println("getDeclareClass : " + s.getDeclaringClass());
            System.out.println("compareTo : " + s.compareTo(Shrubbery.CRAWLING));
            System.out.println("equals : " + s.equals(Shrubbery.CRAWLING));
            System.out.println("== : " + (s == Shrubbery.CRAWLING));
            System.out.println("----------------------------------------------");
        }
        for (String s : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery sr = Enum.valueOf(Shrubbery.class, s);
            System.out.println("return instance of " + sr);
        }
    }
}

enum Shrubbery {
    GROUND,
    CRAWLING,
    HANGING
}