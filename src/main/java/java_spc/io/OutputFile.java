package java_spc.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;

import java_spc.io.InputFile;

public class OutputFile{
    public static void basicOutputWithBuffer(String readFName,String writeFName) throws IOException{ 
        LineNumberReader in=new LineNumberReader(new BufferedReader(new StringReader(InputFile.bufferedRead(readFName))));
        PrintWriter outWithBuffer=new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFName),"UTF-8")));
        String s;
        long t1_before=System.currentTimeMillis();
        while((s=in.readLine())!=null){
            outWithBuffer.println(in.getLineNumber() + ": "+s);
        }
        long t1_after=System.currentTimeMillis();
        outWithBuffer.close();
        System.out.println("out with buffer time:"+(t1_after-t1_before));
    }

    public static void basicOutputNoBuffer(String readFName,String writeFName) throws IOException{ 
        LineNumberReader in=new LineNumberReader(new BufferedReader(new StringReader(InputFile.bufferedRead(readFName))));
        PrintWriter outWithNoBuffer=new PrintWriter(new OutputStreamWriter(new FileOutputStream(writeFName),"UTF-8"));
        String s;
        long t2_before=System.currentTimeMillis();
        while((s=in.readLine())!=null){
            outWithNoBuffer.println(in.getLineNumber() + ": "+s);
        }
        long t2_after=System.currentTimeMillis();
        outWithNoBuffer.close();
        System.out.println("out no buffer time:"+(t2_after-t2_before));
    }

    public static void shortOutput(String readFName,String writeFName) throws IOException{
        BufferedReader in=new BufferedReader(new StringReader(InputFile.bufferedRead(readFName)));
        PrintWriter out=new PrintWriter(writeFName,"UTF-8");
        int lineCount=1;
        String s;
        while((s=in.readLine())!=null){
            out.println(lineCount++ + ": "+s);
        }
        out.close();
    }

    public static void storeAndRecoverData(String fileName) throws IOException{
        DataOutputStream out=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        out.writeDouble(3.1415926);
        out.writeUTF("这是 pi");
        out.writeDouble(1.41413);
        out.writeUTF("That's square of root 2");
        out.close();
        DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
        in.close();

    }

    public static void randomAccessFile(String fileName)throws IOException{
        RandomAccessFile rf=new RandomAccessFile(fileName, "rw");
        for(int i=0;i<7;i++){
            rf.writeDouble(i*1.414);
        }
        rf.writeUTF("The end of file");
        rf.close();
        display(fileName);
        rf=new RandomAccessFile(fileName,"rw");
        rf.seek(5*8);
        rf.writeDouble(47.001);
        rf.close();
        display(fileName);
    }

    private static void display(String fileName) throws IOException{
        RandomAccessFile rf=new RandomAccessFile(fileName, "r");
        for(int i=0;i<7;i++){
            System.out.println("Value "+i+": "+rf.readDouble());
        }
        System.out.println(rf.readUTF());
        rf.close();
    } 

    public static void main(String[] args) throws IOException{
        //basicOutputWithBuffer("./res/test/note.txt", "./res/test/note1.out");
        //basicOutputNoBuffer("./res/test/note.txt", "./res/test/note2.out");
        //shortOutput("./res/test/sing.txt", "./res/test/sing.out");
        //System.out.println(InputFile.bufferedRead("./res/test/sing.out"));
        //storeAndRecoverData("./res/test/data.txt");
        randomAccessFile("./res/test/data.txt");
    }
}