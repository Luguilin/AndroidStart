package lgl.androidstart.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 作者: LGL on 2016/8/8. 邮箱: 468577977@qq.com
 */
public class FileHelper {
	/**
	 * 获得程序缓存路径
	 *
	 * @param context
	 * @param file_name 缓存路径下的需要创建的文件夹
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String file_name) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		File file = new File(cachePath + File.separator + file_name);
		if (file.getParentFile() == null || !file.getParentFile().exists() || !file.exists()) {
			file.mkdirs();// 创建文件夹
		}
		return file;
	}

	/**
	 * 根据文件名获得一个空文件
	 * 
	 * @param file_path
	 * @param delete  如果文件存在是否需要覆盖   true为需要删除
	 * @return
	 */
	public static File getFile(String file_path,boolean delete) {
		File file = null;
		try {
			file = new File(file_path);
			if (file.getParentFile() == null || !file.getParentFile().exists()) {
				file.getParentFile().mkdirs();// 创建文件夹
				if (!file.exists()) {
					file.createNewFile();// 创建一个新文件
				}
			} else if (delete) {//需要覆盖
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 能够保证文件或文件夹的存在
	 * @param file
	 * @return
	 */
	public static boolean existFile(File file){
		if (file.exists())return true;
		if (file.isDirectory()&&!file.exists()) {
			file.mkdirs();
		}else if (!file.exists()) {
			try {
				file.mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}
	/**
	 * 获得一个空的缓存文件
	 * 
	 * @param context
	 * @param cacheDir 要创建在缓存目录下的文件夹
	 * @param file_name 文件名 注意要加上后缀名
	 * @return
	 */
	@Deprecated
	public static File getCacheDirFile(Context context, String cacheDir, String file_name) {

		String path = getDiskCacheDir(context, cacheDir).getAbsolutePath();
		File file = getFile(path + File.separatorChar + file_name,true);
		return file;
	}

	/**
	 * 获取扩展SD卡存储目录
	 * 
	 * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录 否则：返回内置SD卡目录
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getExternalSdCardPath() {

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
			File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
			return sdCardFile.getAbsolutePath();
		}

		String path = null;

		File sdCardFile = null;

		ArrayList<String> devMountList = getDevMountList();

		for (String devMount : devMountList) {
			File file = new File(devMount);

			if (file.isDirectory() && file.canWrite()) {
				path = file.getAbsolutePath();

				String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
				File testWritable = new File(path, "lgl_" + timeStamp);

				if (testWritable.mkdirs()) {
					testWritable.delete();
				} else {
					path = null;
				}
			}
		}

		if (path != null) {
			sdCardFile = new File(path);
			return sdCardFile.getAbsolutePath();
		}
		return "";
	}

	/**
	 * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
	 * 这种获取是准确的    文件浏览器都是从这种文件中挂在的
	 * 
	 * @return
	 */
	private static ArrayList<String> getDevMountList() {
		String[] toSearch = IOHelper.ReadString4File("/system/etc/vold.fstab").toString().split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for (int i = 0; i < toSearch.length; i++) {
			if (toSearch[i].contains("dev_mount")) {
				if (new File(toSearch[i + 2]).exists()) {
					out.add(toSearch[i + 2]);
				}
			}
		}
		return out;
	}

	/**
	 * 读取Assets文件
	 * @param context 上下文对象
	 * @param fileName 文件名  eg:  index.html   aaa.json
     * @return
     */
	public static String readAssets(Context context,  String fileName){
		String resultString="";
		try {
			InputStream inputStream=context.getResources().getAssets().open(fileName);
			byte[] buffer=new byte[inputStream.available()];
			inputStream.read(buffer);
			resultString=new String(buffer,"GB2312");
		} catch (Exception e) {
			Log.e("----assetsException---",e.getMessage());
		}
		return resultString;
	}
}
