package java_spc.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ShowClass {
    private static Pattern p = Pattern.compile("\\w+\\.");

    public static void showMethods(String className) {
        try {
            Class<?> c = Class.forName(className);
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(p.matcher(method.toString()).replaceAll(""));
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showConstructors(String className) {
        try {
            Class<?> c = Class.forName(className);
            Constructor[] constructors = c.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                System.out.println(p.matcher(constructor.toString()).replaceAll(""));
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showFields(String className) {
        try {
            Class<?> c = Class.forName(className);
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(p.matcher(field.toString()).replaceAll(""));
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String className = "java.lang.Class";
        showConstructors(className);
        showMethods(className);
        showFields(className);

    }
}
