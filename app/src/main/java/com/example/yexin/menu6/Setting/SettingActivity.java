package com.example.yexin.menu6.Setting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yexin.menu6.Login;
import com.example.yexin.menu6.Public_class.User_public;
import com.example.yexin.menu6.R;
import com.example.yexin.menu6.Show;

public class SettingActivity extends Activity {
    private ImageView iv_back;
    private ImageView iv_forward_one;
    private ImageView iv_forward_two;
    private ImageView iv_forward_three;
    private ImageView iv_forward_four;
    private ImageView iv_forward_five;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Initial();
    }

    //初始化控件
    public void Initial(){
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_forward_one = (ImageView)findViewById(R.id.iv_forward_one);
        iv_forward_two = (ImageView)findViewById(R.id.iv_forward_two);
        iv_forward_three = (ImageView)findViewById(R.id.iv_forward_three);
        iv_forward_four = (ImageView)findViewById(R.id.iv_forward_four);
        iv_forward_five = (ImageView)findViewById(R.id.iv_forward_five);
    }

    //设置单击事件
    public void onClick(View view){
        switch (view.getId()){
            //点击界面返回键返回上一级
            case R.id.iv_back:
                break;

            //点击安全设置标签跳转安全设置界面
            case R.id.rl_security:
                Intent intent_one = new Intent(SettingActivity.this,SecurityActivity.class);
                startActivity(intent_one);
                finish();
                break;

            //点击通用标签跳转通用界面
            case R.id.rl_common:
                Intent intent_two = new Intent(SettingActivity.this,CommonActivity.class);
                startActivity(intent_two);
                finish();
                break;

            //点击关于标签跳转关于界面
            case R.id.rl_about:
                Intent intent_three = new Intent(SettingActivity.this,AboutActivity.class);
                startActivity(intent_three);
                finish();
                break;

            //点击切换账号标签跳转账号切换界面
            case R.id.rl_account_switch:
                Intent intent_four = new Intent(SettingActivity.this,AccountSwitchActivity.class);
                startActivity(intent_four);
                finish();
                break;

            //退出登录
            case R.id.rl_logout:
                /**
                 * 点击退出登入，当前的登入状态改变，并且
                 */
                SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("User_flag",false);
                editor.commit();
                User_public.setUser_flag(false);
                Log.e("yjq30","退出登入了，状态改变为未登入，并且去重新登入");
                Intent intent = new Intent(SettingActivity.this,Login.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
