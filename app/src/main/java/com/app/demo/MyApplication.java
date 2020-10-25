package com.app.demo;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.app.lockscreen.User_Name_List;
import com.app.shop.mylibrary.utils.AppDir;
import com.app.shop.mylibrary.utils.FrescoUtil;
import com.app.shop.mylibrary.utils.UserManager;

import org.litepal.LitePal;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @anthor : 大海
 * 每天进步一点点
 */


public class MyApplication extends Application {

    private static MyApplication instance;

    private static int fen=60,miao=0;
    private int succeed,fail;
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "e01dc61baeb809789bfb422d9e1ca30c");
        instance = this;
        if (instance != null) {
            //数据库初始化
            LitePal.initialize(instance);

            //创建应用缓存路径
            AppDir.getInstance(this);

            //fresco初始化
            FrescoUtil.init(instance);

        }
    }

    public void setTime(int fen,int miao){
        this.fen=fen;
        this.miao=miao;
    }
    public int getFen(){return fen;}
    public int getMiao(){return miao;}
    public static MyApplication getInstance() {
        return instance;
    }
    public int getSucceed(){return succeed;}
    public int getFail(){return fail;}
    public void addSucceed(){
        SharedPreferences mySharedPreferences= getSharedPreferences("lock", Activity.MODE_PRIVATE);
        String name = UserManager.getUserName(this);
        String id = mySharedPreferences.getString(name,null);
        succeed = mySharedPreferences.getInt("succeed", 0);
        succeed++;
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        int lock_time = mySharedPreferences.getInt("lock_time", 0);
        lock_time+=fen;
        editor.putInt("lock_time", lock_time);
        editor.putInt("succeed", succeed);
        editor.apply();
        Log.d("lock", id);

        if(!TextUtils.isEmpty(id))
        {
            User_Name_List p2 = new User_Name_List();
            p2.setLock_Time(lock_time);
            p2.update(id, new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Toast.makeText(MyApplication.this,"成功计时",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.this,"计时失败",Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }


    }
    public void addFail(){
        SharedPreferences mySharedPreferences= getSharedPreferences("lock", Activity.MODE_PRIVATE);
        fail = mySharedPreferences.getInt("fail", 0);
        fail++;
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt("fail", fail);
        editor.apply();
        Log.d("lock", "fail"+fail);
    }
}
