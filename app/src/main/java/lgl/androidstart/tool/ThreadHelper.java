package lgl.androidstart.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadHelper {

    ExecutorService mExecutorService;

    public void ThreadHelper() {
        mExecutorService = Executors.newFixedThreadPool(5);
    }
}
