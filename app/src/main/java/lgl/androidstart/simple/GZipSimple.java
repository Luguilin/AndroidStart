package lgl.androidstart.simple;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import lgl.androidstart.file.FileHelper;
import lgl.androidstart.file.GzipIOHelper;
import lgl.androidstart.file.IOHelper;
import lgl.androidstart.http.RequestFactory;
import lgl.androidstart.tool.L;
import lgl.androidstart.tool.StringHelper;

/**
 * @author LGL on 2016/9/19.
 * @description
 */
public class GZipSimple implements BaseSimple{
    @Override
    public void Main(Activity context) {
        new Thread(new Runnable() {
            @Override
            public void run() {

//                getGzip4Net();

                ConvertGz3Html();
            }
        }).start();
    }

    private void ConvertGz3Html(){
        GzipIOHelper.UnGzip(FileHelper.getExternalSdFile("a.gz"),FileHelper.getExternalSdFile(StringHelper.getName4FileName("c.txt.gz")));
    }


    private void getGzip4Net(){
        String urlStr="http://tool.chinaz.com/";
        HttpURLConnection httpURLConnection=RequestFactory.BuildGet(urlStr, null);
        try {
            if (RequestFactory.isGzip(httpURLConnection)) {
                L.e("isGzip");
            }
            InputStream gzipInputStream= GzipIOHelper.isGzip(httpURLConnection.getInputStream());
            IOHelper.WirteFile(gzipInputStream, FileHelper.getFile(FileHelper.getExternalSdFile("bbb.html"),true).getAbsolutePath());
//          IOHelper.WirteFile(inputStream, FileHelper.getFile(FileHelper.getExternalSdCardFile("aaa.gz"),true).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
