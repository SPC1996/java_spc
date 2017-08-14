package java_spc.util;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class MethodDifference {
    private static Set<String> objectMethods = methods(Object.class);

    static {
        objectMethods.add("clone");
    }

    public static void difference(Class<?> superSet, Class<?> subSet) {
        Set<String> result = Sets.difference(methods(superSet), methods(subSet));
        result.removeAll(objectMethods);
        System.out.println("Difference in " + superSet.getSimpleName() + " and " + subSet.getSimpleName() + " : " + result);
    }

    public static Set<String> methods(Class<?> type) {
        return Arrays.stream(type.getMethods())
                .map(Method::getName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public static List<String> interfaces(Class<?> type) {
        return Arrays.stream(type.getInterfaces())
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println("Collection Methods : " + methods(Collection.class));
        System.out.println("Collection Interface : " + interfaces(Collection.class));
        difference(Set.class, Collection.class);
        difference(HashSet.class, Set.class);
        difference(LinkedHashSet.class, HashSet.class);
        difference(TreeSet.class, Set.class);
        difference(List.class, Collection.class);
        difference(ArrayList.class, List.class);
        difference(LinkedList.class, List.class);
        difference(Queue.class, Collection.class);
        difference(PriorityQueue.class, Queue.class);
        difference(HashMap.class, Map.class);
        difference(LinkedHashMap.class, HashMap.class);
        difference(SortedMap.class, Map.class);
        difference(TreeMap.class, Map.class);
    }
}
