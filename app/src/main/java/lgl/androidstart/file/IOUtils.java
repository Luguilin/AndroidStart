package lgl.androidstart.file;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by LGL on 2016/11/25.
 */

public class IOUtils {
    public static  void Close(Closeable closeable){
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
