package lgl.androidstart.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
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

    public LDialog() {
    }

    public Dialog CreateDailog(Context context, @LayoutRes int layoutId, boolean cancelable) {
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, null, false);
        mViews = new SparseArray<View>();
        alertDialog = new Dialog(context, R.style.Dialog);
        alertDialog.addContentView(mConvertView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //alertDialog.setContentView(view);
        if (!cancelable) {
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
        }
        setOnclickListener(R.id.negativeButton, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alertDialog;
    }

    public void setDismisListener(DialogInterface.OnDismissListener dismisListener){
        if (dismisListener!=null)
            alertDialog.setOnDismissListener(dismisListener);
    }

    public Dialog getDialog() {
        return alertDialog;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public LDialog setOnclickListener(int id, final DialogInterface.OnClickListener onClickListener) {
        View view = getView(id);
        if (view != null)
            view.setOnClickListener(new DialogOnclickListener(onClickListener));
        return this;
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
     *
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

    public void dismiss() {
        if (alertDialog == null) return;
        if (alertDialog.isShowing()) alertDialog.dismiss();
    }


    /**
     * Dialog中点击事件的包装类
     */
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
