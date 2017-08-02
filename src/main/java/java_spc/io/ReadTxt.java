package java_spc.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;

public class ReadTxt {
    public static void usage() {
        System.err.println("usage: ReadTxt path key");
    }

    public static ArrayList<String> read(String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        String s;
        while ((s = reader.readLine()) != null) {
            list.add(s.toUpperCase());
        }
        reader.close();
        return list;
    }

    public static void main(String[] args) {
        String fileName;
        if (args.length != 2) {
            usage();
            return;
        } else {
            fileName = args[0];
        }
        ArrayList<String> list = null;
        try {
            list = read(fileName);
        } catch (IOException e) {
            System.out.println("文件不存在！");
            return;
        }
        Collections.reverse(list);
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.matches(".*\\b" + args[1].toUpperCase() + "\\b.*")) {
                System.out.println(s);
            }
        }
    }
}