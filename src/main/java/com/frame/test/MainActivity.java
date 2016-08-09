package com.frame.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by lenovo on 2016/8/9.
 */
public class MainActivity extends Activity  {
    private Button mainBtn01;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        initView();
    }


    private void initView(){
        //得到按钮并设置监听事件
        mainBtn01 = (Button)findViewById(R.id.chosepro);
        mainBtn01.setOnClickListener(new MyButtonListener());

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


