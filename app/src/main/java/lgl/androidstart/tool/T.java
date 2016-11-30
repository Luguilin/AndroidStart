package lgl.androidstart.tool;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * @description Toast统一管理类
 * 如果对界面或者显示位置有改动请另行使用自己的Toast
 */
public class T {

    private static Toast mToast;

    public static Toast Instance(Context context, CharSequence message) {
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        return mToast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        mToast.setText(message);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(@StringRes int message) {
        mToast.setText(message);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        mToast.setText(message);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void cancel() {
        mToast.cancel();
    }
}