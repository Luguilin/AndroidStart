package lgl.androidstart.ui;

import android.app.Activity;
import android.app.Dialog;

import lgl.androidstart.R;

/**
 * Created by LGL on 2016/11/1.
 */

public class LLoadingDialog {

    Dialog dialog;
    public void showLoadingDialog(Activity activity){
        dialog = new Dialog(activity, R.style.dialog);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }
    public void dismissLoadinDialog(){
        if (dialog!=null)if (dialog.isShowing())dialog.dismiss();
    }

}
