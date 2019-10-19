package com.example.yexin.menu6.Thread;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.yexin.menu6.Json.Web_Json;
import com.example.yexin.menu6.Login;
import com.example.yexin.menu6.Public_class.User_public;
import com.example.yexin.menu6.Show;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import org.xutils.x;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DELL on 2019/10/17.
 */

public class Thread_one implements Runnable {
    private HashMap<String,String> map;
    private Activity Main;
    public Thread_one(Activity Main){
        this.Main=Main;
    }
    @Override
    public void run() {
        String url = "http://172.22.70.227:8080/ByteSoft_two/Token";
        RequestParams params = new RequestParams(url);
        // params2 = new RequestParams(URL1);
        //Log.w("WangJ", "运行在这个ok"+params.toString());
        Log.w("WangJ", "运行在这个ok");
        String jsonObject= Web_Json.Login_one(User_public.getUser(),User_public.getUser_str());
        Log.e("Json", jsonObject);
        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部\
        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
        params.setBodyContent(jsonObject);//添加json内容到请求参数里
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                /**
                 * 如果返回的报文为200，没有失效，登入成功，换取长连接
                 * 如果为500，长连接失效，登入失败，提示去重新登入
                 */
                map=Web_Json.getJson(result);
                if(map.get("code").equals("200")){
                    Log.e("yjq_11","长连接没有失效，并且更新了长连接");
                    SharedPreferences preferences = Main.getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("User_str",map.get("token"));
                    editor.commit();
                    User_public.setUser_str(map.get("token"));
                }
                else{
                    Toast.makeText(Main, "身份过期，请重新登入", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = Main.getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("User_flag",false);
                    editor.commit();
                    User_public.setUser_flag(false);
                    Intent intent = new Intent(Main,Login.class);
                    Main.startActivity(intent);
                    Main.finish();
                    Log.e("yjq_12","长连接失效了重新去登入");
                }
                Log.e("yjq_four","新的长连接：code"+map.get("code")+"message"+map.get("message")+"token"+map.get("token"));
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(Login.this, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
                Log.e("yjq1","失败");
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("yjq","取消");
            }
            @Override
            public void onFinished() {
                Log.e("yjq","完成");
            }
        });
    }
}
