package java_spc.java8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java_spc.util.Resource;

public class UseLambda{
    public static String processFile(BFProcessor p) throws IOException{
        try (BufferedReader br=new BufferedReader(
            new InputStreamReader(new FileInputStream(Resource.path("file/sing.txt")), "utf-8")
        )) {
            return p.process(br);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> results=new ArrayList<>();
        for(T x : list){
            if(p.test(x)){
                results.add(x);
            }
        }
        return results;
    }

    public static <T> void forEach(List<T> list, Consumer<T> c){
        for(T t : list){
            c.accept(t);
        }
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f){
        List<R> result=new ArrayList<>();
        for(T t : list){
            result.add(f.apply(t));
        }
        return result;
    }

    public static void main(String[] args) throws IOException{
        // String oneLine=processFile((BufferedReader br) -> br.readLine());
        String oneLine=processFile(BufferedReader::readLine);
        String twoLine=processFile((BufferedReader br) -> br.readLine()+br.readLine());
        System.out.println(oneLine+"\n"+twoLine);
        String[] ss={"a","","b","c"};
        List<String> listOfString=Arrays.asList(ss);
        List<String> noEmpty=filter(listOfString, (String s) -> !s.isEmpty());
        System.out.println(noEmpty);
        // forEach(Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println(i*i));
        // List<Integer> count=map(Arrays.asList("lambda","ada","c","fuck"), (String s) -> s.length());
        forEach(Arrays.asList(1,2,3,4,5), System.out::println);
        List<String> count=map(Arrays.asList("lambda","ada","c","fuck"), String::toUpperCase);
        count.sort(String::compareToIgnoreCase);
        System.out.println(count);
    }
}

@FunctionalInterface
interface BFProcessor{
    String process(BufferedReader bf) throws IOException;
}

@FunctionalInterface
interface Predicate<T>{
    boolean test(T t);
}

@FunctionalInterface
interface Consumer<T>{
    void accept(T t);
}

@FunctionalInterface
interface Function<T, R>{
    R apply(T t);
}