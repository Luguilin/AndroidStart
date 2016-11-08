package lgl.androidstart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lgl.androidstart.simple.BaseSimple;
import lgl.androidstart.tool.L;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    BaseSimple mSimple;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
//        mSimple=new GZipSimple();
//        mSimple=new ImageSimple();

//        handler.sendEmptyMessageDelayed(0,2000);

//        mSimple.Main();

        String createSql="insert into user(create_time,user_name,sex,user_face,user_role,true_name,user_id,user_phone,status,user_company,user_company_id)\n" +
                "values(?,?,?,?,?,?,?,?,?,?,?);";

    }
    public void BtnClick(View view){

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSwipeRefreshLayout.setRefreshing(true);
        }
    };

    @Override
    public void onRefresh() {
        L.e("=========");
    }
}
