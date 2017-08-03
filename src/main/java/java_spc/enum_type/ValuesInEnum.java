package java_spc.enum_type;

import java_spc.util.OSExecute;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * 编译器创建的enum都继承自Enum类，
 * Enum中并无values()方法，
 * 通过反编译判断探寻真相
 * <p>
 * 反编译结果:
 * final class java_spc.enum_type.Explore extends java.lang.Enum<java_spc.enum_type.Explore> {
 * public static final java_spc.enum_type.Explore HERE;
 * public static final java_spc.enum_type.Explore THERE;
 * public static java_spc.enum_type.Explore[] values();
 * public static java_spc.enum_type.Explore valueOf(java.lang.String);
 * static {};
 * }
 * <p>
 * 结论:values()和valueOf()是由编译器添加的static方法
 * 添加的valueOf()只有一个参数，Enum中的valueOf()有两个参数
 *
 * @author SPC
 */
public class ValuesInEnum {
    public static Set<String> analyze(Class<?> enumClass) {
        System.out.println("----- Analyze " + enumClass + " -----");
        System.out.println("Interfaces : ");
        for (Type t : enumClass.getGenericInterfaces()) {
            System.out.println(t);
        }
        System.out.println("Base : " + enumClass.getSuperclass());
        System.out.println("Method : ");
        Set<String> methods = new TreeSet<>();
        for (Method m : enumClass.getMethods()) {
            methods.add(m.getName());
        }
        System.out.println(methods);
        return methods;
    }

    public static void upCast() {
        Enum e = Explore.HERE;
        for (Enum en : e.getClass().getEnumConstants())
            System.out.println(en);
    }

    public static void main(String[] args) {
        upCast();
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);
        System.out.println("Explore.containsAll(Enum)? " + exploreMethods.containsAll(enumMethods));
        System.out.println("Explore.removeAll(Enum) : ");
        exploreMethods.removeAll(enumMethods);
        System.out.println("Rest Of Explore Methods : " + exploreMethods);
        OSExecute.command("javap -classpath " + Explore.class.getResource("/").getFile() + " " + Explore.class.getName());
    }
}

enum Explore {
    HERE,
    THERE
}