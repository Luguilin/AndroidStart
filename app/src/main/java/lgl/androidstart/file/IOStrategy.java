package lgl.androidstart.file;

import java.io.File;
import java.io.InputStream;

/**
 * @author LGL on 2016/9/20.
 * @description
 */
public interface IOStrategy {
    InputStream GetInputStream();
    File WirteFile(InputStream inputStream, String path);
}
