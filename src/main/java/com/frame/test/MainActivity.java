package com.frame.test;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import android.util.Log;
import com.frame.utils.AppInfo;
import com.frame.utils.AppInfoProvider;
import com.frame.utils.Programe;
import com.frame.utils.ProcessInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/9.
 */
public class MainActivity extends Activity  {

    private static final String LOG_TAG = "frame-" + MainActivity.class.getSimpleName();
    private List<AppInfo> appInfoList;
    private Button mainBtnConfirm;
    private ListView listViewapp;
    private ProcessInfo processInfo;
    private UpdateReceiver receiver;
    private static final int TIMEOUT = 20000;
    private boolean isServiceStop = false;
    private int pid, uid;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainpage);
        initView();
    }


    private void initView(){
        //初始化
        processInfo = new ProcessInfo();
        mainBtnConfirm = (Button)findViewById(R.id.confirm);
        listViewapp=(ListView)findViewById(R.id.appList);


        listViewapp.setAdapter(new ListAdapter() );


        mainBtnConfirm.setOnClickListener(new MyButtonListener());
        listViewapp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RadioButton rdBtn = (RadioButton) ((LinearLayout) view).getChildAt(2);
                rdBtn.setChecked(true);

            }
        });

    }



    public class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(LOG_TAG,"BroadcastReceiver: "+intent.getStringExtra("msg"));
            isServiceStop = intent.getExtras().getBoolean("isServiceStop");
            if (isServiceStop) {
                mainBtnConfirm.setText(getString(R.string.confirm_pro));
            }
        }
    }

    @Override
    protected void onStart() {
        Log.i(LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        if (isServiceStop) {
            mainBtnConfirm.setText(getString(R.string.confirm_pro));
        }
    }

    /**
     * wait for test application started.
     *
     * @param packageName
     *            package name of test application
     */
    private void waitForAppStart(String packageName) {
        Log.d(LOG_TAG, "wait for app start");
        boolean isProcessStarted = false;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + TIMEOUT) {
            pid = processInfo.getPidByPackageName(getBaseContext(), packageName);
            if (pid != 0) {
                isProcessStarted = true;
                break;
            }
            if (isProcessStarted) {
                break;
            }
        }
    }




    class MyButtonListener implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            ListAdapter adapter = (ListAdapter) listViewapp.getAdapter();
            if (adapter.checkedProg != null) {
                String packageName = adapter.checkedProg.getPackageName();
                String processName = adapter.checkedProg.getProcessName();
                intent = getPackageManager().getLaunchIntentForPackage(packageName);
                String startActivity = "";
                Log.i(LOG_TAG, "packageName: "+packageName);

                // clear logcat
                try {
                    Runtime.getRuntime().exec("logcat -c");

                } catch (IOException e) {
                    Log.d(LOG_TAG, e.getMessage());
                }
                try {
                    startActivity = intent.resolveActivity(getPackageManager()).getShortClassName();
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, getString(R.string.cannot_start_app), Toast.LENGTH_LONG).show();
                    return;
                }
//                waitForAppStart(packageName);
//                monitorService.putExtra("processName", processName);
//                monitorService.putExtra("pid", pid);
//                monitorService.putExtra("uid", uid);
//                monitorService.putExtra("packageName", packageName);
//                monitorService.putExtra("startActivity", startActivity);
//                startService(monitorService);
//                isServiceStop = false;
//                btnTest.setText(getString(R.string.stop_test));
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.choose_app_toast), Toast.LENGTH_LONG).show();
            }


        }

    }
    private class ListAdapter extends BaseAdapter {
        List<Programe> programes;
        Programe checkedProg;
        int lastCheckedPosition = -1;

        public ListAdapter() {
            programes = processInfo.getAllPackages(getBaseContext());
        }

        @Override
        public int getCount() {
            return programes.size();
        }

        @Override
        public Object getItem(int position) {
            return programes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Programe pr = (Programe) programes.get(position);
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.listviewitem, parent, false);
            Viewholder holder = (Viewholder) convertView.getTag();
            if (holder == null) {
                holder = new Viewholder();
                convertView.setTag(holder);
                holder.imgViAppIcon = (ImageView) convertView.findViewById(R.id.proicon);
                holder.txtAppName = (TextView) convertView.findViewById(R.id.proname);
                holder.rdoBtnApp = (RadioButton) convertView.findViewById(R.id.rb);
                holder.rdoBtnApp.setFocusable(false);
                holder.rdoBtnApp.setOnCheckedChangeListener(checkedChangeListener);
            }
            holder.imgViAppIcon.setImageDrawable(pr.getIcon());
            holder.txtAppName.setText(pr.getProcessName());
            holder.rdoBtnApp.setId(position);
            holder.rdoBtnApp.setChecked(checkedProg != null && getItem(position) == checkedProg);
            return convertView;
        }

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    final int checkedPosition = buttonView.getId();
                    if (lastCheckedPosition != -1) {
                        RadioButton tempButton = (RadioButton) findViewById(lastCheckedPosition);
                        if ((tempButton != null) && (lastCheckedPosition != checkedPosition)) {
                            tempButton.setChecked(false);
                        }
                    }
                    checkedProg = programes.get(checkedPosition);
                    lastCheckedPosition = checkedPosition;
                }
            }
        };
    }
    static class Viewholder {
        TextView txtAppName;
        ImageView imgViAppIcon;
        RadioButton rdoBtnApp;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }




}


