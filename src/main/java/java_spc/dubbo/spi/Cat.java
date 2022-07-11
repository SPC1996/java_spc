package java_spc.dubbo.spi;

public class Cat implements Animal{
    @Override
    public void say() {
        System.out.println("Cat say: Hello World!!!");
    }
}
