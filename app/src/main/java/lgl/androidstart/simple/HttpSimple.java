package lgl.androidstart.simple;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import lgl.androidstart.file.IOHelper;
import lgl.androidstart.http.RequestFactory;
import lgl.androidstart.tool.L;

/**
 * @author LGL on 2016/9/19.
 * @description
 */
public class HttpSimple implements BaseSimple{

    String urlstr="http://apis.juhe.cn/mobile/get";
    @Override
    public void Main() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> parameter=new HashMap<String, String>();
                parameter.put("phone","1889414");
                parameter.put("key","378d6498179ff0355703e65dcd224113");
                L.e("----"+RequestFactory.getPramesString(parameter,"utf-8"));
                HttpURLConnection connection = RequestFactory.BuildGet(urlstr, parameter);
                try {
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    L.e("length:" + connection.getContentLength());
//                    String path = FileHelper.getExternalSdCardPath() + File.separator + "aaa/RxJava.html";
//                    L.e(path);
//                    IOHelper.WirteFile(inputStream,path );
                    String temp = IOHelper.ReadString4Stream(inputStream).toString();
//                    String s = new String(MyGZIP.unZip(temp.getBytes()));
                    L.e(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    String token;

    private void 获取Token() {
        try {
            String urlstr = "http://192.168.1.192:4080/resourceview/token/get.htm";
            HashMap<String, String> premes = new HashMap<>();
            premes.put("name", "cHN5MQ==");
            premes.put("password", "MTEx");
            HttpURLConnection httpURLConnection = RequestFactory.BuildGet(urlstr, premes);

            String result = IOHelper.ReadString4Stream(httpURLConnection.getInputStream()).toString();

            JSONObject jsonObject = new JSONObject(result);
            int state = jsonObject.optInt("state", -10);
            if (state == 0) {
                token = jsonObject.optString("access_token", "");
                if (token != null) aaaaaa();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void aaaaaa() {
        String updateStr = "http://192.168.1.192:4080/resourceview/ServletAll";
        HashMap<String, String> parems = new LinkedHashMap<>();
        parems.put("action", "updateUserFace");
        parems.put("access_token", token);

        try {
            L.e(updateStr);
            HttpURLConnection connection = RequestFactory.BuildPost(updateStr, parems);
            connection.connect();

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            HashMap<String, Object> hashMap = new HashMap<>();
            File file = new File("/storage/emulated/0/A.png");
            hashMap.put("file", file);
            hashMap.put("user_id", "1");
            IOHelper.WirtePremes(outputStream, hashMap);

            outputStream.flush();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            String reslt = IOHelper.ReadString4Stream(inputStream).toString();
            L.i(reslt);
//            L.i(connection.getResponseMessage());
//            RequestFactory.printResponseHeader(connection);
//            inputStream.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
