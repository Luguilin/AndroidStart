package lgl.androidstart.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DrawableFactory {

	@SuppressWarnings("unused")
	private static Drawable decodeFile(Context context, File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 400;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
			return bd;
		} catch (FileNotFoundException e) {

		}
		return null;
	}

	public static Drawable createThumbnails(Context context, String url) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(url, options); // 此时返回bm为空

		options.inJustDecodeBounds = false;

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可

		int be = (int) (options.outHeight / (float) 2);
		if (be <= 0)
			be = 1;

		options.inSampleSize = be;

		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

		bitmap = BitmapFactory.decodeFile(url, options);
		// int w = bitmap.getWidth();
		// int h = bitmap.getHeight();
		ImageView iv = new ImageView(context);
		iv.setImageBitmap(bitmap);

		return iv.getDrawable();
	}
}
