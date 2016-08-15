package com.frame.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.util.Log;

/**
 * Created by lenovo on 2016/8/9.
 */

public class ProListActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prolist);
    }
}

