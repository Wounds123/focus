package com.app.shop.mylibrary.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.app.shop.mylibrary.MyApplication;
import com.app.shop.mylibrary.R;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.DisplayUtil;
import com.app.shop.mylibrary.utils.StatusBarUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


/**
 * @author yangshenghui
 * activity页面基类
 */
public abstract class BaseActivity extends SwipeActivity {


    /**
     * 修改顶部栏颜色
     */
    public void setSystemToolbarColor() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_title_bar);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setSystemToolbarColor();
        super.onCreate(savedInstanceState);
        //android 8.0 上设置竖屏 会和透明主题冲突报错
        if (Build.VERSION.SDK_INT != 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

//        DisplayUtil.setCustomDensity(this, MyApplication.getInstance());
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //停止截屏监听

    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止截屏监听

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //移除所有的粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //取消注册
        EventBus.getDefault().unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(EventMessage msg) {

    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onEventSticky(EventMessage msg) {

    }


    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
        this.overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }

    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
        this.overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }

    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
        this.overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }


    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }


}

