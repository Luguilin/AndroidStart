package lgl.androidstart.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/***
 * 图片处理，类似于布局文件里边对图片信息的缩放处理 （fitcenter）
 * 
 * 
 * @author LakeTony
 * @Time 2012/08/20
 * @comment 位图比例缩放支持类
 * 
 * */
public class BitmapHelp {
	static Matrix matrix = null;

	// 设置图片指定大小
	@Deprecated
	public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
		// TODO 紧根据宽度与高度缩放图片```用于头像内容 // 图片源Bitmap bm
		if (bm == null) {
			return bm;
		}
		matrix = null;
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newWidth1 = newWidth;
		int newHeight1 = newHeight;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth1) / width;
		float scaleHeight = ((float) newHeight1) / height;
		// 取得想要缩放的matrix参数
		matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		try {
			bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		} catch (Exception e) {
			bm = null;
		}
		return bm;
	}

	@Deprecated
	public static Bitmap scaleImgWithWidth(Bitmap bm, int newWidth) {
		// TODO 紧根据宽度缩放图片```用于信息内容 // 图片源Bitmap bm
		if (bm == null) {
			return bm;
		}
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newWidth1 = newWidth;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth1) / width;
		// 取得想要缩放的matrix参数
		matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		// 得到新的图片
		try {
			bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		} catch (OutOfMemoryError e) {
			bm = null;
		}

		return bm;
	}

	@Deprecated
	public static Bitmap scaleImgWithHeight(Bitmap bm, int newHeight) {
		// TODO 紧根据宽度缩放图片```用于信息内容 // 图片源Bitmap bm
		if (bm == null) {
			return bm;
		}
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		int newHeight1 = newHeight;
		// 计算缩放比例
		float scale = ((float) newHeight1) / height;
		// 取得想要缩放的matrix参数
		matrix = new Matrix();
		matrix.postScale(scale, scale);
		// 得到新的图片
		try {
			bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		} catch (OutOfMemoryError e) {
			bm = null;
		}

		return bm;
	}
	@Deprecated
	static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable); // drawable转换成bitmap
		Matrix matrix = new Matrix(); // 创建操作图片用的Matrix对象
		float scaleWidth = ((float) w / width); // 计算缩放比例
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight); // 设置缩放比例
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true); // 建立新的bitmap，其内容是对原bitmap的缩放后的图
		return new BitmapDrawable(newbmp); // 把bitmap转换成drawable并返回
	}

	@Deprecated
	static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
	{
		int width = drawable.getIntrinsicWidth(); // 取drawable的长宽
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565; // 取drawable的颜色格式
		Bitmap bitmap = Bitmap.createBitmap(width, height, config); // 建立对应bitmap
		Canvas canvas = new Canvas(bitmap); // 建立对应bitmap的画布
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas); // 把drawable内容画到画布中
		return bitmap;
	}

}
