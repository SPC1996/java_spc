package java_spc.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import java_spc.util.BinaryFile;
import java_spc.util.Directory;
import java_spc.util.TextFile;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;
import java_spc.util.Directory.TreeInfo;
import java_spc.util.Resource;

public class CountInFile {
    public static Map<Character, Integer> countChar(String fileName) {
        String text = TextFile.read(fileName);
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : text.toCharArray()) {
            if (Character.isDefined(c)) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
        }
        return map;
    }

    public static Map<Byte, Integer> countByte(String fileName) throws IOException {
        byte[] bytes = BinaryFile.read(fileName);
        Map<Byte, Integer> map = new HashMap<>();
        for (byte b : bytes) {
            if (map.containsKey(b)) {
                map.put(b, map.get(b) + 1);
            } else {
                map.put(b, 1);
            }
        }
        return map;
    }

    public static ArrayList<String> checkClass(String fileName) throws IOException {
        ArrayList<String> heads = new ArrayList<>();
        TreeInfo tree = Directory.walk(new File(fileName), ".*\\.class");
        byte[] bytes;
        for (File file : tree.files) {
            bytes = BinaryFile.read(file);
            String s = "";
            for (int i = 0; i < 4; i++) {
                int v = bytes[i] & 0xFF;
                s += Integer.toHexString(v).toUpperCase();
            }
            heads.add(s);
        }
        return heads;
    }

    public static void countWordAndStoreInXML(String fileRName, String fileWName) throws Exception {
        Map<String, Long> map = new TextFile(fileRName, "\\W+")
                .stream()
                .collect(Collectors.groupingBy(s -> s, TreeMap::new, Collectors.counting()));
        Element root = new Element("words");
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            Element word = new Element("word");
            Element string = new Element("string");
            Element count = new Element("count");
            string.appendChild(entry.getKey());
            count.appendChild(entry.getValue().toString());
            word.appendChild(string);
            word.appendChild(count);
            root.appendChild(word);
        }
        Document doc = new Document(root);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileWName));
        Serializer serializer = new Serializer(out, "utf-8");
        serializer.setIndent(4);
        serializer.setMaxLength(60);
        serializer.write(doc);
        serializer.flush();
    }

    public static void main(String[] args) throws Exception {
//        Map<Character,Integer> map=countChar(Resource.path("file/sing.txt"));
//        System.out.println(map);
//        Map<Byte,Integer> map2=countByte(Resource.path("file/note.txt"));
//        System.out.println(map2);
//        ArrayList<String> heads=checkClass(".");
//        System.out.println(heads);
        countWordAndStoreInXML(Resource.pathToSource("file/note.txt"), Resource.pathToSource("file/word.xml"));
    }
}