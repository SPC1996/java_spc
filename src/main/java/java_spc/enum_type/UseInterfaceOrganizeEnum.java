package java_spc.enum_type;

import java_spc.util.Enums;

/**
 * 组织枚举的方式:
 * 1.使用接口组织枚举 -- 在一个接口的内部，创建实现该接口的枚举
 * 2.使用枚举的枚举
 * 3.将一个enum嵌套入另外一个enum
 *
 * @author SPC
 */
public class UseInterfaceOrganizeEnum {
    //方式一
    private interface Food {
        enum Appetizer implements Food {
            SALAD, SOUP, SPRING_ROLLS;
        }

        enum MainCourse implements Food {
            LASAGNE, BURRITO, PAD_THAI, LENTILS, HUMMOUS, VINDALOO;
        }

        enum Dessert implements Food {
            TIRAMISU, GELATO, BLACK_FOREST_CAKE, FRUIT, CREME_CARAMEL;
        }
    }

    //方式二
    private enum Course {
        APPETIZER(Food.Appetizer.class),
        MAINCOURSE(Food.MainCourse.class),
        DESSERT(Food.Dessert.class);

        private Food[] values;

        private Course(Class<? extends Food> kind) {
            values = kind.getEnumConstants();
        }

        public Food randomSelect() {
            return Enums.random(values);
        }
    }

    private enum SecurityCategory {
        STOCK(Security.Stock.class), BOND(Security.Bond.class);

        interface Security {
            enum Stock implements Security {SHORT, LONG, MARGIN}

            enum Bond implements Security {MUNICIPAL, JUNK}
        }

        Security[] values;

        SecurityCategory(Class<? extends Security> kind) {
            values = kind.getEnumConstants();
        }

        public Security randomSelect() {
            return Enums.random(values);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            for (Course course : Course.values()) {
                Food food = course.randomSelect();
                System.out.println(food);
            }
            System.out.println("----------------------");
        }

        for (int i = 0; i < 10; i++) {
            SecurityCategory category = Enums.random(SecurityCategory.class);
            System.out.println(category + " : " + category.randomSelect());
        }
    }
}

