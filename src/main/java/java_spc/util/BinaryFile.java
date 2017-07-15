package java_spc.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryFile{
    public static byte[] read(File file) throws IOException{
        BufferedInputStream in=new BufferedInputStream(new FileInputStream(file));
        try {
            byte[] data=new byte[in.available()];
            in.read(data);
            return data;
        } finally {
            in.close();
        }
    }

    public static byte[] read(String fileName) throws IOException{
        return read(new File(fileName).getAbsoluteFile());
    }
}