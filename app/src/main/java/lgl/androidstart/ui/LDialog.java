package lgl.androidstart.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lgl.androidstart.R;

/**
 * @author LGL on 2016/9/21.
 * @description
 */
public class LDialog {
    SparseArray<View> mViews;
    private View mConvertView;
    private Dialog alertDialog;

    public Dialog CreateDailog(Context content, View contentView,boolean cancelable) {
        this.mConvertView = contentView;
        mViews = new SparseArray<View>();
        alertDialog = new Dialog(content, R.style.Dialog);
        alertDialog.addContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //alertDialog.setContentView(view);
        if (!cancelable) {
        	alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
		}
        return alertDialog;
    }

    public void setOnclickListener(int id, final DialogInterface.OnClickListener onClickListener) {
        View view = getView(id);
        if (view == null) return;
        view.setOnClickListener(new DialogOnclickListener(onClickListener));
    }

    /**
     * 通过viewID来找到控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View v = mViews.get(viewId);
        if (v == null) {
            v = mConvertView.findViewById(viewId);
            mViews.put(viewId, v);
        }
        return (T) v;
    }
    /**
     * 这是TextView文本
     * @param viewId
     * @param text
     * @return
     */
    public LDialog setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    public void show() {
        if (alertDialog == null) return;
        if (alertDialog.isShowing()) return;
        alertDialog.show();
    }

    class DialogOnclickListener implements View.OnClickListener {
        DialogInterface.OnClickListener onClickListener;

        public DialogOnclickListener(DialogInterface.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(alertDialog, 0);
        }
    }
}
