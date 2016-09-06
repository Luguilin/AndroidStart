package lgl.androidstart.file;

import java.lang.reflect.Field;

/**
 * @author LGL on 2016/8/31.
 * @description
 */
public class ResHelper {
    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     * @param variableName
     * @param c
     * @return  对应资源的ID
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
