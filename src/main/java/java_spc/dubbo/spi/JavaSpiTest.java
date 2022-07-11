package java_spc.dubbo.spi;

import java.util.ServiceLoader;

public class JavaSpiTest {
    public static void main(String[] args) {
        ServiceLoader<Animal> serviceLoader = ServiceLoader.load(Animal.class);
        System.out.println("Java SPI Test!!!");
        serviceLoader.forEach(Animal::say);
    }
}
