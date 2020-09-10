package com.app.demo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.demo.R;
import com.app.demo.beans.MessageBean;
import com.app.demo.beans.MessageTypeBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.TimeUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddTypeActivity extends BaseActivity {

    @BindView(R.id.edt_name)
    EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imgv_back, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                onBackPressed();
                break;
            case R.id.btn_done:
                add();
                break;
        }
    }

    private void add() {

        if (StringUtil.isEmpty(edtName.getText().toString())) {
            ToastUtil.showToast(this, "请完善信息");
            return;
        }

        MessageTypeBean bean = new MessageTypeBean();
        bean.setUser_id(UserManager.getUserId(this));
        bean.setType_id(System.currentTimeMillis() + "");
        bean.setType(edtName.getText().toString());
        bean.setCreateTime(TimeUtil.getTodayData("yyyy-MM-dd hh-mm-ss"));
        bean.save();
        EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
        onBackPressed();
    }
}
