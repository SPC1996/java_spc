package java_spc.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OSExecute{
    public static void command(String command){
        boolean err=false;
        try {
            Process process=new ProcessBuilder(command.split(" ")).start();
            BufferedReader results=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while((s=results.readLine())!=null){
                System.out.println(s);
            }
            BufferedReader errors=new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while((s=errors.readLine())!=null){
                System.err.println(s);
                err=true;
            }
        } catch (Exception e) {
            if(!command.startsWith("CMD /C")){
                command("CMD /C"+command);
            } else {
                throw new RuntimeException();
            }
        }
        if(err){
            throw new OSExecuteException("Errors executing "+command);
        }
    }

    public ArrayList<String> commandReturnList(String command){
        boolean err=false;
        ArrayList<String> list=new ArrayList<>();
        try {
            Process process=new ProcessBuilder(command.split(" ")).start();
            BufferedReader results=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while((s=results.readLine())!=null){
                list.add(s);
            }
            BufferedReader errors=new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while((s=errors.readLine())!=null){
                list.add(s);
                err=true;
            }
        } catch (Exception e) {
            if(!command.startsWith("CMD /C")){
                command("CMD /C "+command);
            } else {
                throw new RuntimeException();
            }
        }
        if(err){
            throw new OSExecuteException("Errors executing "+command);
        }
        return list;
    }
}