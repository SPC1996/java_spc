package java_spc.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DynamicProxy {
    public static void main(String[] args) {
//        SimpleProxyDemo.play();
//        SimpleDynamicProxy.play();
        MethodSelector.play();
    }
}

class SimpleProxyDemo {
    interface Interface {
        void doSomething();

        void somethingElse(String arg);
    }

    static class RealObject implements Interface {

        @Override
        public void doSomething() {
            System.out.println("doSomething");
        }

        @Override
        public void somethingElse(String arg) {
            System.out.println("somethingElse " + arg);
        }
    }

    static class SimpleProxy implements Interface {
        private Interface source;

        public SimpleProxy(Interface source) {
            this.source = source;
        }

        @Override
        public void doSomething() {
            System.out.println("proxy doSomething");
            source.doSomething();
        }

        @Override
        public void somethingElse(String arg) {
            System.out.println("proxy somethingElse");
            source.somethingElse(arg);
        }
    }

    public static void play() {
        RealObject real = new RealObject();
        real.doSomething();
        real.somethingElse("fucking ball");
        SimpleProxy proxy = new SimpleProxy(real);
        proxy.doSomething();
        proxy.somethingElse("fucking ball");
    }
}

class SimpleDynamicProxy {
    private static Pattern p = Pattern.compile("\\w+[.$]");

    static class DynamicProxyHandler implements InvocationHandler {
        private Object source;

        public DynamicProxyHandler(Object source) {
            this.source = source;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("**** proxy: "
                    + p.matcher(proxy.getClass().toString()).replaceAll("")
                    + ", method: " + p.matcher(method.toString()).replaceAll("")
                    + ", args: " + Arrays.toString(args));
            return method.invoke(source, args);
        }
    }

    public static void consumer(SimpleProxyDemo.Interface face) {
        face.doSomething();
        face.somethingElse("fucking ball");
    }

    public static void play() {
        SimpleProxyDemo.RealObject real = new SimpleProxyDemo.RealObject();
        consumer(real);
        SimpleProxyDemo.Interface proxy = (SimpleProxyDemo.Interface) Proxy.newProxyInstance(
                SimpleProxyDemo.Interface.class.getClassLoader(),
                new Class[]{SimpleProxyDemo.Interface.class},
                new DynamicProxyHandler(real));
        consumer(proxy);
    }
}

interface SomeMethods {
    void veryBoring();

    void boring();

    void littleBoring();

    void interesting();
}

class TakeMethods implements SomeMethods {
    @Override
    public void veryBoring() {
        System.out.println("very boring");
    }

    @Override
    public void boring() {
        System.out.println("boring");
    }

    @Override
    public void littleBoring() {
        System.out.println("little boring");
    }

    @Override
    public void interesting() {
        System.out.println("interesting");
    }
}

class MethodSelector implements InvocationHandler {
    private Object source;

    public MethodSelector(Object source) {
        this.source = source;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("interesting")) {
            System.out.println("proxy detected the method interesting");
        }
        return method.invoke(source, args);
    }

    public static void play() {
        SomeMethods proxy = (SomeMethods) Proxy.newProxyInstance(
                SomeMethods.class.getClassLoader(),
                new Class[]{SomeMethods.class},
                new MethodSelector(new TakeMethods())
        );
        proxy.veryBoring();
        proxy.boring();
        proxy.littleBoring();
        proxy.interesting();
    }
}
