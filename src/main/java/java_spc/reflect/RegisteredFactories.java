package java_spc.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisteredFactories {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(Part.createRandom());
        }
    }
}

interface Factory<T> {
    T create();
}

class Part {
    private static Random rand = new Random(System.currentTimeMillis());
    private static List<Factory<? extends Part>> partFactory = new ArrayList<>();

    static {
        partFactory.add(new ColorFilter.Factory());
        partFactory.add(new WeightFilter.Factory());
        partFactory.add(new ActionListener.Factory());
    }

    public static Part createRandom() {
        int n = rand.nextInt(partFactory.size());
        rand.setSeed(System.currentTimeMillis());
        return partFactory.get(n).create();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

class Filter extends Part {
}

class ColorFilter extends Filter {
    private ColorFilter() {
    }

    public static class Factory implements java_spc.reflect.Factory<ColorFilter> {
        @Override
        public ColorFilter create() {
            return new ColorFilter();
        }
    }
}

class WeightFilter extends Filter {
    private WeightFilter() {
    }

    public static class Factory implements java_spc.reflect.Factory<WeightFilter> {
        @Override
        public WeightFilter create() {
            return new WeightFilter();
        }
    }
}

class Listener extends Part {
}

class ActionListener extends Listener {
    private ActionListener() {
    }

    public static class Factory implements java_spc.reflect.Factory<ActionListener> {
        @Override
        public ActionListener create() {
            return new ActionListener();
        }
    }
}






