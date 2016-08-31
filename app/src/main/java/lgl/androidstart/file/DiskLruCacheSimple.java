package lgl.androidstart.file;

import android.app.Application;

import java.io.File;
import java.io.IOException;

import lgl.androidstart.tool.AppHelper;


/**
 * http://www.tuicool.com/articles/JB7RNj
 * @author LGL
 *
 */
public class DiskLruCacheSimple extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		OpenDiskLruCache();
	}

	private static DiskLruCache mDiskLruCache = null;

	void OpenDiskLruCache() {
		if (mDiskLruCache == null) {
			File cacheDir = FileHelper.getDiskCacheDir(getApplicationContext(), "bitmap");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			try {
				long size = 10 * 1024 * 1024;
				mDiskLruCache = DiskLruCache.open(cacheDir, AppHelper.getAppVersion(getApplicationContext()), 1, size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static DiskLruCache getDiskLruCache() {
		return mDiskLruCache;
	}
}
