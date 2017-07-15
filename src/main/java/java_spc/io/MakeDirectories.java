package java_spc.io;

import java.io.File;
import java.io.IOException;

public class MakeDirectories{
    private static void usage(){
        System.err.println(
            "Usage:MakeDirectories path1 ...\n"+
            "Creates each path\n"+
            "Usage:MakeDirectories -d path1 ...\n"+
            "Deletes each path\n"+
            "Usage:MakeDirectories -r path1 path2\n"+
            "Renames from path1 to path2"
        );
        System.exit(1);
    }

    private static void fileData(File f) throws IOException{
        System.out.println(
            "Path: " + f.getPath()+
            "\n Absolute Path: " + f.getAbsolutePath()+
            "\n Canonical Path: " + f.getCanonicalPath()+
            "\n Can Read: " + f.canRead()+
            "\n Can Write: " + f.canWrite()+
            "\n Can Execute: " + f.canExecute()+
            "\n getName: " + f.getName()+
            "\n getParent: " + f.getParent()+
            "\n length: " + f.length()+
            "\n lastModified: " + f.lastModified()+
            "\n isDirectory: " + f.isDirectory()+
            "\n isFile: " + f.isFile()+
            "\n isHidden: " + f.isHidden()+
            "\n isAbsolute: " + f.isAbsolute()+
            "\n isExist: " + f.exists()+
            "\n total space: " + f.getTotalSpace()+
            "\n usable space: " + f.getUsableSpace()+
            "\n free space: " + f.getFreeSpace()
        );
    }

    public static void main(String[] args) throws IOException{
        if(args.length<1){
            usage();
        }
        if(args[0].equals("-r")){
            if(args.length!=3){
                usage();
            } 
            File old=new File(args[1]);
            File rname=new File(args[2]);
            old.renameTo(rname);
            fileData(old);
            fileData(rname);
            return;
        }
        int count=0;
        boolean del=false;
        if(args[0].equals("-d")){
            count++;
            del=true;
        }
        count--;
        while(++count<args.length){
            File f=new File(args[count]);
            if(f.exists()){
                System.out.println(f+" exists");
                if(del){
                    System.out.println("deleting..."+f);
                    f.delete();
                }
            }
            else{
                if(!del){
                    f.mkdirs();
                    System.out.println("created "+f);
                }
            }
            fileData(f);
        }
    }
}