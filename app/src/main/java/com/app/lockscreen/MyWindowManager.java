package com.app.lockscreen;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;


public class MyWindowManager {

    private static FloatWindowBigView bigWindow;

    private static WindowManager.LayoutParams bigWindowParams;

    private static WindowManager mWindowManager;

    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;//windowManager.getDefaultDisplay().getWidth();
        int screenHeight = dm.heightPixels;
        if (bigWindow == null) {
            bigWindow = new FloatWindowBigView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new WindowManager.LayoutParams();
                bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
                bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;

                if(judge()){
                    bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                else {
                    bigWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }

                bigWindowParams.format = PixelFormat.RGBA_8888;

                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBigView.viewWidth;
                bigWindowParams.height = FloatWindowBigView.viewHeight;
            }

            windowManager.addView(bigWindow, bigWindowParams);
        }
    }

    public static void removeBigWindow(Context context) {
        if (bigWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigWindow);
            bigWindow = null;
        }
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public static boolean judge(){
        String str = android.os.Build.VERSION.RELEASE;
        str =str.substring(0,1);
        int a=Integer.parseInt(str);
        if(a<8&&a!=1) return true;
        else return false;


    }



}
