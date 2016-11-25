package lgl.androidstart.tool;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;

public class PopupHelper {

	public static PopupWindow getPop(Context context, int layoutId) {
		PopupWindow popupWindow = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		View popupGridView = LayoutInflater.from(context).inflate(layoutId, null);
		popupWindow.setContentView(popupGridView);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		return popupWindow;
	}
}
