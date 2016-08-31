package lgl.androidstart.tool;

import android.content.Context;
import android.content.SharedPreferences;

import lgl.androidstart.MyApplication;

/**
 * 作者: LGL on 2016/8/31.
 * 邮箱: 468577977@qq.com
 */
public class SharedPreHelper {
    public static final String SharedPre_NAME = "config";
    public static final String USE_NAME = "userName";
    public static final String USE_PWD = "userPwd";
    public static final String USER_ID = "userId";

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    static Context mContext;

    public SharedPreHelper() {
        getSharedPrefe();
    }

    public static SharedPreferences getSharedPrefe() {
        if (mSharedPreferences == null)
            mSharedPreferences = MyApplication.getContext().getSharedPreferences(SharedPre_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences;
    }

    public static SharedPreferences.Editor getmEditor() {
        if (mEditor == null) mEditor = getSharedPrefe().edit();
        return mEditor;
    }

    public static void save(String key, String values) {
        getmEditor().putString(key, values);
    }

    public static String read(String key) {
        return getSharedPrefe().getString(key, "");
    }
}
