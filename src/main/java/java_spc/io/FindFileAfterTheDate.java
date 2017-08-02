package java_spc.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

import java_spc.util.ProcessFiles;

public class FindFileAfterTheDate {
    private static void usage() {
        System.err.println(
                "FindFileAfterTheDate path yyyy-MM-dd"
        );
    }

    private static boolean isUsableDate(String dateString) {
        return dateString.matches("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
    }

    public static void main(String[] args) throws ParseException {
        if (args.length != 2) {
            usage();
        } else {
            if (!isUsableDate(args[1])) {
                usage();
                return;
            }
            Date theDate = new SimpleDateFormat("yyyy-MM-dd").parse(args[1]);
            String[] paths = {args[0]};
            new ProcessFiles(new ProcessFiles.Strategy() {
                public void process(File file) {
                    if (file.lastModified() > theDate.getTime()) {
                        System.out.println(file);
                    }
                }
            }, "java").start(paths);
        }
    }
}