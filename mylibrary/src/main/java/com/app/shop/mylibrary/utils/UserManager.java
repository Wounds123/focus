package com.app.shop.mylibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.beans.User_Name_List;
import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @anthor : 大海
 * 每天进步一点点
 * @time :   2019/12/26
 * @description :
 */


public class UserManager {


    /**
     * 判断用户是否登录
     *
     * @return
     */

    public static boolean isLogin(Context context) {

        return !StringUtil.isEmpty(SharedPreferencesUtil.getData(context, "user", "user_id", ""));

    }


    public static String getUserName(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "name", "");

    }


    public static int getUserHeadImg(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "photo", 0);

    }


    public static int getUserType(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "type", 0);

    }


    public static String getUserId(Context context) {
        String id = SharedPreferencesUtil.getData(context, "user", "user_id", "");
//        String id="111";
        Logger.e("-------id" + id);
        return id;

    }

    public static String getUserSex(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "sex", "");

    }


    public static String getAge(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "age", "");

    }

    public static String getBirthday(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "birthday", "");

    }

    public static String getMobile(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "mobile", "");

    }


    public static String getClass(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "grade", "");

    }

    public static String getGrade(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "grade", "");

    }


    public static String getPassword(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "password", "");

    }

    public static String getTeach(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "class_teach", "");

    }

    public static String getAllClass(Context context) {
        return SharedPreferencesUtil.getData(context, "user", "class_choice", "");
    }

    public static boolean isHaveClass(Context context, String str) {
        String class_choice = SharedPreferencesUtil.getData(context, "user", "class_choice", "");
        if (!StringUtil.isEmpty(class_choice)) {
            String[] strings = class_choice.split(",");
            List<String> list = Arrays.asList(strings);
            return list.contains(str);
        } else {
            return false;
        }
    }


    public static boolean isHaveUser(String id) {

        List<UserBean> list = DataSupport.findAll(UserBean.class);
        List<String> list_id = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list_id.add(list.get(i).getUser_id());
            }
        }
        return list_id.contains(id);
    }

    public static boolean isHaveUser(String id, int type) {


        List<UserBean> list_all = DataSupport.findAll(UserBean.class);
        List<UserBean> list = new ArrayList<>();
        for (int i = 0; i < list_all.size(); i++) {
            if (list_all.get(i).getType() == type) {
                list.add(list_all.get(i));
            }
        }
        List<String> list_id = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list_id.add(list.get(i).getUser_id());
            }
        }
        return list_id.contains(id);
    }


    public static UserBean getUser(String id) {
        List<UserBean> list = DataSupport.findAll(UserBean.class);

        UserBean bean = new UserBean();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_id().equals(id)) {
                bean = list.get(i);
            }
        }
        return bean;
    }

    public static boolean isOk(String name, String pwd) {
        List<UserBean> list = DataSupport.findAll(UserBean.class);
        String password = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_id().equals(name)) ;
            password = list.get(i).getPassword();
        }
        return password.equals(pwd);
    }

    public void getUser() {
        List<UserBean> list = DataSupport.findAll(UserBean.class);
    }




}
