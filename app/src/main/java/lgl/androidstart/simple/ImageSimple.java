package lgl.androidstart.simple;

import android.app.Activity;
import android.graphics.Bitmap;

import java.io.File;

import lgl.androidstart.file.FileHelper;
import lgl.androidstart.file.IOHelper;
import lgl.androidstart.image.ImageHelper;
import lgl.androidstart.tool.L;

/**
 * @author LGL on 2016/9/20.
 * @description
 */
public class ImageSimple implements BaseSimple{

    @Override
    public void Main(Activity context) {
        new Thread(new MyRun()).start();
    }
    class MyRun implements Runnable{

        @Override
        public void run() {
            Bitmap bitmap= ImageHelper.getimage(FileHelper.getExternalSdCardPath()+ File.separator+"aaa.jpg");
            L.e(bitmap.getHeight()+"========"+bitmap.getWidth());
            IOHelper.WirteByteArray(ImageHelper.Bitmap2Bytes(bitmap),FileHelper.getExternalSdCardPath()+ File.separator+"aaa2.jpg");
            L.e(bitmap.getHeight()+"=====完成===="+bitmap.getWidth());
        }
    }
}
