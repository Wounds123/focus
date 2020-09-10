package com.app.demo.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demo.R;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.TimeUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.imgv_return)
    ImageView imgvReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edtNo)
    EditText edtNo;
    @BindView(R.id.edtpwd)
    EditText edtpwd;
    @BindView(R.id.edtpwd_repeat)
    EditText edtpwdRepeat;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        tvTitle.setText("注册");
    }

    @OnClick({R.id.imgv_return, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;
            case R.id.tv_register:
                if (StringUtil.isEmpty(edtNo.getText().toString())) {
                    ToastUtil.showToast(this, "请输入工号");
                    return;
                }


                if (StringUtil.isEmpty(edtpwd.getText().toString())) {
                    ToastUtil.showToast(this, "请输入密码");
                    return;
                }

                if (StringUtil.isEmpty(edtpwdRepeat.getText().toString())) {
                    ToastUtil.showToast(this, "请再次输入密码");
                    return;
                }


                if (!edtpwd.getText().toString().equals(edtpwdRepeat.getText().toString())) {
                    ToastUtil.showToast(this, "两次密码不一致");
                    return;
                }


                if (UserManager.isHaveUser(edtNo.getText().toString())) {
                    ToastUtil.showToast(this, "已存在该用户");
                    return;
                }

                UserBean userBean = new UserBean();
                userBean.setUser_id(edtNo.getText().toString());
                userBean.setName(edtNo.getText().toString());
                userBean.setPassword(edtpwd.getText().toString());
                userBean.setRigster_time(TimeUtil.getTodayData("yyyy-MM-dd HH-mm-ss"));
                userBean.save();

                ToastUtil.showToast(this, "您已注册成功，请登录");

                onBackPressed();
                break;
        }
    }
}
