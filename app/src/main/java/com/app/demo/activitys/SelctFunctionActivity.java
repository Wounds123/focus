package com.app.demo.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.demo.R;
import com.app.demo.fragments.DataFragment;
import com.app.demo.fragments.SetFragment;
import com.app.lockscreen.Lock_Screen;
import com.app.problem_collect.Problem_collect;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.base.BaseFragment;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.rance.library.ButtonData;
import com.rance.library.ButtonEventListener;
import com.rance.library.SectorMenuButton;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import de.greenrobot.event.EventBus;

public class SelctFunctionActivity extends BaseActivity {


    private RelativeLayout ground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rance.sectormenu.R.layout.button_view);
        if(ContextCompat.checkSelfPermission(SelctFunctionActivity.this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WAKE_LOCK},
                    1);
            Log.d("test", "1566");

        }
        Bmob.initialize(this, "e01dc61baeb809789bfb422d9e1ca30c");
        ground = (RelativeLayout) findViewById(R.id.ground);
        ground.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toPicture();
                return false;
            }
        });
        initData();
        initBottomSectorMenuButton();

    }



    private void initBottomSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(com.rance.sectormenu.R.id.bottom_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {com.rance.sectormenu.R.mipmap.like, com.rance.sectormenu.R.mipmap.lock,
                com.rance.sectormenu.R.mipmap.data, com.rance.sectormenu.R.mipmap.problem,com.rance.sectormenu.R.mipmap.book};
        for (int i = 0; i < 5; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, com.rance.sectormenu.R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    private void setListener(final SectorMenuButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                if(index==1){
                    Intent intent2 = new Intent(SelctFunctionActivity.this,com.app.lockscreen.Lock_Screen.class);
                    startActivity(intent2);

                }
                else if(index==2){
                    showActivity(SelctFunctionActivity.this,com.app.demo.MainActivity.class);
                }
                else if(index==3){
                    Intent intent = new Intent(SelctFunctionActivity.this,com.app.problem_collect.Problem_collect.class);
                    startActivity(intent);
                }
                else if(index==4){
                    Intent intent = new Intent(SelctFunctionActivity.this,com.app.problem_collect.Problem_review.class);
                    startActivity(intent);
                }
               // showToast("button" + index);
            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(SelctFunctionActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    private void initData() {
        if (UserManager.isLogin(this)) {
            return;
        } else {
            showActivity(this, LoginActivity.class);
            finish();

        }
    }

    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent,100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断返回码不等于0
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RESULT_CANCELED) {    //RESULT_CANCELED = 0(也可以直接写“if (requestCode != 0 )”)
            //读取返回码
            switch (requestCode) {
                case 100:   //相册返回的数据（相册的返回码）
                    Uri uri01 = data.getData();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri01));
                        Drawable drawable =new BitmapDrawable(bitmap);
                        ground.setBackground(drawable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;
            }
        }
    }




}
