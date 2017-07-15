package java_spc.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java_spc.util.BinaryFile;
import java_spc.util.Directory;
import java_spc.util.TextFile;
import java_spc.util.Directory.TreeInfo;
import java_spc.util.Resource;

public class CountInFile{
    public static Map<Character,Integer> countChar(String fileName){
        String text=TextFile.read(fileName);
        Map<Character,Integer> map=new HashMap<>();
        for(Character c:text.toCharArray()){
            if(Character.isDefined(c)){
                if(map.containsKey(c)){
                    map.put(c, map.get(c)+1);
                } else {
                    map.put(c, 1);
                }
            }
        }
        return map;
    }

    public static Map<Byte,Integer> countByte(String fileName) throws IOException{
        byte[] bytes=BinaryFile.read(fileName);
        Map<Byte,Integer> map=new HashMap<>();
        for(byte b:bytes){
            if(map.containsKey(b)){
                map.put(b, map.get(b)+1);
            } else {
                map.put(b, 1);
            }
        }
        return map;
    }

    public static ArrayList<String> checkClass(String fileName) throws IOException{
        ArrayList<String> heads=new ArrayList<>();
        TreeInfo tree=Directory.walk(new File(fileName), ".*\\.class");
        byte[] bytes;
        for(File file:tree.files){
            bytes=BinaryFile.read(file);
            String s="";
            for(int i=0;i<4;i++){
                int v=bytes[i] & 0xFF;
                s+=Integer.toHexString(v).toUpperCase();
            }
            heads.add(s);
        }
        return heads;
    }

    public static void main(String[] args) throws IOException{
        Map<Character,Integer> map=countChar(Resource.path("file/sing.txt"));
        System.out.println(map);
        Map<Byte,Integer> map2=countByte(Resource.path("file/note.txt"));
        System.out.println(map2);
        ArrayList<String> heads=checkClass(".");
        System.out.println(heads);
    }
}