package com.app.demo.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.demo.R;
import com.app.lockscreen.UserAdapter;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.beans.User_Name_List;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.TimeUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends BaseActivity {

    private boolean used = true;
    private boolean finish = true;
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
        Bmob.initialize(this, "e01dc61baeb809789bfb422d9e1ca30c");
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



                equal(edtNo.getText().toString());








                break;
        }
    }


    private void equal(String name) {

        BmobQuery<User_Name_List> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("User_Name", name);
        categoryBmobQuery.findObjects(new FindListener<User_Name_List>() {
            @Override
            public void done(List<User_Name_List> object, BmobException e) {
                Message msg = new Message();
                if (e == null) {

                    if(object.size()>0)
                    {
                        msg.what=2;

                    }
                    else
                    {
                        msg.what=1;
                    }


                } else {
                    //Log.e("BMOB", e.toString());
                        msg.what=2;
                }
                handler.sendMessage(msg);
            }
        });
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    conect(edtNo.getText().toString());
                    break;
                case 2:
                    ToastUtil.showToast(RegisterActivity.this, "该用户已存在或网络断开");
                    break;
                case 3:
                    succeed_re();
                    break;
                case 4:
                    ToastUtil.showToast(RegisterActivity.this, "注册失败，请检查网络");
                    break;
                default:break;




            }
        }
    };

    private void conect(String name)
    {

        User_Name_List p2 = new User_Name_List();
        p2.setUser_Name(name);
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    SharedPreferences mySharedPreferences= getSharedPreferences("lock", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString(name,objectId);
                    editor.apply();
                    Message msg = new Message();
                    msg.what=3;
                    handler.sendMessage(msg);
                }else{
                   Message msg = new Message();
                   msg.what=4;
                   handler.sendMessage(msg);
                }
            }
        });
    }

    private void succeed_re()
    {
        UserBean userBean = new UserBean();
        userBean.setUser_id(edtNo.getText().toString());
        userBean.setName(edtNo.getText().toString());
        userBean.setPassword(edtpwd.getText().toString());
        userBean.setRigster_time(TimeUtil.getTodayData("yyyy-MM-dd HH-mm-ss"));
        userBean.save();

        ToastUtil.showToast(this, "您已注册成功，请登录");
        used=true;
        onBackPressed();
    }
}
