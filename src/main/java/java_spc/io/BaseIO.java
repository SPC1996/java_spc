package java_spc.io;

import java.io.*;

import java_spc.util.Resource;

public class BaseIO{
    public static void echo() throws IOException{
        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in,"utf-8"));
        String s;
        while((s=stdin.readLine())!=null&&!s.equals("exit")){
            System.out.println(s.toUpperCase());
        }
    }

    public static PrintWriter changeSystemOut(){
        return new PrintWriter(System.out,true);
    }

    public static void redirect(String fileRName,String fileWName) throws IOException{
        PrintStream console=System.out;
        BufferedInputStream in=new BufferedInputStream(new FileInputStream(fileRName));
        PrintStream out=new PrintStream(new BufferedOutputStream(new FileOutputStream(fileWName)));
        System.setIn(in);
        System.setOut(out);
        System.setErr(out);
        // BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        // String s;
        // while((s=br.readLine())!=null){
        //     System.out.println(s);
        // }
        // Scanner scanner=new Scanner(System.in);
        // while(scanner.hasNext()){
        //     System.out.println(scanner.nextLine());
        // }
        InputStream ri=System.in;
        byte[] b=new byte[1024];
        @SuppressWarnings("unused")
		int count;
        while((count=ri.read(b))!=-1){
            System.out.write(b);
        }
        out.close();
        in.close();
        System.setIn(System.in);
        System.setOut(console);
        System.setErr(System.err);

    }

    public static void main(String[] args) throws IOException{
//         echo();
        redirect(Resource.pathToSource("file/sing.txt"), Resource.pathToSource("file/sing.out"));
    }
}