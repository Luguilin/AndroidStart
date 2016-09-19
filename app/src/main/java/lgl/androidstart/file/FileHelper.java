package lgl.androidstart.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author LGL on 2016/8/8.
 * @description
 * @permission  WRITE_EXTERNAL_STORAGE
 *
 */
public class FileHelper {
    /**
     * 获得程序缓存路径(没有将创建一个指定文件夹)
     *
     * @param context
     * @param dir_name 缓存路径下的需要创建的文件夹
     * @return
     */
    public static File getDiskCacheDir(Context context, String dir_name) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File file = new File(cachePath + File.separator + dir_name);
        if (file.getParentFile() == null || !file.getParentFile().exists() || !file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        return file;
    }

    /**
     * 根据文件名获得一个空文件
     *
     * @param file_path
     * @param delete    如果文件存在是否需要覆盖   true为需要删除
     * @return
     */
    public static File getFile(String file_path, boolean delete) {
        File file = new File(file_path);
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()|file.getParentFile() == null) {
                    file.getParentFile().mkdirs();// 创建文件夹
                }
                file.createNewFile();// 创建一个新文件
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
     * 根据文件名获得一个空文件
     *
     * @param file
     * @return
     */
    public static File getFile(File file) {
        return getFile(file.getAbsolutePath(), true);
    }

    /**
     * 获得一个空的缓存文件
     *
     * @param context
     * @param cacheDir  要创建在缓存目录下的文件夹
     * @param file_name 文件名 注意要加上后缀名
     * @return
     */
    @Deprecated
    public static File getCacheDirFile(Context context, String cacheDir, String file_name) {

        String path = getDiskCacheDir(context, cacheDir).getAbsolutePath();
        File file = getFile(path + File.separatorChar + file_name, true);
        return file;
    }

    public static String getExternalSdFile(String file_name) {
        return getExternalSdCardPath() + File.separator + file_name;
    }

    /**
     * 获取扩展SD卡存储目录
     * <p/>
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
        File sdCardFile;
        ArrayList<String> devMountList = getDevMountList();

        for (String devMount : devMountList) {
            File file = new File(devMount);

            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "lgl_" + timeStamp);

                if (testWritable.mkdirs()) {//测试写一个文件试试   可以写入说明有权限
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
     *
     * @param context  上下文对象
     * @param fileName 文件名  eg:  index.html   aaa.json
     * @return
     */
    public static String readAssets(Context context, String fileName) {
        String resultString = "";
        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "GB2312");
        } catch (Exception e) {
            Log.e("----assetsException---", e.getMessage());
        }
        return resultString;
    }

    public static long getFileSize(FileInputStream fi) {
        try {
            return fi.getChannel().size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void copyFile(String path1, String path2) {
        File fromFile = new File(path1);
        File toFile = new File(path2);
        boolean rewrite = false;
        if (!fromFile.exists()) {//存在
            return;
        }
        if (!fromFile.isFile()) {//是文件
            return;
        }
        if (!fromFile.canRead()) {//能读
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        try {
            java.io.FileInputStream fosfrom = new java.io.FileInputStream(
                    fromFile);
            java.io.FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); // 将内容写到新文件当中
            }
            fosfrom.close();
            fosto.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
