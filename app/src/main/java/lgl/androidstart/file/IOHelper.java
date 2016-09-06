package lgl.androidstart.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/**
 * 作者: LGL on 2016/8/8. 邮箱: 468577977@qq.com
 *
 * @description
 */
public class IOHelper {

    /**
     * 从制定路径读入流
     *
     * @param path 路径/文件名
     * @return
     */
    public static StringBuffer ReadString4File(String path) {
        StringBuffer sb = new StringBuffer();
        try {
            FileReader fileReader = new FileReader(new File(path));
            char[] buffer = new char[1024];
            int len = 0;
            while ((len = fileReader.read(buffer)) > 0) {
                sb.append(buffer, 0, len);
            }
            fileReader.close();
        } catch (Exception e) {
            // 文件未找到等异常
        }
        return sb;
    }

    /**
     * 从流中读取字符串
     *
     * @param inputStream
     * @return
     */
    public static StringBuffer ReadString4Stream(InputStream inputStream) {
        StringBuffer sb = new StringBuffer();
        char[] beffer = new char[1024];
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));
        int length;
        try {
            while ((length = bufferedReader.read(beffer)) > 0) {
                sb.append(beffer, 0, length);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * 写入流到SD卡
     *
     * @param byteArray 目标流
     * @param path      要写入的文件名  包含文件名
     */
    public static void WirteByteArray(byte[] byteArray, String path) {
        File file = new File(path);
        FileHelper.existFile(file);
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        byte[] buffer = new byte[1024];
        int len = 0;

        FileOutputStream fileWriter = null;
        try {
            fileWriter = new FileOutputStream(file);
            while ((len = arrayInputStream.read(buffer)) > 0) {
                fileWriter.write(buffer, 0, len);
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入流到SD卡
     *
     * @param inputStream 目标流
     * @param file_path   要写入的文件名
     */
    public static void WirteFile(InputStream inputStream, String file_path) {
        File file = new File(file_path);
        FileHelper.existFile(file);
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");//"r", "rw", "rws", or "rwd"
            byte[] buffer = new byte[1024];
            int len = 0;
            try {
                while ((len = inputStream.read(buffer)) > 0) {
                    raf.write(buffer, 0, len);
                }
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从指定文件中得到一个InputStream对象
     *
     * @param file_path eg:路径+文件.txt
     * @return
     * @throws RuntimeException 异常转为RuntimeException
     */
    public static InputStream getInputStream4File(String file_path) {
        File file = new File(file_path);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public interface wirteHowLenghtListener {
        /**
         * @param where 写到了哪里
         * @param sum   累记写的长度
         */
        public void wirteTo(int where, int sum);
    }

    /**
     * 请确保 File 有效 ！大小  >=start
     * @param inputStream
     * @param target         目标文件路径  eg: 路径+文件名.txt
     * @param start             从哪里开始写
     * @param howLenghtListener 写入进度监听器
     */
    public static void RandomWirte(InputStream inputStream, File target, int start, wirteHowLenghtListener howLenghtListener) {
        if (inputStream == null) return;
        if (target==null||!target.exists()||target.length()+1<start)return;//无效文件
        RandomAccessFile raf = null;
        int sum_len = 0;
        try {
            raf = new RandomAccessFile(target, "rwd");
            if (start > 0) raf.skipBytes(start);
            byte[] buffer = new byte[5];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                raf.write(buffer, 0, len);
                sum_len += len;//累记写的长度
                start += len;//写到了哪里
                if (howLenghtListener != null)
                    howLenghtListener.wirteTo(start, sum_len);//不要担心还没执行到这里的时候崩溃    因为状态没有更新的下次会覆盖过去
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (raf != null) raf.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void RandomWirte(byte[] buffer, String file_path, int start) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        File target=new File(file_path);
        FileHelper.existFile(target);
        try {
            RandomAccessFile raf =new RandomAccessFile(target,"rwd");
            raf.setLength(buffer.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        RandomWirte(byteArrayInputStream, , start, null);

    }

}
