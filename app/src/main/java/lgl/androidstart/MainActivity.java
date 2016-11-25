package lgl.androidstart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import lgl.androidstart.simple.BaseSimple;
import lgl.androidstart.simple.HttpSimple;

public class MainActivity extends AppCompatActivity {

    BaseSimple mSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mSimple=new GZipSimple();
//        mSimple=new ImageSimple();
        mSimple= new HttpSimple();

//        handler.sendEmptyMessageDelayed(0,2000);

        mSimple.Main();


    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            throw new RuntimeException();
        }
    };
}
