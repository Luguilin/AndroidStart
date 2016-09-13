package lgl.androidstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import lgl.androidstart.file.IOHelper;
import lgl.androidstart.http.RequestFactory;
import lgl.androidstart.tool.L;

public class MainActivity extends AppCompatActivity {

    String urlstr = "http://192.168.1.192:4080/resourceview/token/get.htm";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> premes = new HashMap<>();
                    premes.put("name", "cHN5MQ==");
                    premes.put("password", "MTEx");
                    HttpURLConnection httpURLConnection = RequestFactory.getHttpURLConnection(urlstr, premes);
                    L.e(httpURLConnection.getResponseCode() + "========");

                    String result = IOHelper.ReadString4Stream(httpURLConnection.getInputStream()).toString();
//                    L.i(result);

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
        }).start();

    }

    String aaa = "------WebKitFormBoundaryLLGIEgTXIUBu9iNr\n" +
            "Content-Disposition: form-data; name=\"user_id\"\n" +
            "\n" +
            "13\n" +
            "------WebKitFormBoundaryLLGIEgTXIUBu9iNr\n" +
            "Content-Disposition: form-data; name=\"file\"; filename=\"\"\n" +
            "Content-Type: application/octet-stream\n" +
            "\n" +
            "\n" +
            "------WebKitFormBoundaryLLGIEgTXIUBu9iNr--";

    public void aaaaaa() {
        String updateStr = "http://192.168.1.192:4080/resourceview/ServletAll";
        String boundary = "----WebKitFormBoundaryLLGIEgTXIUBu9iNr";
        HashMap<String, String> parems = new HashMap<>();
        parems.put("action", "updateUserFace");
        parems.put("access_token", token);
        updateStr += RequestFactory.getPramesString(parems, "utf-8");
        try {
            L.e(updateStr);
            URL url = new URL(updateStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection = RequestFactory.postHttpURLConnection(connection, boundary, null);

            connection.connect();
            OutputStream outputStream = connection.getOutputStream();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("face", new File("/storage/emulated/0/A.png"));
            hashMap.put("user_id","13");
            IOHelper.WirtePremes(outputStream, hashMap, boundary);


//            outputStream.write("Content-Disposition: form-data; name=\"file\"; filename=\"\"".getBytes());
//            outputStream.write(("\r\t").getBytes());
//            outputStream.write("Content-Type: application/octet-stream".getBytes());
//            outputStream.write("\r\t".getBytes());

//            String aa=;
//            InputStream file_pohto=IOHelper.getInputStream4File(aa);
//
//            byte[] buffetr=new byte[1024*10];
//            int len=0;
//          while ((len=file_pohto.read(buffetr))>0){
//                outputStream.write(buffetr,0,len);
//          }
//            outputStream.write(boundary.getBytes());
//            outputStream.flush();
//            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            String reslt = IOHelper.ReadString4Stream(inputStream).toString();
            L.i(reslt + "++++++++++");

            outputStream.flush();
            outputStream.close();
            inputStream.close();
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
