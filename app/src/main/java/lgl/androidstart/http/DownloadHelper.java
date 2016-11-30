package lgl.androidstart.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import static lgl.androidstart.http.RequestFactory.getHttpResponseHeader;

/**
 * Created by LGL on 2016/11/30.
 * 本类是用了多线程下载技术，请在主线程中 new 出本对象   因为进度需要发送到到主线程
 */

public class DownloadHelper {

    /**
     * @param urlStr     URL
     * @param RangeStart 开始位置  不大于0将不限制
     * @param RangeEnd   结束位置  不大于0将不限制
     * @return 配置好的httprulconnction对象
     */
    private HttpURLConnection getHttpURLConnection(String urlStr, long RangeStart, long RangeEnd) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);// 不缓存 可以的得到进度(同时我们上传大文件的时候会导致内存溢出的问题，所以这里关闭缓存)
        // connection.setFixedLengthStreamingMode(contentLength);
        connection.setChunkedStreamingMode(0);//不分块   默认大小为2MB   为了得到进度 我们不分块缓存
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setConnectTimeout(10000);
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        connection.setRequestProperty("Charset", "UTF-8");
        String rangeStr = "";
        if (RangeStart >= 0) {//bytes=154-456
            rangeStr = "bytes=" + RangeStart + "-";
            if (RangeEnd > 0) rangeStr += RangeEnd;
            connection.setRequestProperty("Range", rangeStr);
        }
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");//gzip,
        return connection;
    }

    InputStream getInputString(String urlStr, long RangeStart, long RangeEnd) {
        HttpURLConnection connection = getHttpURLConnection(urlStr, RangeStart, RangeEnd);
        try {
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    long getLength(String urlStr) {
        HttpURLConnection connection = getHttpURLConnection(urlStr, -1, -1);
        try {
            connection.connect();
        } catch (IOException e) {
            return 0;
        }
        boolean hasLength = connection.getHeaderFields().containsKey("Content-Length");
        if (hasLength) {
            return Long.parseLong(connection.getHeaderField("Content-Length"));
        }
        return 0;
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mListener!=null)mListener.update(msg.what);
        }
    };

    /**
     * 打印Http头字段
     *
     * @param http
     */
    public void printResponseHeader(HttpURLConnection http) {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
            Log.e("===",key + entry.getValue());
        }
    }

    DownloadListener mListener;
    String newUrl="";

    private void download(String urlStr, File target) throws IOException {
        final long length = getLength(urlStr);
        RandomAccessFile raf = new RandomAccessFile(target, "rwd");
        raf.setLength(length);
        raf.close();

        final int cpuCount = Runtime.getRuntime().availableProcessors() / 2;//开启CPU一半的线程来下载

        int partSize = (int) (length / cpuCount);//每一段大小

        int[] ints = new int[cpuCount];
        ints[0] = 0;
        for (int i = 1; i < ints.length; i++) {
            ints[i] = partSize * i;
        }

        DownloadListener downloadListener = new DownloadListener() {
            float currentSize = 0f;
            int currentProgress = 0;

            @Override
            public void update(int progress) {
                synchronized (this) {
                    currentSize += progress;
                    if (progress == length) {
                        currentProgress = 100;
                        mHandler.obtainMessage(currentProgress).sendToTarget();
                        return;
                    }
                    int temp_progress = (int) (currentSize * 100 / length);//四舍五入
                    if (currentProgress == 0 || temp_progress - 1 > currentProgress) {
                        currentProgress++;
                        mHandler.obtainMessage(currentProgress).sendToTarget();
                    }
                }
            }
        };

        for (int i = 0; i < ints.length - 1; i++) {
            long RangeStart = ints[i];
            long RangeEnd = ints[i + 1];
            Thread thread = new Thread(new DownloadTask(urlStr, target, RangeStart, RangeEnd, downloadListener));
            thread.start();
            thread.setName(RangeStart + "---" + RangeEnd);
        }
        new Thread(new DownloadTask(urlStr, target, ints[ints.length - 1], length, downloadListener)).start();
    }

    public void download(final String urlStr, final File target, DownloadListener listener) throws IOException {
        if (target==null||!target.exists()||urlStr.length()<5){
            listener.update(100);
            return;
        }
        if (newUrl.equals(urlStr))return;//不能重复下载
        mListener=listener;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    download(urlStr, target);
                } catch (IOException e) {
                }
            }
        }).start();
    }

    public interface DownloadListener {
        void update(int progress);
    }

    /**
     * 单线程下载任务体
     */
    class DownloadTask implements Runnable {

        File target;
        String urlStr;
        long offset;
        long RangeEnd;
        DownloadListener downloadListener;

        public DownloadTask(String urlStr, File target, long rangeStart, long rangeEnd, DownloadListener downloadListener) {
            this.target = target;
            this.urlStr = urlStr;
            offset = rangeStart;
            RangeEnd = rangeEnd;
            this.downloadListener = downloadListener;
        }

        @Override
        public void run() {
            InputStream inputStream = getInputString(urlStr, offset, RangeEnd);
            try {
                RandomAccessFile raf = new RandomAccessFile(target, "rwd");
                raf.seek(offset);
                int len;
                byte[] buffer = new byte[1024 * 10];//10KB
                while ((len = inputStream.read(buffer)) > 0) {
                    raf.write(buffer, 0, len);
                    if (downloadListener != null) downloadListener.update(len);
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
