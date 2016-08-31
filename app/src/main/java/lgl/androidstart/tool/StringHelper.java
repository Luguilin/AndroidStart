package lgl.androidstart.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对String进行一定的处理功能
 *
 * @author LGL
 */
public class StringHelper {

    /**
     * 检出字符串是否为空
     *
     * @param s
     * @return 是空的
     */
    public static Boolean isEmpty(String s) {
        if (s == null || s.equals("") || s.length() < 1) {
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

    public static String toMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //            System.out.println(buf.toString());// 32位的加密
            //            System.out.println(buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
            return buf.toString().substring(8, 24);
        } catch (Exception e) {
            return "" + (int) (Math.random() * 1000000);//如果加密失败则给出一个随机数
        }
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

}
