package com.frame.test;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.frame.utils.getCpuInfo.getProcessCpuRate;
import static java.lang.Runtime.getRuntime;

public class HelloAndroidActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button bt1=(Button)findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener(){
//            @Override
            public  void  onClick(View v){
                TextView textv =(TextView)findViewById(R.id.textView1);
                textv.setMovementMethod(new ScrollingMovementMethod());
                Toast.makeText(HelloAndroidActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
                textv.setText("asdasdasd");

                String Result;
                Process p= null;
                try {
//                    p = getRuntime().exec("top -d 1 -n 1");
                    p = getRuntime().exec("ps");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedReader br=new BufferedReader(new InputStreamReader
                        (p.getInputStream ()));
                try {
                    while((Result=br.readLine())!=null)
                    {

                        if(Result.trim().length()<1){
                            continue;
                        }else{
                            String sa=Result.toString();
                            textv.append(sa+"asd");

                            //                            String[] CPUusr = Result.split("%");
//                            textv.append("USER:"+CPUusr[0]+"\n");
//                            String[] CPUusage = CPUusr[0].split("User");
//                            String[] SYSusage = CPUusr[1].split("System");
//                            textv.append("CPU:"+CPUusage[1].trim()+" length:"+CPUusage[1].trim().length()+"\n");
//                            textv.append("SYS:"+SYSusage[1].trim()+" length:"+SYSusage[1].trim().length()+"\n");
//                            textv.append(Result+"\n");
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                float f=getProcessCpuRate();
                String s=String.valueOf(f);
                textv.append(s+"   \n");






            }
        });
    }





}

