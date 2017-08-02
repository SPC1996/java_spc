package java_spc.io;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import java_spc.util.Resource;

public class Blips {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        System.out.println("Construct objects");
        Blip1 blip1 = new Blip1();
        Blip2 blip2 = new Blip2();
        Blip3 blip3 = new Blip3(2333, "^_^");
        System.out.println(blip3);

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Resource.pathToSource("file/blip.out")));
        System.out.println("Save objects");
        out.writeObject(blip1);
        out.writeObject(blip2);
        out.writeObject(blip3);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(Resource.pathToSource("file/blip.out")));
        System.out.println("Get objects");
        blip1 = (Blip1) in.readObject();
        blip2 = (Blip2) in.readObject();
        blip3 = (Blip3) in.readObject();
        in.close();
        System.out.println(blip3);
    }
}

class Blip1 implements Externalizable {
    public Blip1() {
        System.out.println("Blip1 Constructor");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip1.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip1.readExternal");
    }

}

class Blip2 implements Externalizable {
    public Blip2() {
        System.out.println("Blip2 Constructor");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip2.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip2.readExternal");
    }

}

class Blip3 implements Externalizable {
    private int i;
    private String s;

    public Blip3() {
        System.out.println("Blip3 Constructor");
    }

    public Blip3(int i, String s) {
        this.i = i;
        this.s = s;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip3.writeExternal");
        out.writeInt(i);
//		out.writeObject(s);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip3.readExternal");
        this.i = in.readInt();
//		this.s=(String) in.readObject();
    }

    public String toString() {
        return "[i=" + i + ",s=" + s + "]";
    }
}