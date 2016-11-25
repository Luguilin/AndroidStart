package lgl.androidstart.simple;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import lgl.androidstart.R;
import lgl.androidstart.ui.LDialog;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        LDialog lDialog=new LDialog();
        lDialog.CreateDailog(this,R.layout.dialog_normal_layout,false);
        lDialog.setOnclickListener(R.id.positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        lDialog.show();

    }
}
