package java_spc.tutorials.custom_networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class UseNetworkInterface {
    public static void main(String[] args) throws IOException {
        showNetworkInterface();
        listNIFs();
        listNets();
    }

    private static void showNetworkInterface() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("en0");
        Enumeration<InetAddress> nifAddress = nif.getInetAddresses();
        while (nifAddress.hasMoreElements()) {
            System.out.println(nifAddress.nextElement());
        }
    }

    private static void listNIFs() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface nif : Collections.list(nets)) {
            System.out.printf("display name: %s%n", nif.getDisplayName());
            System.out.printf("name: %s%n", nif.getName());
            displaySubInterfaces(nif);
            System.out.println();
        }
    }

    private static void displaySubInterfaces(NetworkInterface nif) {
        Enumeration<NetworkInterface> subIfs = nif.getSubInterfaces();
        for (NetworkInterface subIf : Collections.list(subIfs)) {
            System.out.printf("\tsub interface display name: %s%n", subIf.getDisplayName());
            System.out.printf("\tsub interface name: %s%n", subIf.getName());
        }
    }

    private static void listNets() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface net : Collections.list(nets)) {
            displayInterfaceInformation(net);
        }
    }

    private static void displayInterfaceInformation(NetworkInterface net) throws SocketException {
        System.out.printf("display name: %s%n", net.getDisplayName());
        System.out.printf("name: %s%n", net.getName());
        Enumeration<InetAddress> addresses = net.getInetAddresses();
        for (InetAddress address : Collections.list(addresses)) {
            System.out.printf("inet address: %s%n", address);
        }
        System.out.printf("up? %s%n", net.isUp());
        System.out.printf("loopback? %s%n", net.isLoopback());
        System.out.printf("point to point? %s%n", net.isPointToPoint());
        System.out.printf("support multicast? %s%n", net.supportsMulticast());
        System.out.printf("virtual? %s%n", net.isVirtual());
        System.out.printf("hardware address: %s%n", Arrays.toString(net.getHardwareAddress()));
        System.out.printf("MTU: %s%n", net.getMTU());
        System.out.println();
    }
}
