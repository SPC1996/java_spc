package java_spc.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import java_spc.util.Resource;

public class Worm implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Random random = new Random(47);
    private Data[] datas = {new Data(random.nextInt(10)), new Data(random.nextInt(10)), new Data(random.nextInt(10))};
    private Worm next;
    private char c;

    public Worm(int i, char c) {
        System.out.println("Worm constructor: " + i);
        this.c = c;
        if (--i > 0) {
            next = new Worm(i, (char) (c + 1));
        }
    }

    public Worm() {
        System.out.println("Default constructor");
    }

    public String toString() {
        StringBuilder res = new StringBuilder(":");
        res.append(c);
        res.append("(");
        for (Data data : datas) {
            res.append(data);
        }
        res.append(")");
        if (next != null) {
            res.append(next);
        }
        return res.toString();
    }

    public static void init(String fileName) throws FileNotFoundException, IOException {
        out = new ObjectOutputStream(new FileOutputStream(fileName));
        in = new ObjectInputStream(new FileInputStream(fileName));

    }

    public static void writeObject(Object object) throws IOException {
        out.writeObject(object);
    }

    public static ArrayList<Object> readObject(String fileName) throws IOException, ClassNotFoundException {
        ArrayList<Object> objects = new ArrayList<>();
        try {
            while (true) {
                objects.add(in.readObject());
            }
        } catch (EOFException e) {
            System.out.println("Read all object!");
        }
        return objects;
    }

    public static void close() throws IOException {
        out.close();
        in.close();
    }


    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Worm worm = new Worm(6, 'a');
        System.out.println("worm = " + worm);
        String fileName = Resource.pathToSource("file/worm.out");
        init(fileName);
        writeObject("Worm storage");
        writeObject(worm);
        for (Object object : readObject(fileName)) {
            System.out.println(object);
        }
        close();

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject("Worm storage\n");
        out.writeObject(worm);
        out.flush();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
        String s = (String) in.readObject();
        Worm w = (Worm) in.readObject();
        System.out.println(s + w);
    }
}

class Data implements Serializable {
    private static final long serialVersionUID = 1L;
    private int n;

    public Data(int n) {
        this.n = n;
    }

    public String toString() {
        return Integer.toString(n);
    }
}

