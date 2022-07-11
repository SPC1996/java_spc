package java_spc.dubbo.spi;

public class Dog implements Animal{
    @Override
    public void say() {
        System.out.println("Dog say: Hello World!!!");
    }
}
