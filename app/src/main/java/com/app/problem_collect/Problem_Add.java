package com.app.problem_collect;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.app.demo.R;
import com.app.shop.mylibrary.utils.AppDir;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;

import java.io.File;
import java.io.IOException;


//添加一道错题的子活动******************************************返回值为1


public class Problem_Add extends AppCompatActivity {

    private Context context;
    String str="nothing";
    public static  final int TAKE_PHOTO=1;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_activity_add);
        final EditText e=(EditText)findViewById(R.id.edit);
        Button btn_a=(Button)findViewById(R.id.btn_add);
        Button btn_c=(Button)findViewById(R.id.btn_cancle);
        context= Problem_Add.this;

        if(ContextCompat.checkSelfPermission(Problem_Add.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);


        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=e.getText().toString();

                if (StringUtil.isEmpty(str)) {
                    ToastUtil.showToast(Problem_Add.this, "请输入题目名");
                    return;
                }
                File outputImage=new File(getExternalFilesDir("DirectoryPictures"),System.currentTimeMillis() + ".jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();

                }


                if(Build.VERSION.SDK_INT>=24){

                    imageUri= FileProvider.getUriForFile(Problem_Add.this, "com.app.demo.FileProvider", outputImage);


                }else{
                    imageUri=Uri.fromFile(outputImage);
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                //Log.d("aaa", "123");
                startActivityForResult(intent,TAKE_PHOTO);
                //Log.d("aaa","成功");


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    int gp;
                    Bundle bd=getIntent().getExtras();
                    gp=bd.getInt("GP");
                    String ss=str;
                    Intent intent=new Intent();
                    Bundle rt=new Bundle();
                    rt.putInt("GP",gp);
                    rt.putString("name",ss);
                    String uri=imageUri.toString();
                    rt.putString("uri",uri);
                    intent.putExtras(rt);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            default:break;
        }
    }
}
