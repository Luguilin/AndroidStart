package lgl.androidstart.ui;

import android.app.Activity;
import android.app.Dialog;

import lgl.androidstart.R;

/**
 * Created by LGL on 2016/11/1.
 */

public class LLoadingDialog {

    public static void showLoadingDialog(Activity activity){
        Dialog dialog = new Dialog(activity, R.style.dialog);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
