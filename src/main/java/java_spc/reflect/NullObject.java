package java_spc.reflect;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NullObject {
    public static void main(String[] args) {
        Staff.play();
        SnowRemoveRobot.play();
        NullRobot.play();
    }
}

interface Null {
}

class Person {
    public final String first;
    public final String last;
    public final String address;

    public static final Person NULL = new NullPerson();

    public static class NullPerson extends Person implements Null {
        private NullPerson() {
            super("None", "None", "None");
        }

        @Override
        public String toString() {
            return "NullPerson";
        }
    }

    public Person(String first, String last, String address) {
        this.first = first;
        this.last = last;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person: " + first + " " + last + " " + address;
    }
}

class Position {
    private String title;
    private Person person;

    public Position(String title, Person person) {
        this.title = title;
        this.person = person;
        if (this.person == null) {
            person = Person.NULL;
        }
    }

    public Position(String title) {
        this.title = title;
        this.person = Person.NULL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        if (this.person == null) {
            this.person = Person.NULL;
        }
    }

    @Override
    public String toString() {
        return "Position: " + title + " " + person;
    }
}

class Staff extends ArrayList<Position> {
    public Staff(String... title) {
        add(title);
    }

    public void add(String title, Person person) {
        add(new Position(title, person));
    }

    public void add(String... titles) {
        for (String title : titles) {
            add(new Position(title));
        }
    }

    public boolean positionAvailable(String title) {
        for (Position position : this) {
            if (position.getTitle().equals(title) && position.getPerson() == Person.NULL) {
                return true;
            }
        }
        return false;
    }

    public void fillPosition(String title, Person hire) {
        for (Position position : this) {
            if (position.getTitle().equals(title) && position.getPerson() == Person.NULL) {
                position.setPerson(hire);
                return;
            }
        }
        throw new RuntimeException("Position " + title + " not available");
    }

    public static void play() {
        Staff staff = new Staff("President", "CTO", "CEO", "Project Manager", "Product Manager");
        staff.fillPosition("President", new Person("Nick", "Brown", "American"));
        staff.fillPosition("CTO", new Person("Zed", "Buck", "American"));
        staff.fillPosition("CEO", new Person("Jase", "Dan", "American"));
        System.out.println(staff);
    }
}

interface Operation {
    String description();

    void command();
}

interface Robot {
    String name();

    String model();

    List<Operation> operations();

    class Test {
        public static void test(Robot robot) {
            if (robot instanceof Null) {
                System.out.println("[NULL ROBOT]");
            }
            System.out.println("Robot name: " + robot.name());
            System.out.println("Robot model: " + robot.model());
            for (Operation operation : robot.operations()) {
                System.out.println(operation.description());
                operation.command();
            }
        }
    }
}

class SnowRemoveRobot implements Robot {
    private String name;

    public SnowRemoveRobot(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String model() {
        return "SNOW ROBOT 001";
    }

    @Override
    public List<Operation> operations() {
        return Arrays.asList(
                new Operation() {
                    @Override
                    public String description() {
                        return name + " can clean snow";
                    }

                    @Override
                    public void command() {
                        System.out.println(name + " clean snow");
                    }
                },
                new Operation() {
                    @Override
                    public String description() {
                        return name + " can clean ice";
                    }

                    @Override
                    public void command() {
                        System.out.println(name + " clean ice");
                    }
                }
        );
    }

    public static void play() {
        Robot.Test.test(new SnowRemoveRobot("jue"));
    }
}

class NullRobotProxyHandler implements InvocationHandler {
    private String nullName;
    private Robot source = new NRobot();

    private class NRobot implements Null, Robot {
        @Override
        public String name() {
            return nullName;
        }

        @Override
        public String model() {
            return nullName;
        }

        @Override
        public List<Operation> operations() {
            return Collections.emptyList();
        }
    }

    NullRobotProxyHandler(Class<? extends Robot> type) {
        nullName = type.getSimpleName() + " NULL ROBOT";
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(source, args);
    }
}

class NullRobot {
    public static Robot newNullRobot(Class<? extends Robot> type) {
        return (Robot) Proxy.newProxyInstance(
                Robot.class.getClassLoader(),
                new Class[]{Null.class, Robot.class},
                new NullRobotProxyHandler(type)
        );
    }

    public static void play() {
        Robot[] robots = {new SnowRemoveRobot("ket"), newNullRobot(SnowRemoveRobot.class)};

        for (Robot robot : robots) {
            Robot.Test.test(robot);
        }
    }
}