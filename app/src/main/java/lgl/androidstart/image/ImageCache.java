package lgl.androidstart.image;

import android.graphics.Bitmap;

public interface ImageCache {
    Bitmap get(String url);

    void put(String key,Bitmap bitmap);
}
