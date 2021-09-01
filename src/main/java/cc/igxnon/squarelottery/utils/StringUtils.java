package cc.igxnon.squarelottery.utils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author iGxnon
 * @date 2021/8/31
 */
public class StringUtils {

    /**
     * 一个很烂的算法
     * @param targetString 目标 String
     * @param startMatchedStr 起始标记
     * @param endMatchedStr 终止标记
     * @return 结果
     */
    public static String[] readContent(String targetString, String startMatchedStr, String endMatchedStr) {
        String[] strings = new String[targetString.length()];
        for (int i = 0; i < targetString.length(); i ++) {
            strings[i] = targetString.substring(i, i + 1);
        }
        int startMatchedCount = appearNumber(targetString, startMatchedStr);
        int endMatchedCount = appearNumber(targetString, endMatchedStr);
        if(startMatchedCount != endMatchedCount) {
            return new String[0];
        }
        String[] result = new String[startMatchedCount];
        AtomicReference<StringBuilder> stringBuilder = new AtomicReference<>(new StringBuilder());
        AtomicBoolean checkEncoding = new AtomicBoolean(false);
        Arrays.stream(strings).forEach(str -> {
            if(str.equals(endMatchedStr)){
                checkEncoding.set(false);
                stringBuilder.get().append(endMatchedStr);
                result[notNullCount(result)] = stringBuilder.get().toString();
                stringBuilder.set(new StringBuilder());
            }
            if(checkEncoding.get()) {
                stringBuilder.get().append(str);
            }
            if(str.equals(startMatchedStr)) {
                checkEncoding.set(true);
                stringBuilder.get().append(startMatchedStr);
            }
        });
        return result;
    }

    public static int notNullCount(String[] target) {
        int count = 0;
        for (String str : target) {
            if(str != null) {
                count ++;
            }
        }
        return count;
    }

    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        int index = 0;
        while ((index = srcText.indexOf(findText, index)) != -1) {
            index = index + findText.length();
            count++;
        }
        return count;
    }

}
