package com.app.lockscreen;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

public class time_store extends Activity {

    private static int fen,miao;

    public void set_time(int fen,int miao){
        this.fen=fen;
        this.miao=miao;
    }

    public int getFen(){
        return fen;
    }

    public int getMiao(){
        return miao;
    }
}
