package lgl.androidstart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lgl.androidstart.simple.BaseSimple;

public class MainActivity extends AppCompatActivity {

    BaseSimple mSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mSimple=new GZipSimple();
//        mSimple=new ImageSimple();

//        handler.sendEmptyMessageDelayed(0,2000);

//        mSimple.Main();
    }
    public void BtnClick(View view){

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            throw new RuntimeException();
        }
    };
}
