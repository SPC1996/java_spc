package java_spc.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList3 {
    public static void main(String[] args) {
        File filePath = new File(".");
        String[] list;

        if (args.length == 0) {
            list = filePath.list();
        } else {
            list = filePath.list(new FilenameFilter() {
                private Pattern pattern = Pattern.compile(args[0]);

                public boolean accept(File dir, String name) {
                    return pattern.matcher(name).matches();
                }
            });
        }
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        long sumSize = 0;
        for (String item : list) {
            sumSize += new File(item).length();
            System.out.println(item);
        }
        System.out.println(sumSize);
    }
}