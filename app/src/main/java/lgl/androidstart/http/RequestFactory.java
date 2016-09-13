package lgl.androidstart.http;

import java.io.File;
import java.io.IOException;
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
 * @description  做Http的配置
 */
public class RequestFactory {

    /**
     * 分割线
     */
    public static final String boundary = "---5Y2i5qGC5p6X";//base64

    private static boolean deBug = true;

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
        if (prams==null)return "";
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
            L.i(key + entry.getValue());
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

    public static HttpURLConnection postHttpURLConnection(HttpURLConnection connection, String boundary, String cookie) {
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);// 不缓存 可以的得到进度
        // connection.setFixedLengthStreamingMode(contentLength);
        connection.setChunkedStreamingMode(0);//不分块
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setConnectTimeout(10000);
        connection.setRequestProperty("Connection", "keep-alive");
//      connection.setRequestProperty("Content-Length", Length + "");
        connection.setRequestProperty("Transfer-Encoding", "chunked");//Length
        connection.setRequestProperty("Cache-Control", "max-age=0");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setRequestProperty("Accept-Encoding", "deflate");//gzip,
//      connection.addRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        connection.setRequestProperty("Charset", "UTF-8");
        if (cookie != null)
            connection.addRequestProperty("Cookie", cookie);
        return connection;
    }

    /**
     * 设置Get参数
     * @param connection
     * @param cookie
     * @return
     */
    /**
     *
     * @param connection 链接对象
     * @param cookie 没有cookie可以传null
     * @param RangeStart 开始位置
     * @param RangeEnd 结束位置
     * @return 配置好的httprulconnction对象
     */
    public static HttpURLConnection getHttpURLConnection(HttpURLConnection connection, String cookie,long RangeStart,long RangeEnd) {
//        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);// 不缓存 可以的得到进度
        // connection.setFixedLengthStreamingMode(contentLength);
        connection.setChunkedStreamingMode(0);//不分块
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
        String rangeStr="";
        if (RangeStart>0){//bytes=154-456
            rangeStr="bytes="+RangeStart+"-";
            if (RangeEnd>0)rangeStr+=RangeEnd;
            connection.setRequestProperty("Range", rangeStr);
        }
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");//
        if (cookie != null)
            connection.addRequestProperty("Cookie", cookie);
        return connection;
    }



    private static HttpURLConnection getConnection(String urlStr, HashMap<String, String> prames){
        urlStr += getPramesString(prames, "utf-8");

        if (deBug) L.i(urlStr);
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static final int GET = 0x11111;
    public static final int POST = 0x2222;

    public static HttpURLConnection Build(String urlStr, HashMap<String, String> prames, int method,long RangeStart,long RangeEnd) {
        HttpURLConnection connection =getConnection(urlStr, prames);
        switch (method) {
            case GET:
                return getHttpURLConnection(connection, null,RangeStart,RangeEnd);
            case POST:
                return postHttpURLConnection(connection, boundary, null);
            default:
                return connection;
        }
    }
    public static HttpURLConnection BuildGetRange(String urlStr, HashMap<String, String> prames,long RangeStart,long RangeEnd) {
        HttpURLConnection connection =getConnection(urlStr, prames);
        return getHttpURLConnection(connection, null,RangeStart,RangeEnd);
    }
    public static HttpURLConnection BuildGet(String urlStr, HashMap<String, String> prames) {
        HttpURLConnection connection =getConnection(urlStr, prames);
        return getHttpURLConnection(connection, null,0,0);
    }
    public static HttpURLConnection BuildPost(String urlStr, HashMap<String, String> prames) {
        HttpURLConnection connection =getConnection(urlStr, prames);
        return postHttpURLConnection(connection, boundary, null);
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
            textContent.append("Content-Type: multipart/form-data");
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
