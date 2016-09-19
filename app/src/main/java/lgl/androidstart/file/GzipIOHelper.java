package lgl.androidstart.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import lgl.androidstart.tool.L;

/**
 * Created by LGL on 2016/9/18.
 */
public class GzipIOHelper implements LIO {

    public String convertGzipStr(String str) {
        return "";
    }

    /**
     * 解压GZip
     * @param source
     * @param target
     * @return
     */
    public static boolean UnGzip(String source,String target) {
        File file = new File(source);
        if (!file.exists()) {
            L.i("这个文件不存在");
            return false;
        }
        try {
            FileInputStream temp = new FileInputStream(file);
            InputStream inputStream = isGzip(temp);
            IOHelper.WirteFile(inputStream,target);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是不是GZip流
     * @param inputStream  转化过后的流
     *                     ----- inputStream是不可逆转的   所以这里做了检测就必须将使用过得流返回出去
     *                     否者报 EOInputStream 异常
     * @return
     */
    public static InputStream isGzip(InputStream inputStream) {
        // 取前两个字节
        byte[] header = new byte[2];
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        bis.mark(2);
        try {
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int ss = (header[0] & 0xff) | ((header[1] & 0xff) << 8);
            if (result != -1 && ss == GZIPInputStream.GZIP_MAGIC)
                return new GZIPInputStream(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bis;
    }

    /**
     * 将一个流传入，如果是GZip压缩过的将处理为GZIPInputStream
     * @param inputStream
     * @return
     */
//    public static InputStream ConvertGzipInputStream(InputStream inputStream) {
//        try {
//            if (isGzip(inputStream))
//                return new GZIPInputStream(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return inputStream;
//    }
}
