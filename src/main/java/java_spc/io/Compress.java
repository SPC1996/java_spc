package java_spc.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java_spc.util.Resource;

public class Compress{
    public static void gzip(String source, String dist) throws IOException{
    	BufferedReader in=new BufferedReader(
    			new InputStreamReader(new FileInputStream(source), "utf-8"));
    	BufferedWriter out=new BufferedWriter(new OutputStreamWriter(
    			new GZIPOutputStream(new FileOutputStream(dist)),"utf-8"));
    	int c;
    	while((c=in.read())!=-1){
    		out.write(c);
    	}
    	in.close();
    	out.close();
    }
    
    public static void displayGZipFile(String fileName) throws IOException{
    	BufferedReader in=new BufferedReader(new InputStreamReader(
    			new GZIPInputStream(new FileInputStream(fileName)),"utf-8"));
    	String s;
    	while((s=in.readLine())!=null){
    		System.out.println(s);
    	}
    	in.close();
    }
    
    public static void zip(String[] sources, String dist) throws IOException{
    	FileOutputStream f=new FileOutputStream(dist);
    	CheckedOutputStream csum=new CheckedOutputStream(f, new Adler32());
    	ZipOutputStream zos=new ZipOutputStream(csum);
    	BufferedWriter out=new BufferedWriter(
    			new OutputStreamWriter(zos,"utf-8"));
    	zos.setComment("a test for java zipping!");
    	
    	for(String source:sources){
    		BufferedReader in=new BufferedReader(
    					new InputStreamReader(new FileInputStream(source), "utf-8"));
    		zos.putNextEntry(new ZipEntry(new File(source).getName()));
    		int c;
    		while((c=in.read())!=-1){
    			out.write(c);
    		}
    		in.close();
    		out.flush();
    	}
    	out.close();
    }
    
    public static void displayZipFile(String fileName) throws IOException{
    	FileInputStream f=new FileInputStream(fileName);
    	CheckedInputStream csum=new CheckedInputStream(f, new Adler32());
    	ZipInputStream zin=new ZipInputStream(csum);
    	BufferedReader in=new BufferedReader(
    			new InputStreamReader(zin, "utf-8"));
    	ZipEntry entry;
    	while((entry=zin.getNextEntry())!=null){
    		System.out.println("Begin reading file "+entry+" -----------------------------");
    		String s;
    		while((s=in.readLine())!=null){
    			System.out.println(s);
    		}
    		System.out.println("End reading file "+entry+" -----------------------------");
    	}
    	in.close();
    }
    
    public static void main(String[] args) throws IOException{
//    	gzip(Resource.pathToSource("file/sing.txt"), Resource.pathToSource("file/sing.txt.gz"));
//    	displayGZipFile(Resource.pathToSource("file/sing.txt.gz"));
    	String[] files={Resource.pathToSource("file/sing.txt"),Resource.pathToSource("file/note.txt")};
    	zip(files, Resource.pathToSource("file/sing_note.zip"));
    	displayZipFile(Resource.pathToSource("file/sing_note.zip"));
    }
}