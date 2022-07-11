package java_spc.dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class DubboSpiTest {
    public static void main(String[] args) {
        ExtensionLoader<Animal> extensionLoader = ExtensionLoader.getExtensionLoader(Animal.class);
        System.out.println("Dubbo SPI Test!!!");
        extensionLoader.getSupportedExtensions().forEach(name -> extensionLoader.getExtension(name).say());
    }
}
