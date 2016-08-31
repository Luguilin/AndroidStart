package lgl.androidstart;

import android.app.Application;
import android.content.Context;

/**
 * 作者: LGL on 2016/8/31.
 * 邮箱: 468577977@qq.com
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
