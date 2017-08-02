package java_spc.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UseStream {
    public static List<String> sortDishBefore(List<Dish> menu) {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish d : menu) {
            if (d.getCalories() < 400) {
                lowCaloricDishes.add(d);
            }
        }
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {

            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish d : lowCaloricDishes) {
            lowCaloricDishesName.add(d.getName());
        }
        return lowCaloricDishesName;
    }

    public static List<String> sortDishAfter(List<Dish> menu) {
        return menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Dish> dishs = Dish.generator(20);
        System.out.println(dishs + "\n" + sortDishBefore(dishs));
        System.out.println(dishs + "\n" + sortDishBefore(dishs));
    }
}

class Dish {
    private static Integer id = 10000;
    private static Random random = new Random(23333);

    public static enum TYPE {FISH, MEAT, CHICKEN, OTHER}

    ;
    private String name;
    private Integer calories;
    private Double price;
    private TYPE type;

    public Dish() {
    }

    public Dish(String name, Integer calories, Double price, TYPE type) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String toString() {
        return "{name:" + name + ",calories:" + calories + ",price:" + price + ",type:" + type + "}";
    }

    public static List<Dish> generator(int size) {
        List<Dish> dishs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Dish dish = new Dish();
            dish.setName("menu-" + id);
            dish.setCalories(random.nextInt(1000));
            dish.setPrice(random.nextInt(1000) / 10.0);
            dish.setType(TYPE.values()[random.nextInt(TYPE.values().length)]);
            dishs.add(dish);
            id++;
        }
        return dishs;
    }

}