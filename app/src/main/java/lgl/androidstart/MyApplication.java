package lgl.androidstart;

import android.app.Application;
import android.content.Context;

import lgl.androidstart.tool.T;

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

        T.Instance(this,"");
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

    }

    public static Context getContext() {
        return mContext;
    }
}
