package com.frame.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.util.Log;
import android.widget.ListView;
import com.frame.utils.AppInfo;
import com.frame.utils.AppInfoProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/9.
 */
public class MainActivity extends Activity  {

    private static final String LOG_TAG = "frame-" + MainActivity.class.getSimpleName();
    private List<AppInfo> appInfoList;
    private Button mainBtn01;
    private ListView listViewapp;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainpage);
        initView();
    }


    private void initView(){
        AppInfoProvider ap=new AppInfoProvider(getBaseContext());

        List<AppInfo> listapp=ap.getAllApps();
        List<String> l=new ArrayList<String>();
        l.add("begin");

        for (AppInfo ai:listapp
             ) {
            l.add(ai.getPackageName());
        }
        Log.i(LOG_TAG,l)

        //得到按钮并设置监听事件
//        mainBtn01 = (Button)findViewById(R.id.chosepro);
        listViewapp=(ListView)findViewById(R.id.appList);
        listViewapp.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,l));


//        mainBtn01.setOnClickListener(new MyButtonListener());

    }



    class MyButtonListener implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ProListActivity.class);
            MainActivity.this.startActivity(intent);
        }

    }




    }


