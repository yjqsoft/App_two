package com.example.yexin.menu6.SharedPresences;


import android.content.SharedPreferences;

import com.example.yexin.menu6.Public_class.User_public;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DELL on 2019/10/13.
 */

public class Share {
    private static SharedPreferences preferences=null;
    public static void setUser(SharedPreferences pre,String name,String token,boolean flag){
        preferences=pre;

    }
}
