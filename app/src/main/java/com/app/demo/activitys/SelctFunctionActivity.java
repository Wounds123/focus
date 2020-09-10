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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.rance.sectormenu.MainActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SelctFunctionActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rance.sectormenu.R.layout.button_view);
        initData();
        initBottomSectorMenuButton();
//        initTopSectorMenuButton();
//        initRightSectorMenuButton();
//        initCenterSectorMenuButton();
    }

//    private void initTopSectorMenuButton() {
//        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.top_sector_menu);
//        final List<ButtonData> buttonDatas = new ArrayList<>();
//        int[] drawable = {R.mipmap.like, R.mipmap.mark,
//                R.mipmap.search, R.mipmap.copy};
//        for (int i = 0; i < 4; i++) {
//            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
//            buttonData.setBackgroundColorId(this, R.color.colorAccent);
//            buttonDatas.add(buttonData);
//        }
//        sectorMenuButton.setButtonDatas(buttonDatas);
//        setListener(sectorMenuButton);
//    }
//
//    private void initRightSectorMenuButton() {
//        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.right_sector_menu);
//        final List<ButtonData> buttonDatas = new ArrayList<>();
//        int[] drawable = {R.mipmap.like, R.mipmap.mark,
//                R.mipmap.search, R.mipmap.copy};
//        for (int i = 0; i < 4; i++) {
//            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
//            buttonData.setBackgroundColorId(this, R.color.colorAccent);
//            buttonDatas.add(buttonData);
//        }
//        sectorMenuButton.setButtonDatas(buttonDatas);
//        setListener(sectorMenuButton);
//    }
//
//    private void initCenterSectorMenuButton() {
//        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.center_sector_menu);
//        final List<ButtonData> buttonDatas = new ArrayList<>();
//        int[] drawable = {R.mipmap.like, R.mipmap.mark,
//                R.mipmap.search, R.mipmap.copy, R.mipmap.settings,
//                R.mipmap.heart, R.mipmap.info, R.mipmap.record,
//                R.mipmap.refresh};
//        for (int i = 0; i < 9; i++) {
//            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
//            buttonData.setBackgroundColorId(this, R.color.colorAccent);
//            buttonDatas.add(buttonData);
//        }
//        sectorMenuButton.setButtonDatas(buttonDatas);
//        setListener(sectorMenuButton);
//    }

    private void initBottomSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(com.rance.sectormenu.R.id.bottom_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {com.rance.sectormenu.R.mipmap.like, com.rance.sectormenu.R.mipmap.lock,
                com.rance.sectormenu.R.mipmap.data, com.rance.sectormenu.R.mipmap.problem};
        for (int i = 0; i < 4; i++) {
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




}
