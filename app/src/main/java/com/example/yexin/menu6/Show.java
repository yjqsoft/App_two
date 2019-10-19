package com.example.yexin.menu6;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.yexin.menu6.Public_class.User_public;

/**
 * Created by DELL on 2019/10/17.
 */

public class Show extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
        boolean isFirstIn = preferences.getBoolean("isFirst", true);
        if(isFirstIn){
            Log.e("yjq_fire","第一次登入,跳转登入界面");
            Log.e("yjq_13","创建xml文件");
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isFirst",false);
            editor.putBoolean("User_flag",false);
            editor.putString("User",null);
            editor.putString("Icon",null);
            editor.putString("User_str",null);
            editor.putString("Icon_time",null);
            editor.putString("User_n",null);
            editor.putString("User_qian",null);
            editor.putInt("User_level",0);
            editor.commit();
            User_public.setFirst(false); //不是后台登入
            Intent intent = new Intent(Show.this,Login.class);
            startActivity(intent);
            finish();
        }
        else{
            User_public.setUser(preferences.getString("User",null));
            User_public.setUser_flag(preferences.getBoolean("User_flag",false));
            User_public.setUser_str(preferences.getString("User_str",null));
            if(User_public.isUser_flag()){
                Log.e("yqj_21","不是第一次登入,并且在登入状态");
                User_public.setFirst(true);  //存在后台登入
                Intent intent = new Intent(Show.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Log.e("yjq_22","不是第一次登入，并且不在登入状态");
                User_public.setFirst(false); //不是后台登入
                Intent intent = new Intent(Show.this,Login.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
