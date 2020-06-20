package java_spc.effective.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Seven Shen
 * @version V1.0
 * @Title: RemoveTagUsingRegex.java
 * @Package java_spc.effective.regex
 * @Description 使用正则表达式去除字符串中的标签
 * @date 2020 06-20 14:24.
 */
public class RemoveTagUsingRegex {
    private static final String TAG_PATTERN = "\\{\\{SPC:(.*?)}}";

    private static String removeTags(String originStr) {
        Pattern pattern = Pattern.compile(TAG_PATTERN);
        Matcher matcher = pattern.matcher(originStr);

        StringBuilder sb = new StringBuilder();
        int left = 0;
        while (matcher.find()) {
            sb.append(originStr, left, matcher.start());
            sb.append(matcher.group(1).trim());
            left = matcher.end();
        }
        sb.append(originStr, left, originStr.length());
        return sb.toString();
    }

    public static void main(String[] args) {
        String testStr = "t}}est{{test}}{{SPC:king zone}}K{{duke}}K{{SPC: knight}}zo{o}  m";
        System.out.println(removeTags(testStr));
    }
}
