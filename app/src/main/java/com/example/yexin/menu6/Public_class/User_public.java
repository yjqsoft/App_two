package com.example.yexin.menu6.Public_class;

/**
 * Created by DELL on 2019/10/13.
 */

public class User_public {

    private static String User;
    private static boolean User_flag;
    private static String User_str;
    private static boolean first;

    public static boolean isFirst() {
        return first;
    }

    public static void setFirst(boolean network) {
        first = network;
    }


    public static String getUser() {
        return User;
    }

    public static void setUser(String user) {
        User = user;
    }

    public static boolean isUser_flag() {
        return User_flag;
    }

    public static void setUser_flag(boolean user_flag) {
        User_flag = user_flag;
    }

    public static String getUser_str() {
        return User_str;
    }

    public static void setUser_str(String user_str) {
        User_str = user_str;
    }
}
