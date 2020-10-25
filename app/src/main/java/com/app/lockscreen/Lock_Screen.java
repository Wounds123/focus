package com.app.lockscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.demo.MyApplication;
import com.app.demo.R;
import com.app.problem_collect.Problem_collect;



public class Lock_Screen extends AppCompatActivity {

    MyApplication myApplication=MyApplication.getInstance();
    private int succeed,fail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i);

        EditText fen=findViewById(R.id.fen);
        EditText second=findViewById(R.id.second);
        TextView rate=findViewById(R.id.succeed_rate);

        InitRate();

        int sum=fail+succeed;
        rate.setText(succeed+"/"+sum);

        Button rank = findViewById(R.id.rank);

        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Lock_Screen.this,Lock_Rank.class);
                startActivity(intent);
            }
        });



        Button startFloatWindow = (Button) findViewById(R.id.start_float_window);
        startFloatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String f=fen.getText().toString();
                String s=second.getText().toString();

                if(TextUtils.isEmpty(f)||TextUtils.isEmpty(s))
                {
                    Toast.makeText(Lock_Screen.this,"请输入完整时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    int i = Integer.parseInt(f), j = Integer.parseInt(s);
                    //Log.d("sss", Integer.toString(i));
                    myApplication.setTime(i, j);
                    fen.setText("");
                    second.setText("");
                    MyWindowManager.createBigWindow(Lock_Screen.this);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                //启动Activity让用户授权
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 100);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "悬浮窗权限已被接受", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "悬浮窗权限已被拒绝", Toast.LENGTH_SHORT).show();

                }
            }

        }

    }
    private void InitRate(){
        SharedPreferences mySharedPreferences= getSharedPreferences("lock", Activity.MODE_PRIVATE);
        succeed = mySharedPreferences.getInt("succeed", 0);
        fail = mySharedPreferences.getInt("fail", 0);
    }

}
