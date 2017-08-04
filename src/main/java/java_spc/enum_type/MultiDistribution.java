package java_spc.enum_type;

import java_spc.util.Enums;

import java.util.EnumMap;
import java.util.Random;

/**
 * 使用多路分发完成“石头，剪刀，布”游戏
 *
 * @author SPC
 */
public class MultiDistribution {
    private static final int SIZE = 5;

    public static <T extends Competitor<T>> void match(T a, T b) {
        System.out.println(a + " vs " + b + " : " + a.compete(b));
    }

    public static <T extends Enum<T> & Competitor<T>> void play(Class<T> tClass, int size) {
        for (int i = 0; i < size; i++) {
            match(Enums.random(tClass), Enums.random(tClass));
        }
    }

    public static void main(String[] args) {
        System.out.println("CaseOne : -------------------------------");
        for (int i = 0; i < SIZE; i++) {
            CaseOne.match(CaseOne.newItem(), CaseOne.newItem());
        }
        System.out.println("-----------------------------------------");
        System.out.println("CaseTwo : -------------------------------");
        play(CaseTwo.class, SIZE);
        System.out.println("-----------------------------------------");
        System.out.println("CaseThree : -----------------------------");
        play(CaseThree.class, SIZE);
        System.out.println("-----------------------------------------");
        System.out.println("CaseFour : -----------------------------");
        play(CaseFour.class, SIZE);
        System.out.println("-----------------------------------------");
        System.out.println("CaseFive : -----------------------------");
        play(CaseFive.class, SIZE);
        System.out.println("-----------------------------------------");
        System.out.println("CaseSix : -----------------------------");
        play(CaseSix.class, SIZE);
        System.out.println("-----------------------------------------");
    }
}

enum Outcome {WIN, LOSE, DRAW}

interface Competitor<T extends Competitor<T>> {

    Outcome compete(T competitor);
}

class CaseOne {
    private static Random rand = new Random(2333);

    interface Item {
        Outcome complete(Item item);

        Outcome eval(Paper paper);

        Outcome eval(Scissors scissors);

        Outcome eval(Rock rock);
    }

    static class Paper implements Item {

        @Override
        public Outcome complete(Item item) {
            return item.eval(this);
        }

        @Override
        public Outcome eval(Paper paper) {
            return Outcome.DRAW;
        }

        @Override
        public Outcome eval(Scissors scissors) {
            return Outcome.WIN;
        }

        @Override
        public Outcome eval(Rock rock) {
            return Outcome.LOSE;
        }

        @Override
        public String toString() {
            return "Paper";
        }
    }

    static class Scissors implements Item {

        @Override
        public Outcome complete(Item item) {
            return item.eval(this);
        }

        @Override
        public Outcome eval(Paper paper) {
            return Outcome.LOSE;
        }

        @Override
        public Outcome eval(Scissors scissors) {
            return Outcome.DRAW;
        }

        @Override
        public Outcome eval(Rock rock) {
            return Outcome.WIN;
        }

        @Override
        public String toString() {
            return "Scissors";
        }
    }

    static class Rock implements Item {

        @Override
        public Outcome complete(Item item) {
            return item.eval(this);
        }

        @Override
        public Outcome eval(Paper paper) {
            return Outcome.WIN;
        }

        @Override
        public Outcome eval(Scissors scissors) {
            return Outcome.LOSE;
        }

        @Override
        public Outcome eval(Rock rock) {
            return Outcome.DRAW;
        }

        @Override
        public String toString() {
            return "Rock";
        }
    }

    public static Item newItem() {
        switch (rand.nextInt(3)) {
            default:
            case 0:
                return new Scissors();
            case 1:
                return new Paper();
            case 2:
                return new Rock();
        }
    }

    public static void match(Item a, Item b) {
        System.out.println(a + " vs " + b + " : " + a.complete(b));
    }
}

enum CaseTwo implements Competitor<CaseTwo> {
    PAPER(Outcome.DRAW, Outcome.LOSE, Outcome.WIN),
    SCISSORS(Outcome.WIN, Outcome.DRAW, Outcome.LOSE),
    ROCK(Outcome.LOSE, Outcome.WIN, Outcome.DRAW);

    private Outcome vP, vS, vR;

    private CaseTwo(Outcome vP, Outcome vS, Outcome vR) {
        this.vP = vP;
        this.vS = vS;
        this.vR = vR;
    }

    @Override
    public Outcome compete(CaseTwo it) {
        switch (it) {
            default:
            case PAPER:
                return vR;
            case SCISSORS:
                return vS;
            case ROCK:
                return vP;
        }
    }
}

enum CaseThree implements Competitor<CaseThree> {
    PAPER {
        @Override
        public Outcome compete(CaseThree it) {
            switch (it) {
                default:
                case PAPER:
                    return Outcome.DRAW;
                case SCISSORS:
                    return Outcome.LOSE;
                case ROCK:
                    return Outcome.WIN;
            }
        }
    },
    SCISSORS {
        @Override
        public Outcome compete(CaseThree it) {
            switch (it) {
                default:
                case PAPER:
                    return Outcome.WIN;
                case SCISSORS:
                    return Outcome.DRAW;
                case ROCK:
                    return Outcome.LOSE;
            }
        }
    },
    ROCK {
        @Override
        public Outcome compete(CaseThree it) {
            switch (it) {
                default:
                case PAPER:
                    return Outcome.LOSE;
                case SCISSORS:
                    return Outcome.WIN;
                case ROCK:
                    return Outcome.DRAW;
            }
        }
    };

    @Override
    public abstract Outcome compete(CaseThree it);
}

enum CaseFour implements Competitor<CaseFour> {
    PAPER {
        @Override
        public Outcome compete(CaseFour it) {
            return compete(ROCK, it);
        }
    },
    SCISSORS {
        @Override
        public Outcome compete(CaseFour it) {
            return compete(PAPER, it);
        }
    },
    ROCK {
        @Override
        public Outcome compete(CaseFour it) {
            return compete(SCISSORS, it);
        }
    };

    public Outcome compete(CaseFour a, CaseFour b) {
        return b == this ? Outcome.DRAW : b == a ? Outcome.WIN : Outcome.LOSE;

    }
}

enum CaseFive implements Competitor<CaseFive> {
    PAPER, SCISSORS, ROCK;

    private static EnumMap<CaseFive, EnumMap<CaseFive, Outcome>> table = new EnumMap<>(CaseFive.class);

    static {
        for (CaseFive it : CaseFive.values()) {
            table.put(it, new EnumMap<>(CaseFive.class));
        }
        initRow(PAPER, Outcome.DRAW, Outcome.LOSE, Outcome.WIN);
        initRow(SCISSORS, Outcome.WIN, Outcome.DRAW, Outcome.LOSE);
        initRow(ROCK, Outcome.LOSE, Outcome.WIN, Outcome.DRAW);
    }

    private static void initRow(CaseFive it, Outcome p, Outcome s, Outcome r) {
        EnumMap<CaseFive, Outcome> row = table.get(it);
        row.put(PAPER, p);
        row.put(SCISSORS, s);
        row.put(ROCK, r);
    }

    @Override
    public Outcome compete(CaseFive it) {
        return table.get(this).get(it);
    }
}

enum CaseSix implements Competitor<CaseSix> {
    PAPER, SCISSORS, ROCK;

    private static Outcome[][] table = {
            {Outcome.DRAW, Outcome.LOSE, Outcome.WIN},
            {Outcome.WIN, Outcome.DRAW, Outcome.LOSE},
            {Outcome.LOSE, Outcome.WIN, Outcome.DRAW}
    };

    @Override
    public Outcome compete(CaseSix it) {
        return table[this.ordinal()][it.ordinal()];
    }
}




