package lgl.androidstart.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author LGL
 *
 */
public class ViewHolder {

	SparseArray<View> mViews;
	View mConvertView;
	int mPosition;

	public ViewHolder(Context context, int position, int layoutId,
			ViewGroup parent) {
		this.mPosition = position;
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);
	}

	public static ViewHolder get(Context context, int position, int layoutId,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			return new ViewHolder(context, position, layoutId, parent);
		} else {
			return (ViewHolder) convertView.getTag();
		}
	}

	/**
	 * 通过viewID来找到控件
	 * @param viewId
	 * @param <T>
     * @return
     */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View v = mViews.get(viewId);
		if (v == null) {
			v = mConvertView.findViewById(viewId);
			mViews.put(viewId, v);
		}
		return (T) v;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 这是TextView文本
	 * @param viewId
	 * @param text
     * @return
     */
	public ViewHolder setText(int viewId, String text) {
		((TextView) getView(viewId)).setText(text);
		return this;
	}
}
