package com.example.yexin.menu6;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yexin.menu6.Public_class.User_public;
import com.example.yexin.menu6.Setting.SettingActivity;
import com.example.yexin.menu6.Thread.Thread_one;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView navigation;
    private Thread_one thread_one=null;
    private Thread thread=null;
    /*底部导航栏按钮监听事件*/
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_games:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_friends:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                /**
                 * 不是第一次登入，并且长连接并没有失效，去服务端重新获取长连接，如果失效，要求重新登入
                 */
        /**
         * 启动一个线程进行长连接的判断
         */
        /**
         * 没有失效，code=200,message=没有失效，token=新的
                 * 失效了的话，code=500,message=失效了，token=null
                 */
        Log.e("yjq_six","不是第一次登入，判断长连接");
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        /**
         * 判断的线程
         */
//        if(User_public.isFirst()){
            thread_one=new Thread_one(MainActivity.this);
            thread=new Thread(thread_one);
            thread.run();
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*修改toolbar原有返回button*/
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.icon_setting);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if (User_public.isUser_flag()) {
                drawer.openDrawer(GravityCompat.START);
            } else {
                Toast.makeText(MainActivity.this, "请先登入！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new oneFragment());
        adapter.addFragment(new twoFragment());
        adapter.addFragment(new threeFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.searchview, menu);
        final MenuItem item = menu.findItem(R.id.search_main);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(item);

        //搜索图标是否显示在搜索框内
        mSearchView.setIconifiedByDefault(true);
        //设置搜索框展开时是否显示提交按钮，可不显示
        mSearchView.setSubmitButtonEnabled(true);
        //让键盘的回车键设置成搜索
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //搜索框是否展开，false表示展开
        mSearchView.setIconified(false);
        //获取焦点
        /*mSearchView.setFocusable(true);
        mSearchView.requestFocusFromTouch();*/
        //设置提示词
        mSearchView.setQueryHint("搜索");
        //设置输入框文字颜色
        EditText editText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        editText.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_indent) {
            // Handle the camera action
        } else if (id == R.id.nav_wallet) {

        } else if (id == R.id.nav_erweima) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_yijianfankui) {

        } else if (id == R.id.nav_help) {

        } else if (id ==R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * 界面退出
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
//    public boolean Overdue(){
//        boolean flag=true;
//        String url = "http://172.22.70.227:8080/ByteSoft_two/Login";
//        RequestParams params = new RequestParams(url);
//        // params2 = new RequestParams(URL1);
//        //Log.w("WangJ", "运行在这个ok"+params.toString());
//        Log.w("WangJ", "运行在这个ok");
//        String jsonObject= Web_Json.Login_one(User_public.getUser().toString(),User_public.getUser_str().toString());
//        Log.e("Json", jsonObject);
//        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部\
//        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
//        params.setBodyContent(jsonObject);//添加json内容到请求参数里
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                /**
//                 * 如果返回的报文为200，没有失效，登入成功，换取长连接
//                 * 如果为500，长连接失效，登入失败，提示去重新登入
//                 */
//                map=Web_Json.getJson(result);
//                Log.e("yjq_four","新的长连接：code"+map.get("code")+"message"+map.get("message")+"token"+map.get("token"));
//            }
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                //Toast.makeText(Login.this, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
//                Log.e("yjq1","失败");
//            }
//            @Override
//            public void onCancelled(CancelledException cex) {
//                Log.e("yjq","取消");
//            }
//            @Override
//            public void onFinished() {
//                Log.e("yjq","完成");
//            }
//        });
//        if(map.get("code").equals("200")){
//            Log.e("yjq_11","长连接没有失效，并且更新了长连接");
//        }
//        else{
//            Log.e("yjq_12","长连接失效了重新去登入");
//            flag=false;
//        }
//        return flag;
//    }
//    public void Layout_init(){
//        tv_register = (TextView)findViewById(R.id.tv_register);
//        text_username=(EditText)findViewById(R.id.et_username);
//        text_password=(EditText)findViewById(R.id.et_password);
//        button_submit=(Button)findViewById(R.id.btn_login);
//        button_submit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(text_username.getText().toString())
//                        && !TextUtils.isEmpty(text_password.getText().toString())) {
//                    Log.e("WangJ", "都不空");
//                    /**
//                     * 点击登入，账号密码都不为空的时候，发起网络请求
//                     */
//                    String url = "http://172.22.70.227:8080/ByteSoft_two/Login";
//                    RequestParams params = new RequestParams(url);
//                    // params2 = new RequestParams(URL1);
//                    //Log.w("WangJ", "运行在这个ok"+params.toString());
//                    Log.w("WangJ", "运行在这个ok");
//                    String jsonObject= Web_Json.Login(text_username.getText().toString(),text_password.getText().toString());
//                    Log.e("Json", jsonObject);
//                    params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部\
//                    params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
//                    params.setBodyContent(jsonObject);//添加json内容到请求参数里
//                    x.http().post(params, new Callback.CommonCallback<String>() {
//                        @Override
//                        public void onSuccess(String result) {
//                            Log.e("yjq",result);//接收JSON的字符串
//                            HashMap<String,String> map=Web_Json.getJson(result);
//                            SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
//                            SharedPreferences.Editor editor=preferences.edit();
//                            editor.putBoolean("User_flag",true);
//                            editor.putString("User",text_username.toString());
//                            editor.putString("Icon",null);
//                            editor.putString("User_str",map.get("token"));
//                            editor.putString("Icon_time",null);
//                            editor.commit();
//                            User_public.setUser(preferences.getString("User",text_username.toString()));
//                            User_public.setUser_flag(preferences.getBoolean("User_flag",true));
//                            User_public.setUser_str(preferences.getString("User_str",map.get("token")));
//
//                            Log.e("yjq1", "编码:"+map.get("code")+map.get("message"));
//                        }
//                        @Override
//                        public void onError(Throwable ex, boolean isOnCallback) {
//                            Toast.makeText(MainActivity.this, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
//                            Log.e("yjq1","失败");
//                        }
//                        @Override
//                        public void onCancelled(CancelledException cex) {
//                            Log.e("yjq","取消");
//                        }
//                        @Override
//                        public void onFinished() {
//                            Log.e("yjq","完成");
//                        }
//                    });
//                } else {
//                    Log.e("WangJ", "都空");
//                    Toast.makeText(MainActivity.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);
        finish();
    }
}
