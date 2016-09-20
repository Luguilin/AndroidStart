package lgl.androidstart.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lgl.androidstart.file.DiskLruCache;
import lgl.androidstart.file.FileHelper;
import lgl.androidstart.tool.AppHelper;
import lgl.androidstart.tool.EncryptUtils;

/**
 * @author LGL on 2016/9/20.
 * @description
 */
public class LruDiskCache implements ImageCache {

    private static DiskLruCache mDiskLruCache;

    public static DiskLruCache OpenDiskLruCache(Context context) {
        if (mDiskLruCache == null) {
            File cacheDir = FileHelper.getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            try {
                long size = 100 * 1024 * 1024;//100M
                mDiskLruCache = DiskLruCache.open(cacheDir, AppHelper.getAppVersion(context), 1, size);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mDiskLruCache;
    }

    @Override
    public Bitmap get(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(EncryptUtils.toMD5(key));
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(String key, Bitmap bitmap) {
        if (bitmap == null) return;
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(EncryptUtils.toMD5(key));
            OutputStream outputStream = editor.newOutputStream(0);
            outputStream.write(ImageHelper.Bitmap2Bytes(bitmap));
            outputStream.flush();
            outputStream.close();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
//            editor.abort();
        }
    }
}
