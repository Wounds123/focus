package com.app.lockscreen;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.demo.MyApplication;
import com.app.demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class FloatWindowBigView extends LinearLayout {

    public static int viewWidth;//悬浮窗宽度

    public static int viewHeight;//悬浮窗高度


    private int seconds = 5;
    private int fen = 0;
    private TextView txtViews;
    private TextView txtViewf;
    private TextView mao;
    private TextView succeed;
    MyApplication myApplication=MyApplication.getInstance();
    Timer timer = new Timer();

    String min,second;



    public FloatWindowBigView(final Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Button close = (Button) findViewById(R.id.close);
        txtViews=(TextView)findViewById(R.id.count2);
        txtViewf=(TextView)findViewById(R.id.count);
        mao=(TextView)findViewById(R.id.mao);
        succeed=(TextView)findViewById(R.id.succeed) ;
        fen=myApplication.getFen();
        seconds=myApplication.getMiao();
        txtViewf.setText(""+fen);
        txtViews.setText(""+seconds);
        timer.schedule(task, 1000, 1000);


        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗
                MyWindowManager.removeBigWindow(context);
            }
        });
    }
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:

                    if(seconds>=0&&seconds<10){
                        txtViews.setText("0"+seconds);
                    }
                    else if(seconds<0){
                        fen--;
                        if(fen<0){
                            timer.cancel();
                            mao.setVisibility(View.GONE);
                            txtViews.setVisibility(View.GONE);
                            txtViewf.setVisibility(View.GONE);
                            succeed.setVisibility(View.VISIBLE);
                        }
                        seconds=59;
                        txtViews.setText(""+seconds);
                        if(fen<10)
                            txtViewf.setText("0"+fen);
                        else
                            txtViewf.setText(""+fen);
                    }
                    else {
                        txtViews.setText(""+seconds);
                    }
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            seconds--;
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };


}
