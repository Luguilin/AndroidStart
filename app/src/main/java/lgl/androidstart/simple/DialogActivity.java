package lgl.androidstart.simple;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import lgl.androidstart.R;
import lgl.androidstart.ui.LDialog;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


    }

    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_normal_layout, null);
        LDialog lDialog = new LDialog();
        lDialog.CreateDailog(this, view, true);
        lDialog.setText(R.id.message, "是否拨打客服电话？").setText(R.id.negativeButton, "拨打")
                .setText(R.id.positiveButton, "取消");
        lDialog.setOnclickListener(R.id.negativeButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//				startActivity(intent);
                dialog.dismiss();
            }
        });
        lDialog.setOnclickListener(R.id.positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        lDialog.show();
    }
}
