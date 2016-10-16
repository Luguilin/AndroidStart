package lgl.androidstart.file;

import java.io.File;
import java.io.InputStream;

/**
 * @author LGL on 2016/9/20.
 * @description  策略模式+简单工厂       IO上下文
 */
public class LIO {
    IOStrategy ioStrategy;

    public static String GZIP="GZIP";
    public static String FILE="File";
    public LIO(String ioType) {
        switch (ioType){
//            case GZIP:
//                ioStrategy=new GzipIOHelper()
        }
    }

    public InputStream GetInputStream(){
        return ioStrategy.GetInputStream();
    }

    public File WirteFile(InputStream inputStream, String path){
        return ioStrategy.WirteFile(inputStream, path);
    }
}
