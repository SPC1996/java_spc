package java_spc.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java_spc.util.Resource;

public class Logon implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static String FILE = Resource.pathToSource("file/logon.out");
    private Date date = new Date();
    private String username;
    private transient String password;

    public Logon(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return "logon info: \n    username: " + username +
                "\n    date: " + date + "\n    password: " + password;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, InterruptedException {
        Logon a = new Logon("spc", "123asdqaz");
        SerialCtl ctl = new SerialCtl("test1", "test2");
        House house = new House();
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("dog", house));
        animals.add(new Animal("cat", house));
        animals.add(new Animal("bird", house));
        System.out.println("logon a=" + a + "\n" + "SerialCtl ctl=" + ctl);
        System.out.println("animals: " + animals);

        ByteArrayOutputStream buff = new ByteArrayOutputStream();

        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(FILE));
        ObjectOutputStream outOhter = new ObjectOutputStream(buff);
        out.writeObject(a);
        out.writeObject(ctl);
        out.writeObject(animals);
        out.writeObject(animals);
        outOhter.writeObject(animals);
        out.close();
        outOhter.close();

        Thread.sleep(5000);

        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(FILE));
        ObjectInputStream inOther = new ObjectInputStream(new ByteArrayInputStream(buff.toByteArray()));
        System.out.println("Recovering object at " + new Date());
        a = (Logon) in.readObject();
        ctl = (SerialCtl) in.readObject();
        List<Animal> animals1 = (List<Animal>) in.readObject(),
                animals2 = (List<Animal>) in.readObject(),
                animals3 = (List<Animal>) inOther.readObject();
        in.close();
        inOther.close();
        System.out.println("logon a=" + a + "\n" + "SerialCtl ctl=" + ctl);
        System.out.println("animals1: " + animals1 + "\nanimals2: " + animals2 + "\nanimals3: " + animals3);

    }

}

class SerialCtl implements Serializable {
    private static final long serialVersionUID = 1L;
    private String a;
    private transient String b;

    public SerialCtl(String a, String b) {
        this.a = "Not Transient: " + a;
        this.b = "Transient: " + b;
    }

    public String toString() {
        return "ctl info:\n    " + a + "\n    " + b;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(b);
    }

    private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        stream.defaultReadObject();
        b = (String) stream.readObject();
    }
}

class Animal implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private House house;

    public Animal(String name, House house) {
        this.name = name;
        this.house = house;
    }

    public String toString() {
        return name + "[" + super.toString() + "]" + house;
    }
}

class House implements Serializable {
    private static final long serialVersionUID = 1L;

}