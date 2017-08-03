package java_spc.enum_type;

/**
 * 在switch使用enum
 * 通过ordinal()取得次序
 * JDK7以后switch已经可以使用字符串
 *
 * @author SPC
 */
public class EnumInSwitch {
    private Signal color = Signal.RED;

    public void change() {
        switch (color) {
            case RED:
                color = Signal.GREEN;
                break;
            case GREEN:
                color = Signal.BLUE;
                break;
            case BLUE:
                color = Signal.RED;
                break;
        }
    }

    @Override
    public String toString() {
        return "The color is " + color;
    }

    public static void main(String[] args) {
        EnumInSwitch s = new EnumInSwitch();
        for (int i = 0; i < 5; i++) {
            System.out.println(s);
            s.change();
        }

    }
}

enum Signal {
    GREEN,
    RED,
    BLUE
}