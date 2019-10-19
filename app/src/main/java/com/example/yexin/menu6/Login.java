package com.example.yexin.menu6;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yexin.menu6.Json.Web_Json;
import com.example.yexin.menu6.Public_class.User_public;
import com.example.yexin.menu6.SharedPresences.Share;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

public class Login extends Activity {
    private TextView tv_register;
    private EditText text_username;
    private EditText text_password;
    private Button button_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Layout_init();
    }
    public void Layout_init(){
        tv_register = (TextView)findViewById(R.id.tv_register);
        text_username=(EditText)findViewById(R.id.et_username);
        text_password=(EditText)findViewById(R.id.et_password);
        button_submit=(Button)findViewById(R.id.btn_login);
        button_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(text_username.getText().toString())
                        && !TextUtils.isEmpty(text_password.getText().toString())) {
                    Log.e("WangJ", "都不空");
                    /**
                     * 点击登入，账号密码都不为空的时候，发起网络请求
                     */
                    String url = "http://172.22.70.227:8080/ByteSoft_two/Login";
                    RequestParams params = new RequestParams(url);
                    // params2 = new RequestParams(URL1);
                    //Log.w("WangJ", "运行在这个ok"+params.toString());
                    Log.w("WangJ", "运行在这个ok");
                    String jsonObject= Web_Json.Login(text_username.getText().toString(),text_password.getText().toString());
                    Log.e("Json", jsonObject);
                    params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部\
                    params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
                    params.setBodyContent(jsonObject);//添加json内容到请求参数里
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("yjq",result);//接收JSON的字符串
                            HashMap<String,String> map=Web_Json.getJson(result);
                            SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putBoolean("User_flag",true);
                            editor.putString("User",text_username.getText().toString());
                            editor.putString("Icon",null);
                            editor.putString("User_str",map.get("token"));
                            editor.putString("Icon_time",null);
                            editor.commit();
                            User_public.setUser(preferences.getString("User",text_username.getText().toString()));
                            User_public.setUser_flag(preferences.getBoolean("User_flag",true));
                            User_public.setUser_str(preferences.getString("User_str",map.get("token")));
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            Log.e("yjq1", "编码:"+map.get("code")+map.get("message"));

                        }
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(Login.this, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
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
                } else {
                    Log.e("WangJ", "都不空");
                    Toast.makeText(Login.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onClick(View view){
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);
        finish();
    }
}
