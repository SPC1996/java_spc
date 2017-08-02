package java_spc.io;

import java.io.*;

import java_spc.util.Resource;

public class InputFile {
    public static String bufferedRead(String filename) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
        BufferedReader in = new BufferedReader(isr);
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine()) != null) {
            sb.append(s + "\n");
        }
        in.close();
        return sb.toString();
    }

    public static void memoryRead(String fileName) throws IOException {
        StringReader in = new StringReader(bufferedRead(fileName));
        int c;
        while ((c = in.read()) != -1) {
            System.out.print((char) c);
        }
    }

    public static void formatMemoryRead(String fileName) throws IOException {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(bufferedRead(fileName).getBytes()));
            while (true) {
                System.out.print((char) in.readByte());
            }
        } catch (EOFException e) {
            System.err.println("End of Stream");
        }

    }

    public static void testEOF(String fileName) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bufferedRead(fileName).getBytes()));
        while (in.available() != 0) {
            System.out.print((char) in.readByte());
        }


    }

    public static void main(String[] args) throws IOException {
        memoryRead(Resource.path("file/sing.txt"));
        formatMemoryRead(Resource.path("file/sing.txt"));
        testEOF(Resource.path("file/sing.txt"));
    }
}