package lgl.androidstart.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lgl.androidstart.tool.L;

/**
 * @author LGL
 * @description
 */
public class RequestFactory {

    private static boolean deBug=true;

    public RequestFactory setDeBug(boolean deBug) {
        this.deBug = deBug;
        return this;
    }

    /**
     * @param prams
     * @param encode （字节码）客户端向服务器申请数据的时候可能会出现中文乱码的问题
     * @return
     */

    public static String getPramesString(HashMap<String, String> prams, String encode) {
        StringBuffer buffer = new StringBuffer("?");
        for (String key : prams.keySet()) {
            try {
                buffer.append(key + "=" + URLEncoder.encode(prams.get(key), encode) + "&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString().trim();
    }

    /**
     * 获取Http响应头字段
     *
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        for (int i = 0; ; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null)
                break;
            header.put(http.getHeaderFieldKey(i), mine);
        }
        return header;
    }

    /**
     * 打印Http头字段
     *
     * @param http
     */
    public static void printResponseHeader(HttpURLConnection http) {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
            System.out.println(key + entry.getValue());
        }
    }

    /**
     * 获取文件名
     */
    private String getFileName(String downloadUrl, HttpURLConnection conn) {
        String filename = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1);
        if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
            for (int i = 0; ; i++) {
                String mine = conn.getHeaderField(i);
                if (mine == null)
                    break;
                if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
                    Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                    if (m.find())
                        return m.group(1);
                }
            }
            filename = UUID.randomUUID() + ".tmp";// 默认取一个文件名
        }
        return filename;
    }

    public static HttpURLConnection postHttpURLConnection(HttpURLConnection connection, String boundary, String cookie) throws ProtocolException {
        connection.setDoOutput(true);
        connection.setDoInput(true);
        // connection.setFixedLengthStreamingMode(contentLength);
        connection.setUseCaches(false);// 不缓存 可以的得到进度
        // connection.setFixedLengthStreamingMode(contentLength);
        connection.setChunkedStreamingMode(0);
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(10000);
        connection.setRequestProperty("Connection", "keep-alive");
        // connection.setRequestProperty("Content-Length", Length + "");
        connection.setRequestProperty("Cache-Control", "max-age=0");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setRequestProperty("Accept-Encoding", "deflate");
        connection.addRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        connection.setRequestProperty("Charset", "UTF-8");
        if (cookie != null)
            connection.addRequestProperty("Cookie", cookie);
        return connection;
    }
    public static HttpURLConnection getHttpURLConnection(String urlStr,HashMap<String,String> prames){
        urlStr+=getPramesString(prames,"utf-8");
        if (deBug) L.i(urlStr);
        HttpURLConnection connection=null;
        try {
            URL url=new URL(urlStr);
            connection= (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.setDoOutput(true);
        connection.setDoInput(true);
        // connection.setFixedLengthStreamingMode(contentLength);
        connection.setUseCaches(false);// 不缓存 可以的得到进度
        // connection.setFixedLengthStreamingMode(contentLength);
        connection.setChunkedStreamingMode(0);
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setConnectTimeout(10000);
        connection.setRequestProperty("Connection", "keep-alive");
        // connection.setRequestProperty("Content-Length", Length + "");
        connection.setRequestProperty("Cache-Control", "max-age=0");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setRequestProperty("Accept-Encoding", "deflate");
        connection.addRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        connection.setRequestProperty("Charset", "UTF-8");
        return connection;
    }

    /**
     * 写入表单数据
     *
     * @param connection     连接对象
     * @param textSet        表单中的文本集
     * @param fileMap        表单中的文件集合
     * @param updataProgress 上传进度的接口
     * @throws IOException
     */
    public void WirteMultipart(HttpURLConnection connection, Map<String, String> textSet, Map<String, File> fileMap, UpdataProgress updataProgress)
            throws IOException {
        String end = "\r\n";
        String boundary = end + "----4220f6594b9c88cb10484fd01ad6d92b" + end;//卢桂林

        byte[] byetContent = getTextContent(boundary, textSet);
        int contentLength = byetContent.length;
        contentLength += getFileSize(boundary, fileMap);

        connection.setRequestProperty("Content-Length", "" + contentLength);
        // connection.setRequestProperty("Transfer-Encoding", "chunked");
        connection.connect();// 开始连接
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return;
        }
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

        outputStream.write(byetContent);

        int current_length = byetContent.length;
        if (updataProgress != null)
            updataProgress.Progress(contentLength, current_length);
        byetContent = null;// 尽快的放出内存
        // =======================================华丽的分割线

        StringBuffer textContent = new StringBuffer(end);
        for (String key : fileMap.keySet()) {
            textContent.append(boundary);
            textContent.append("Content-Disposition: form-data; name=\"" + key + "\"");
            textContent.append(end);
            // textContent.append("Content-Type: application/octet-stream");
            textContent.append("Content-Type: image/jpeg");
            textContent.append(end);

            byte[] fileText = textContent.toString().getBytes("utf-8");
            outputStream.write(fileText);

            current_length += fileText.length;

            File file = fileMap.get(key);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            byte[] buffer = new byte[1024 * 5];// 这代表每5KB才走一个进度
            int len = -1;
            while (((len = raf.read(buffer)) > 0)) {
                outputStream.write(buffer, 0, len);
                current_length += len;
                if (updataProgress != null)
                    updataProgress.Progress(contentLength, current_length);
            }
            raf.close();
        }
        outputStream.write(boundary.getBytes());// 这根分割线没有算入进度条中 因为我懒^_^
        outputStream.close();
        connection.disconnect();
        if (updataProgress != null)
            updataProgress.Progress(contentLength, contentLength);// 写入完成
    }

    /**
     * 得到文件大小
     *
     * @param boundary
     * @param fileMap
     * @return
     * @throws UnsupportedEncodingException
     */
    int getFileSize(String boundary, Map<String, File> fileMap) throws UnsupportedEncodingException {
        String end = "\r\n";

        StringBuffer textContent = new StringBuffer(end);
        int length = 0;
        for (String key : fileMap.keySet()) {
            textContent.append(boundary);
            textContent.append("Content-Disposition: form-data; name=\"" + key + "\"");
            textContent.append(end);
            textContent.append("Content-Type: image/jpeg");
            // textContent.append("Content-Type: application/octet-stream");
            textContent.append(end);
            length += fileMap.get(key).length();
        }
        textContent.append(boundary);// 添加最后一根华丽的分割线
        length += textContent.toString().getBytes("utf-8").length;
        return length;
    }

    /**
     * 得到请求中包含的文本信息的大小
     *
     * @param boundary
     * @param textSet
     * @return
     * @throws UnsupportedEncodingException
     */
    byte[] getTextContent(String boundary, Map<String, String> textSet) throws UnsupportedEncodingException {
        String end = "\r\n";
        StringBuffer textContent = new StringBuffer(end);

        for (String key : textSet.keySet()) {
            textContent.append(boundary);
            textContent.append("Content-Disposition: form-data; name=\"" + key + "\"");
            textContent.append(end);
            textContent.append(textSet.get(key));
            textContent.append(end);
        }
        byte[] byetContent = textContent.toString().getBytes("utf-8");
        return byetContent;
    }

    /**
     * 进度回调的接口
     *
     * @author LGL
     */
    public interface UpdataProgress {
        public void Progress(int contentLength, int current_length);
    }

}
