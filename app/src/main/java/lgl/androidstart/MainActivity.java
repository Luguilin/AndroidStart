package lgl.androidstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lgl.androidstart.simple.BaseSimple;
import lgl.androidstart.simple.HttpSimple;
import lgl.androidstart.tool.L;

public class MainActivity extends AppCompatActivity {

    BaseSimple mSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mSimple=new GZipSimple();
//        mSimple=new ImageSimple();
        mSimple= new HttpSimple();
        L.e("----:");
//        handler.sendEmptyMessageDelayed(0,2000);


    }
    public void Oncli(View view){
        mSimple.Main(this);
    }
}
