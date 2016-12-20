package lgl.androidstart.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import lgl.androidstart.R;

/**
 * Created by LGL on 2016/12/6.
 */

public class LLoadingDialog {

    private Dialog loadingDialog;

    public LLoadingDialog(Context context, @LayoutRes int layout_id) {
        loadingDialog = new Dialog(context, R.style.LoadingDialog);
        loadingDialog.addContentView(LayoutInflater.from(context).inflate(layout_id, null, false), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void ShowLoading(boolean cancelable) {
        if (loadingDialog == null) return;
        if (loadingDialog.isShowing()) loadingDialog.dismiss();
        setCancelable(cancelable);
        loadingDialog.show();
    }

    private void setCancelable(boolean cancelable) {
        if (!cancelable && loadingDialog != null) {
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setCancelable(false);
        } else if (loadingDialog != null) {
            loadingDialog.setCanceledOnTouchOutside(true);
            loadingDialog.setCancelable(true);
        }
    }

    public void ShowLoading() {
        if (loadingDialog == null) return;
        if (loadingDialog.isShowing()) loadingDialog.dismiss();
        loadingDialog.show();
    }

    public void DismissLoading() {
        if (loadingDialog == null) return;
        loadingDialog.dismiss();
    }
}
