package java_spc.enum_type;

import java.text.DateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * 使用EnumSet和EnumMap
 * EnumSet基于long实现，理论上最多应用不超过64个元素的enum，实际中可以超过64个会扩容
 * EnumMap是一个特殊的Map，其中的key必须是enum,内部使用数组实现
 *
 * @author SPC
 */
public class UseEnumSetAndEnumMap {
    private interface Command {
        void action(String name);
    }

    public static void useEnumSet() {
        EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class);
        points.add(AlarmPoints.ONE);
        System.out.println(points);
        points.addAll(EnumSet.of(AlarmPoints.THREE, AlarmPoints.FOUR, AlarmPoints.FIVE));
        System.out.println(points);
        points = EnumSet.allOf(AlarmPoints.class);
        System.out.println(points);
        points.removeAll(EnumSet.of(AlarmPoints.TWO, AlarmPoints.SIX, AlarmPoints.SEVEN));
        System.out.println(points);
        points.removeAll(EnumSet.range(AlarmPoints.EIGHT, AlarmPoints.TWELVE));
        System.out.println(points);
        points = EnumSet.complementOf(points);
        System.out.println(points);
    }

    public static void useEnumMap() {
        EnumMap<AlarmPoints, Command> em = new EnumMap<>(AlarmPoints.class);
        for (int i = 8; i < 11; i++) {
            em.put(AlarmPoints.values()[i], name -> System.out.println("To do task " + name));
        }

        for (Map.Entry<AlarmPoints, Command> e : em.entrySet()) {
            System.out.println("key : " + e.getKey());
            e.getValue().action(e.getKey().name().toLowerCase());
        }
    }

    public static void useConstantMethod() {
        for (ConstantSpecificMethod csm : ConstantSpecificMethod.values()) {
            System.out.println(csm.name() + " : " + csm.getInfo());
        }
        for (BasicMethod bm : BasicMethod.values()) {
            System.out.print(bm.name() + " : ");
            bm.f();
        }
    }

    public static void toWashCar() {
        CarWash wash = new CarWash();
        System.out.println("init : " + wash);
        wash.washCar();
        wash.add(CarWash.Cycle.BLOWDRY);
        wash.add(CarWash.Cycle.BLOWDRY);
        wash.add(CarWash.Cycle.RINSE);
        wash.add(CarWash.Cycle.HOTWAX);
        System.out.println("add some cycle : " + wash);
        wash.washCar();
    }

    public static void main(String[] args) {
        useEnumSet();
        System.out.println("----------------------------------------------------");
        useEnumMap();
        System.out.println("----------------------------------------------------");
        useConstantMethod();
        System.out.println("----------------------------------------------------");
        toWashCar();
    }
}

enum AlarmPoints {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE
}

/**
 * 覆盖抽象方法
 */
enum ConstantSpecificMethod {
    DATE_TIME {
        String getInfo() {
            return DateFormat.getDateInstance().format(new Date());
        }
    },
    CLASSPATH {
        String getInfo() {
            return System.getenv("CLASSPATH");
        }
    },
    VERSION {
        String getInfo() {
            return System.getProperty("java.version");
        }
    };

    abstract String getInfo();
}

/**
 * 覆盖抽象方法
 */
class CarWash {
    enum Cycle {
        UNDERBODY {
            void action() {
                System.out.println("Spraying the underbody");
            }
        },
        WHEELWASH {
            void action() {
                System.out.println("Washing the wheels");
            }
        },
        PREWASH {
            void action() {
                System.out.println("Loosening the dirt");
            }
        },
        BASIC {
            void action() {
                System.out.println("The basic wash");
            }
        },
        HOTWAX {
            void action() {
                System.out.println("Applying hot wax");
            }
        },
        RINSE {
            void action() {
                System.out.println("Rinsing");
            }
        },
        BLOWDRY {
            void action() {
                System.out.println("Blowing dry");
            }
        };

        abstract void action();
    }

    EnumSet<Cycle> cycles = EnumSet.of(Cycle.BASIC, Cycle.RINSE);

    public void add(Cycle cycle) {
        cycles.add(cycle);
    }

    public void washCar() {
        for (Cycle c : cycles) {
            c.action();
        }
    }

    @Override
    public String toString() {
        return cycles.toString();
    }
}

/**
 * 覆盖普通方法
 */
enum BasicMethod {
    NUT, BOLT,
    WASHER {
        void f() {
            System.out.println("Override Method");
        }
    };

    void f() {
        System.out.println("Default Method");
    }
}