package lgl.androidstart.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import sun.misc.BASE64Decoder.encoder.BASE64Decoder;
import sun.misc.BASE64Decoder.encoder.BASE64Encoder;

/**
 * http://blog.csdn.net/u012488189/article/details/17709797
 * @author LGL on 2016/9/13.
 * @description 使用java的方式进行加密
 * <p>
 * Base64Encoder并不属于JDK标准库范畴，但是又包含在了JDK中
 * %JAVA_HOME%\jre\lib\rt.jar
 * 本类中引用的就源码
 */
public class JavaEncrypt {

    /**
     * java  Base64解密
     *
     * @param s 目标字符串
     * @return
     */
    public static byte[] getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            try {
                b = new BASE64Decoder().decodeBuffer(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        return b;
    }

    /**
     * java  Base64加密
     *
     * @param data
     * @return
     */
    public static String getBase64(byte[] data) {
        return new BASE64Encoder().encode(data);
    }

    /**
     * java  Base64 加密
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getBase64(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] b = new byte[fileInputStream.available()];
        fileInputStream.read(b);
        if (b != null) {
            return new BASE64Encoder().encode(b);
        }
        return "";
    }
}
