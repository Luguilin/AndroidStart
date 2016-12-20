package lgl.androidstart.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LGL
 * @description 对String进行一定的处理功能
 */
public class StringHelper {

    /**
     * 检出字符串是否为空
     *
     * @param s
     * @return 是空的
     */
    public static Boolean isEmpty(String s) {
        if (s == null || s.length() < 1 || s.equals("") || s.equals("null") || s.equals("[null]")) {
            return true;
        }
        return false;
    }

    /**
     * 判断两个字符串是否相同
     *
     * @param actual
     * @param expected
     * @return 相等
     */
    public static boolean isEquals(String actual, String expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * 多字符串进行MD5加密
     *
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * 根据URL截取文件名  eg:aaa.jpg
     *
     * @param urlStr
     * @return
     */
    public static String getNameFromUrl(String urlStr) {
        if (urlStr.contains("/"))
            return urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
        else return "";
    }

    // 校验文本只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 获取随机字符串
     *
     * @param len 字符串的长度
     * @return
     */
    public static String getRandomString(int len) {
        String returnStr = "";
        char[] ch = new char[len];
        Random rd = new Random();
        for (int i = 0; i < len; i++) {
            ch[i] = (char) (rd.nextInt(9) + 97);
        }
        returnStr = new String(ch);
        return returnStr;
    }


    /**
     * 获得名字不包含文件
     *
     * @param file_name eg:  aa.txt  -->  aa
     *                  eg:   aa.txt.gz   --->  aa.txt
     * @return 文件名
     */
    public static String getName4FileName(String file_name) {
        if (!file_name.contains(".")) return "temp_" + Math.random();
        else return file_name.substring(0, file_name.lastIndexOf("."));
    }

    /**
     * 转换为万
     *
     * @param number
     * @param pattern
     * @return
     */
    public static String convertNumber2WanString(double number, String... pattern) {
        if (number > 100000) {
            number = number / 10000;
            return new DecimalFormat(pattern[0]).format(number) + "万";// 格式化设置
        } else {
            if (pattern.length > 1) return new DecimalFormat(pattern[1]).format(number);
            return number + "";
        }
    }

}
