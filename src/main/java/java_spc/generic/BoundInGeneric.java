package java_spc.generic;

import java.awt.Color;

/**
 * 泛型的边界
 * 在泛型的参数类型上设置限制条件
 */
public class BoundInGeneric {
    public static void main(String[] args) {
        BasicBounds.play();
        InheritBounds.play();
    }
}

class BasicBounds {
    interface HasColor {
        Color getColor();
    }

    interface Weight {
        int weight();
    }

    static class Colored<T extends HasColor> {
        protected T item;

        Colored(T item) {
            this.item = item;
        }

        T getItem() {
            return item;
        }

        Color getColor() {
            return item.getColor();
        }
    }

    static class Dimension {
        protected int x, y, z;
    }

    static class ColoredDimension<T extends Dimension & HasColor> {
        protected T item;

        ColoredDimension(T item) {
            this.item = item;
        }

        T getItem() {
            return item;
        }

        Color color() {
            return item.getColor();
        }

        int getX() {
            return item.x;
        }

        int getY() {
            return item.y;
        }

        int getZ() {
            return item.z;
        }
    }

    static class Solid<T extends Dimension & HasColor & Weight> {
        protected T item;

        Solid(T item) {
            this.item = item;
        }

        T getItem() {
            return item;
        }

        Color color() {
            return item.getColor();
        }

        int getX() {
            return item.x;
        }

        int getY() {
            return item.y;
        }

        int getZ() {
            return item.z;
        }

        int weight() {
            return item.weight();
        }
    }

    static class Bounded extends Dimension implements HasColor, Weight {
        @Override
        public Color getColor() {
            return null;
        }

        @Override
        public int weight() {
            return 0;
        }
    }

    public static void play() {
        Solid<Bounded> solid = new Solid<>(new Bounded());
        System.out.println("color : " + solid.color() + "\n"
                + "x : " + solid.getX() + "\n"
                + "y : " + solid.getY() + "\n"
                + "z : " + solid.getZ() + "\n"
                + "weight : " + solid.weight() + "\n"
        );
    }
}

class InheritBounds {
    static class HoldItem<T> {
        protected T item;

        HoldItem(T item) {
            this.item = item;
        }

        T getItem() {
            return item;
        }
    }

    static class Colored<T extends BasicBounds.HasColor> extends HoldItem<T> {

        Colored(T item) {
            super(item);
        }

        Color color() {
            return item.getColor();
        }
    }

    static class ColoredDimension<T extends BasicBounds.Dimension & BasicBounds.HasColor> extends Colored<T> {

        ColoredDimension(T item) {
            super(item);
        }

        int getX() {
            return item.x;
        }

        int getY() {
            return item.y;
        }

        int getZ() {
            return item.z;
        }
    }

    static class Solid<T extends BasicBounds.Dimension & BasicBounds.HasColor & BasicBounds.Weight>
            extends ColoredDimension<T> {

        Solid(T item) {
            super(item);
        }

        int weight() {
            return item.weight();
        }
    }

    public static void play() {
        Solid<BasicBounds.Bounded> solid = new Solid<>(new BasicBounds.Bounded());
        System.out.println("color : " + solid.color() + "\n"
                + "x : " + solid.getX() + "\n"
                + "y : " + solid.getY() + "\n"
                + "z : " + solid.getZ() + "\n"
                + "weight : " + solid.weight() + "\n"
        );
    }
}