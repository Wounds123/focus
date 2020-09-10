package com.app.demo;

import android.app.Application;

import com.app.shop.mylibrary.utils.AppDir;
import com.app.shop.mylibrary.utils.FrescoUtil;

import org.litepal.LitePal;

/**
 * @anthor : 大海
 * 每天进步一点点
 */


public class MyApplication extends Application {

    private static MyApplication instance;

    private static int fen=60,miao=0;
    @Override
    public void onCreate() {
        super.onCreate();

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
}
