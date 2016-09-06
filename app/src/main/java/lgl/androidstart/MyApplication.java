package lgl.androidstart;

import android.app.Application;
import android.content.Context;
/**
 * @author LGL on 2016/8/31.
 * @description
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
