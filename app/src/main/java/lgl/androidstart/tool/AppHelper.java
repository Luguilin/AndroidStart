package lgl.androidstart.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

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

	// 获取当前应用的版本号：
	private String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
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

}
