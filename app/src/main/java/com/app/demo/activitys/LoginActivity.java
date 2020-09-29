package com.app.demo.activitys;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demo.MainActivity;
import com.app.demo.R;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.beans.UserBean;
import com.app.shop.mylibrary.utils.SharedPreferencesUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;


import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.imgv_return)
    ImageView imgvReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.inputName)
    EditText inputName;
    @BindView(R.id.inputpwd)
    EditText inputpwd;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSwipeEnabled(false);
        imgvReturn.setVisibility(View.GONE);
        tvTitle.setText("登录");

    }

    @OnClick({R.id.toLogin, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toLogin:

                if (StringUtil.isEmpty(inputName.getText().toString())) {
                    ToastUtil.showToast(this, "请输入用户名");
                    return;
                }

                if (StringUtil.isEmpty(inputpwd.getText().toString())) {
                    ToastUtil.showToast(this, "请输入密码");
                    return;
                }


                boolean isHaveUser = UserManager.isHaveUser(inputName.getText().toString());
                if (isHaveUser) {//有该用户
                    if (UserManager.isOk(inputName.getText().toString(), inputpwd.getText().toString())) { //密码正确
                        UserBean userBean = UserManager.getUser(inputName.getText().toString());
                        SharedPreferencesUtil.saveDataBean(this, userBean, "user");
                        EventBus.getDefault().post(new EventMessage(EventMessage.LOGIN));
                        showActivity(this, SelctFunctionActivity.class);
                        finish();
                    } else {
                        ToastUtil.showToast(this, "密码不匹配");
                    }
                } else {
                    ToastUtil.showToast(this, "无此用户，请先注册");
                }

                break;
            case R.id.tv_register:
                showActivity(this, RegisterActivity.class);
                break;
        }
    }

//    public Boolean Login(SharedPreferences UserInfo) throws IOException {
//        Connection hc = Jsoup.connect("http://jw.hitsz.edu.cn/cas")
//                .headers(defaultRequestHeader);
//        cookies.clear();
//        cookies.putAll(hc.execute().cookies());
//        String lt = null;
//        String execution = null;
//        String eventId = null;
//        Document d = hc.cookies(cookies).get();
//        lt = d.select("input[name=lt]").first().attr("value");
//        execution = d.select("input[name=execution]").first().attr("value");
//        eventId = d.select("input[name=_eventId]").first().attr("value");
//        Connection c2 = Jsoup.connect("https://sso.hitsz.edu.cn:7002/cas/login?service=http%3A%2F%2Fjw.hitsz.edu.cn%2FcasLogin")
//                .cookies(cookies)
//                .timeout(10000)
//                .headers(defaultRequestHeader)
//                .ignoreContentType(true);
//        Document page = c2.cookies(cookies)
//                .data("username", CurrentUser.getUsername())
//                .data("password", UserInfo.getString(CurrentUser.getUsername(), ""))
//                .data("lt", lt)
//                .data("rememberMe", "on")
//                .data("execution", execution)
//                .data("_eventId", eventId)
//                .post();
//        System.out.println("登录结束");
//        cookies.putAll(c2.response().cookies());
//        System.out.println(page.toString());
//
//        return page.toString().contains("信息查询");
//    }
}
