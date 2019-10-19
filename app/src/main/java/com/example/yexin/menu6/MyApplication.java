package com.example.yexin.menu6;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.yexin.menu6.Json.Web_Json;
import com.example.yexin.menu6.Public_class.User_public;
import com.example.yexin.menu6.SharedPresences.Share;
import com.example.yexin.menu6.Wifi.NetManagerService;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

/**
 * Created by DELL on 2019/9/29.
 */

public class MyApplication extends Application {
    private NetManagerService netManagerService;
    private IntentFilter intentFilter;
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(true); //是否输出debug日志，开启debug会影响性能。
        /**
         * 判断是否是第一次运行
         */
        /**
         * 注册监听器
         */
        netManagerService=new NetManagerService();
        intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netManagerService, intentFilter);
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
        unregisterReceiver(netManagerService);
    }
    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        super.onTrimMemory(level);
    }
}
