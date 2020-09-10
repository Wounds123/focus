package com.app.demo.activitys;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.beans.MessageBean;
import com.app.demo.beans.MessageTypeBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.interfaces.I_itemSelectedListener;
import com.app.shop.mylibrary.utils.ItemChooseUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.TimeUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.timepicker.TimePickerCallback;
import com.app.shop.mylibrary.widgts.timepicker.TimePickerUtil;
import com.app.shop.mylibrary.widgts.timepicker.TimePickerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddScheduleActivity extends BaseActivity {


    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_detail)
    EditText edt_detail;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;

    List<MessageTypeBean> list = new ArrayList<>();
    List<String> list_type = new ArrayList<>();

    String type, time, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        list = DataSupport.findAll(MessageTypeBean.class);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                list_type.add(list.get(i).getType());
            }
        }
    }


    private void selectTime() {
        TimePickerUtil mTimePicker = new TimePickerUtil();
        mTimePicker.setDatePicker(this, TimePickerView.Type.YEAR_MONTH_DAY, null, null, null, new TimePickerCallback() {
            @Override
            public void setTimeCallback(int order, int year, int month, int day, int hour, int min, String idcard) {
                time = year + "-" + addZero(month) + "-" + addZero(day) + " " + addZero(hour) + ":" + addZero(min);
                date = year + "-" + addZero(month) + "-" + addZero(day);
                tvTime.setText(time);
            }
        });
        mTimePicker.showTimePicker(0);
    }


    public static String addZero(int i) {
        if (i > 9) {
            return String.valueOf(i);
        } else {
            return "0" + i;
        }
    }

    @OnClick({R.id.imgv_back, R.id.tv_type, R.id.tv_time, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                onBackPressed();
                break;
            case R.id.tv_type:
                if (list_type.size() > 0) {
                    ItemChooseUtil.showItemWheel(this, list_type, null, 0, new I_itemSelectedListener() {
                        @Override
                        public void onItemSelected(int currentPosition) {
                            type = list_type.get(currentPosition);
                            tvType.setText(list_type.get(currentPosition));
                        }
                    });
                } else {
                    ToastUtil.showToast(this, "暂无类型，快快去设置中添加吧");
                }

                break;
            case R.id.tv_time:
                selectTime();
                break;

            case R.id.btn_done:

                if (StringUtil.isEmpty(edtName.getText().toString()) || StringUtil.isEmpty(edt_detail.getText().toString()) || StringUtil.isEmpty(type) || StringUtil.isEmpty(time)) {
                    ToastUtil.showToast(this, "请完善信息");
                    return;
                }

                MessageBean bean = new MessageBean();
                bean.setUser_id(UserManager.getUserId(this));
                bean.setMessage_id(System.currentTimeMillis() + "");
                bean.setMessage(edtName.getText().toString());
                bean.setMessageDate(date);
                bean.setCreateTime(TimeUtil.getTodayData("yyyy-MM-dd hh-mm-ss"));
                bean.setMessageTime(time);
                bean.setMessage_type(type);
                bean.setDetail(edt_detail.getText().toString());
                bean.save();
                EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
                onBackPressed();
                break;
        }
    }
}
