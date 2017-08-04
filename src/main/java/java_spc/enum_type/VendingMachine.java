package java_spc.enum_type;

import java_spc.util.TextFile;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Random;

/**
 * 使用枚举类型创建状态机
 *
 * @author SPC
 */
public class VendingMachine {
    private static State state = State.RESTING;
    private static int amount = 0;
    private static Input selection = null;

    enum StateDuration {TRANSIENT}

    enum State {
        RESTING {
            void next(Input input) {
                switch (Category.categorize(input)) {
                    case MONEY:
                        amount += input.amount();
                        state = State.ADDING_MONEY;
                        break;
                    case SHUT_DOWN:
                        state = State.TERMINAL;
                    default:
                }
            }
        },
        ADDING_MONEY {
            void next(Input input) {
                switch (Category.categorize(input)) {
                    case MONEY:
                        amount += input.amount();
                        break;
                    case ITEM_SELECTION:
                        selection = input;
                        if (amount < selection.amount()) {
                            System.out.println("Insufficient money for " + selection);
                        } else {
                            state = State.DISPENSING;
                        }
                        break;
                    case QUIT_TRANSACTION:
                        state = State.GIVING_CHANGE;
                        break;
                    case SHUT_DOWN:
                        state = State.TERMINAL;
                    default:
                }
            }
        },
        DISPENSING(StateDuration.TRANSIENT) {
            void next() {
                System.out.println("Here is your " + selection);
                amount -= selection.amount();
                state = State.GIVING_CHANGE;
            }
        },
        GIVING_CHANGE(StateDuration.TRANSIENT) {
            void next() {
                if (amount > 0) {
                    System.out.println("Your change : " + amount);
                    amount = 0;
                }
                state = State.RESTING;
            }
        },
        TERMINAL {
            void output() {
                System.out.println("Halted");
            }
        };

        private boolean isTransient = false;

        State() {
        }

        State(StateDuration trans) {
            isTransient = true;
        }

        void next(Input input) {
            throw new RuntimeException("Only call next(Input input) for non-transient states");
        }

        void next() {
            throw new RuntimeException("Only call next() for StateDuration.TRANSIENT states");
        }

        void output() {
            System.out.println(amount);
        }
    }

    interface Generator<T> {
        T next();
    }

    static class RandomInputGenerator implements Generator<Input> {

        @Override
        public Input next() {
            return Input.randomSelect();
        }
    }

    static class FileInputGenerator implements Generator<Input> {
        private Iterator<String> input;

        public FileInputGenerator(String fileName) {
            input = new TextFile(fileName, ";").iterator();
        }

        @Override
        public Input next() {
            if (!input.hasNext())
                return null;
            return Enum.valueOf(Input.class, input.next().trim());
        }
    }

    static class StringInputGenerator implements Generator<Input> {
        private Iterator<String> input;

        public StringInputGenerator(String string) {
            input = Arrays.asList(string.split(" ")).iterator();
        }

        @Override
        public Input next() {
            if (!input.hasNext())
                return null;
            return Enum.valueOf(Input.class, input.next().trim());
        }
    }

    static void run(Generator<Input> gen) {
        while (state != State.TERMINAL) {
            state.next(gen.next());
            while (state.isTransient) {
                state.next();
            }
            state.output();
        }
    }

    public static void main(String[] args) {
        String action = "QUARTER QUARTER QUARTER CHIPS " +
                "DOLLAR DOLLAR TOOTHPASTE " +
                "QUARTER DIME SODA " +
                "ABORT_TRANSACTION STOP";
        Generator<Input> gen = new StringInputGenerator(action);
        if (args.length == 1) {
            gen = new FileInputGenerator(args[0]);
        }
        run(gen);
    }
}

enum Input {
    NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100), TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
    ABORT_TRANSACTION {
        public int amount() {
            throw new RuntimeException("ABORT.amount()");
        }
    },
    STOP {
        public int amount() {
            throw new RuntimeException("STOP.amount()");
        }
    };

    private static Random rand = new Random(2333);
    private int value;

    Input() {
    }

    Input(int value) {
        this.value = value;
    }

    int amount() {
        return value;
    }

    public static Input randomSelect() {
        return values()[rand.nextInt(values().length - 1)];
    }
}

enum Category {
    MONEY(Input.NICKEL, Input.DIME, Input.QUARTER, Input.DOLLAR),
    ITEM_SELECTION(Input.TOOTHPASTE, Input.CHIPS, Input.SODA, Input.SOAP),
    QUIT_TRANSACTION(Input.ABORT_TRANSACTION),
    SHUT_DOWN(Input.STOP);

    private Input[] values;
    private static EnumMap<Input, Category> categories = new EnumMap<>(Input.class);

    static {
        for (Category c : Category.class.getEnumConstants()) {
            for (Input type : c.values) {
                categories.put(type, c);
            }
        }
    }

    Category(Input... values) {
        this.values = values;
    }

    public static Category categorize(Input input) {
        return categories.get(input);
    }
}