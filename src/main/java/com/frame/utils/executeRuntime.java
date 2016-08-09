package com.frame.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static android.R.attr.process;

/**
 * Created by lenovo on 2016/8/9.
 */
public class executeRuntime {

    
    private executeRuntime(){}
     /**
      * 获取实例
      *
      * @Author: Frame
      * @Data:   2016/8/9 10:42
      * @Param:  
      * 
      */
    public static executeRuntime custom(){
        return new executeRuntime();

    }

    private String cmd="";

    public String getCmd() {
        return cmd;
    }

    public executeRuntime setCmd(String cmd) {
        this.cmd = cmd;
        return this;
    }
     /**
      * 执行cmd 返回执行结果 String
      *
      * @Author: Frame
      * @Data:   2016/8/9 11:29
      * @Param:
      *
      */

    public  String executeCmd(){
        String outcomes="";
        try{
            Process p;
            p = Runtime.getRuntime().exec(this.getCmd());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                outcomes += line + "\n";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return outcomes;
    }

    public  String executeCmd(String cmd){
        String outcomes="";
        this.setCmd("cmd");
        try{
            Process p;
            p = Runtime.getRuntime().exec(this.getCmd());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                outcomes += line + "\n";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return outcomes;
    }

}
