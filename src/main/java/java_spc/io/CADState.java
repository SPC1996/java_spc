package java_spc.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java_spc.util.Resource;

public class CADState {
    private static final String FILE = Resource.pathToSource("file/cad.out");

    public static void store() throws FileNotFoundException, IOException {
        List<Class<? extends Shape>> shapeTypes = new ArrayList<>();
        shapeTypes.add(Circle.class);
        shapeTypes.add(Square.class);
        shapeTypes.add(Line.class);
        List<Shape> shapes = Stream.generate(Shape::randomFactory).limit(10).collect(Collectors.toList());
        shapes.stream().forEach(s -> s.setColor(Shape.GREEN));

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE));
        out.writeObject(shapeTypes);
        Line.serializeStaticState(out);
        out.writeObject(shapes);
        out.close();

        System.out.println(shapeTypes + "\n" + shapes);
    }

    @SuppressWarnings("unchecked")
    public static void recover() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE));
        List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>) in.readObject();
        Line.deserializeStaticState(in);
        List<Shape> shapes = (List<Shape>) in.readObject();
        in.close();

        System.out.println(shapeTypes + "\n" + shapes);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        store();
        recover();
    }
}

abstract class Shape implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int RED = 1, BLUE = 2, GREEN = 3;
    private int xPos, yPos, dimension;
    private static Random random = new Random(2333);
    private static int counter = 0;

    public abstract void setColor(int newColor);

    public abstract int getColor();

    public Shape(int xPos, int yPos, int dimension) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.dimension = dimension;
    }

    public String toString() {
        return "\n" + this.getClass() + " " +
                "color[" + getColor() + "] " +
                "xPos[" + xPos + "] " +
                "yPos[" + yPos + "] " +
                "dimension[" + dimension + "]";
    }

    public static Shape randomFactory() {
        int xPos = random.nextInt(100);
        int yPos = random.nextInt(100);
        int dimension = random.nextInt(100);
        switch (counter++ % 3) {
            default:
            case 0:
                return new Circle(xPos, yPos, dimension);
            case 1:
                return new Square(xPos, yPos, dimension);
            case 2:
                return new Line(xPos, yPos, dimension);
        }
    }
}

class Circle extends Shape {
    private static final long serialVersionUID = 1L;
    private static int color = RED;

    public Circle(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);
    }

    @Override
    public void setColor(int newColor) {
        color = newColor;
    }

    @Override
    public int getColor() {
        return color;
    }

}

class Square extends Shape {
    private static final long serialVersionUID = 1L;
    private static int color;

    public Square(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);
        color = RED;

    }

    @Override
    public void setColor(int newColor) {
        color = newColor;
    }

    @Override
    public int getColor() {
        return color;
    }
}

class Line extends Shape {
    private static final long serialVersionUID = 1L;
    private static int color = RED;

    public Line(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);

    }

    @Override
    public void setColor(int newColor) {
        color = newColor;
    }

    @Override
    public int getColor() {
        return color;
    }

    public static void serializeStaticState(ObjectOutputStream out) throws IOException {
        out.writeInt(color);
    }

    public static void deserializeStaticState(ObjectInputStream in) throws IOException {
        color = in.readInt();
    }
}