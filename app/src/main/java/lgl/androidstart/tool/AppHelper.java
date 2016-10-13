package lgl.androidstart.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author LGL
 * @description
 */
public class AppHelper {

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("获取版本信息", "异常", e);
        }
        return versionName;
    }

    /**
     * 获得安卓系统版本
     * @return
     */
    public static String getAndroidVersion(){
        String remark = android.os.Build.VERSION.RELEASE;
        return remark;
    }

    // <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    // 需要添加权限
    @SuppressWarnings("static-access")
    public static String getDeviceId(Context Context) {
        TelephonyManager tm = (TelephonyManager) Context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();// 所有的设备都可以返回
    }

    // 获取程序版本号
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


//	long memory=Runtime.getRuntime().maxMemory();//获取系统分配的最大内存      单位是   KB
//
//	int memory1=((ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();   单位是  MB


    /**
     * 拨打电话
     * @Promission CALL_PHONE
     * @param number 电话号码
     */
    public static void Call(Context context, String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        // url:统一资源定位符
        // uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + number));
        // 开启系统拨号器
        context.startActivity(intent);
    }
}
