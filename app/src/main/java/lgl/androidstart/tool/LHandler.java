package lgl.androidstart.tool;

import android.content.Context;
import android.os.Handler;

/**
 * Created by LGL on 2017/1/3.
 */

public class LHandler extends Handler {

    private static LHandler mHandler = null;
    private LHandler(){}
    public static synchronized void initHandler(Context context) throws Exception {
        if (!Thread.currentThread().getName().contains("main")){
            throw new Exception("必须在主线程初始化");
        }
        if (mHandler == null) mHandler = new LHandler();
    }

    public static void switchMainThread(Runnable runnable) {
        mHandler.post(runnable);
    }
}
