package lgl.androidstart.simple;

import android.app.Activity;

import java.io.File;
import java.io.IOException;

import lgl.androidstart.file.FileHelper;
import lgl.androidstart.http.DownloadHelper;
import lgl.androidstart.tool.L;

/**
 * @author LGL on 2016/9/19.
 * @description
 */
public class HttpSimple implements BaseSimple {

    @Override
    public void Main(final Activity context) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HashMap<String,String> parameter=new HashMap<String, String>();
//                parameter.put("phone","1889414");
//                parameter.put("key","378d6498179ff0355703e65dcd224113");
//                L.e("----"+RequestFactory.getPramesString(parameter,"utf-8"));
//                HttpURLConnection connection = RequestFactory.BuildGet(urlstr, parameter);
//                try {
//                    connection.connect();
//                    InputStream inputStream = connection.getInputStream();
//                    L.e("length:" + connection.getContentLength());
////                    String path = FileHelper.getExternalSdCardPath() + File.separator + "aaa/RxJava.html";
////                    L.e(path);
////                    IOHelper.WirteFile(inputStream,path );
//                    String temp = IOHelper.ReadString4Stream(inputStream).toString();
////                    String s = new String(MyGZIP.unZip(temp.getBytes()));
//                    L.e(temp);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();


        File file = FileHelper.getFile(FileHelper.getExternalSdCardPath() + File.separator + "zzz.apk", true);
        DownloadHelper downloadHelper=new DownloadHelper();
        try {
            downloadHelper.download(urlStr, file, new DownloadHelper.DownloadListener() {
                @Override
                public void update(int progress) {
                    L.e(Thread.currentThread().getName()+"======="+progress);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int sss=1;

    public synchronized int getSss() {
        this.sss++;
        return sss-1;
    }



    String urlStr = "http://120.132.13.158:8090/load/luntai/siji.apk";




}
