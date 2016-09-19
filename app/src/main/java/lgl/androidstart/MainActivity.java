package lgl.androidstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import lgl.androidstart.simple.BaseSimple;
import lgl.androidstart.simple.GZipSimple;

public class MainActivity extends AppCompatActivity {

    BaseSimple mSimple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSimple=new GZipSimple();



        mSimple.Main();
    }
}
