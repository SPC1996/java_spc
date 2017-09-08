package java_spc.reflect;

public class DifferenceBetweenInstanceofAndClass {
    public static void test(Object object) {
        System.out.println("Testing object of type " + object.getClass());
        System.out.println("object instanceof Base " + (object instanceof Base));
        System.out.println("object instanceof Sub " + (object instanceof Sub));
        System.out.println("Base.isInstance(object) " + Base.class.isInstance(object));
        System.out.println("Sub.isInstance(object) " + Sub.class.isInstance(object));
        System.out.println("object.getClass()==Base.class " + (object.getClass() == Base.class));
        System.out.println("object.getClass()==Sub.class " + (object.getClass() == Sub.class));
        System.out.println("object.getClass().equals(Base.class) " + object.getClass().equals(Base.class));
        System.out.println("object.getClass().equals(Sub.class) " + object.getClass().equals(Sub.class));
    }

    public static void main(String[] args) {
        test(new Base());
        test(new Sub());
    }
}

class Base {
}

class Sub extends Base {
}
