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
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

import lgl.androidstart.http.RequestFactory;

/**
 * 作者: LGL on 2016/8/8. 邮箱: 468577977@qq.com
 *
 * @description
 */
public class IOHelper implements LIO {

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
        File file = FileHelper.getFile(path,true);
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
     * @param path   要写入的文件名
     */
    public static void WirteFile(InputStream inputStream, String path) {
        File file =FileHelper.getFile(path,true);
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");//"r", "rw", "rws", or "rwd"
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                raf.write(buffer, 0, len);
            }
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file.length() < 1) file.delete();//写入失败，将其删除
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
     *
     * @param inputStream
     * @param target            目标文件路径  eg: 路径+文件名.txt
     * @param start             从哪里开始写
     * @param howLenghtListener 写入进度监听器
     */
    public static void RandomWirte(InputStream inputStream, File target, int start, wirteHowLenghtListener howLenghtListener) {
        if (inputStream == null) return;
        if (target == null || !target.exists() || target.length() + 1 < start) return;//无效文件
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
        } finally {
            try {
                if (raf != null) raf.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void RandomWirte(byte[] buffer, String file_path, int start) {
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        File target = FileHelper.getFile(file_path,true);
        try {
            RandomAccessFile raf = new RandomAccessFile(target, "rwd");
            raf.setLength(buffer.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        RandomWirte(byteArrayInputStream, , start, null);
    }

    /**
     * @param outputStream 网络流
     * @param parames      所有的参数
     */
    public static void WirtePremes(OutputStream outputStream, HashMap<String, Object> parames) {
        StringBuffer stringBuffer = new StringBuffer();
        String rn = "\r\n";
        LinkedHashMap<String, File> files = new LinkedHashMap<>();
        long sizeSum = 0;//总大小
        for (String key : parames.keySet()) {
            Object o = parames.get(key);

            if (o instanceof String) {
                stringBuffer.append("--" + RequestFactory.boundary);
                stringBuffer.append(rn);
                stringBuffer.append(MessageFormat.format("Content-Disposition: form-data; name=\"{0}\"", key));
                stringBuffer.append(rn);
                stringBuffer.append(rn);
                stringBuffer.append((String) o);
                stringBuffer.append(rn);
                //一个文件到此结束
            } else if (o instanceof File) {
                File file = (File) o;
                files.put(key, file);
                sizeSum += file.length();
            }
        }

        byte[] premesStr = stringBuffer.toString().getBytes();
        sizeSum += premesStr.length;
        try {
            outputStream.write(premesStr);
            for (String key : files.keySet()) {
                byte[] newline = "\r\n".getBytes();//换行
                outputStream.write(("--" + RequestFactory.boundary).getBytes());
                outputStream.write(newline);
                outputStream.write("Content-Disposition: form-data; name=\"file\"; filename=\"i.png\"".getBytes());
                outputStream.write(newline);
                outputStream.write("Content-Type: application/octet-stream".getBytes());//这个地方是两个\r\n
                outputStream.write(newline);
                outputStream.write(newline);

                File file = files.get(key);
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                byte[] buffer = new byte[1024 * 5];
                int len;
                while ((len = randomAccessFile.read(buffer)) > 0)
                    outputStream.write(buffer, 0, len);
            }
            String end = "\r\n--" + RequestFactory.boundary + "--";//结束   要在后面加上    --
            outputStream.write(end.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
