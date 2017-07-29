package java_spc.io;

import java_spc.util.OSExecute;

public class OSExecuteDemo{
    public static void main(String[] args){
        OSExecute.command("javap -classpath "+OSExecuteDemo.class.getResource("/").getFile()+" "+OSExecuteDemo.class.getName());
    }
}