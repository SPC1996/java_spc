package java_spc.util;

import java.io.PrintStream;

public class SPrint{
    public static void print(Object obj){
        System.out.println(obj);
    }

    public static void print(){
        System.out.println();
    }

    public static void printb(Object obj){
        System.out.print(obj);
    }

    public static PrintStream printf(String format,Object... args){
        return System.out.printf(format, args);
    }
}