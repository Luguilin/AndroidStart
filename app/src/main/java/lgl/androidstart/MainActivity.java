package lgl.androidstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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


    public void aaaaaa() {
        String updateStr = "http://192.168.1.192:4080/resourceview/ServletAll";
        String boundary = RequestFactory.boundary;
        HashMap<String, String> parems = new LinkedHashMap<>();
        parems.put("action", "updateUserFace");
        parems.put("access_token", token);

        try {
            L.e(updateStr);
            HttpURLConnection connection=RequestFactory.getHttpURLConnection(updateStr, parems);
            connection.connect();

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            HashMap<String, Object> hashMap = new HashMap<>();
            File file= new File("/storage/emulated/0/A.png");
//            L.e("exists====="+file.exists());
            hashMap.put("file",file);
            hashMap.put("user_id", "1");
//            String fName = "-------safdsafdsafdsafdsa\r\n" +
//                    "Content-Disposition: form-data; name=\"user_id\"\r\n\r" +
//                    "\n1\r\n" +
//                    "-------safdsafdsafdsafdsa--";


//            outputStream.writeBytes(fName);

//            RequestFactory.printResponseHeader(connection);
            IOHelper.WirtePremes(outputStream, hashMap);

//            int len;
//            FileInputStream fileReader = new FileInputStream("/storage/emulated/0/b.jpg");
//            byte[] buffer = new byte[1024 * 80];
//            while ((len = fileReader.read()) > 0) {
//                outputStream.write(buffer, 0, len);
//            }

//            outputStream.write("------WebKitFormBoundaryvNHP2OD2375X2MQy--".getBytes());
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
