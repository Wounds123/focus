package com.app.demo.activitys;

import android.content.ContentValues;
import android.content.DialogInterface;
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
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.utils.ItemChooseUtil;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;
import com.app.shop.mylibrary.widgts.CustomDialog;
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

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.edt_detail)
    EditText edtDetail;
    @BindView(R.id.tv_shoucang)
    TextView tv_shoucang;


    private String message_id;
    MessageBean bean;
    private String dialog_title = "删除类型";
    private String dialog_content = "是否确定删除？";
    private CustomDialog customDialog;
    Bundle bundle;
    String time;
    String date;
    String type;
    List<MessageTypeBean> list_type = new ArrayList<>();
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {

        bundle = getIntent().getExtras();
        bean = (MessageBean) bundle.getSerializable("messageBean");
        message_id = bean.getMessage_id();
        type = bean.getMessage_type();
        time = StringUtil.getContentEmpty(bean.getMessageTime());
        edtName.setText(StringUtil.getContentEmpty(bean.getMessage()));
        tv_time.setText(StringUtil.getContentEmpty(bean.getMessageTime()));
        edtDetail.setText(StringUtil.getContentEmpty(bean.getDetail()));
        tv_type.setText(StringUtil.getContentEmpty(type));
        tv_shoucang.setText(bean.getIsShoucang() == 0 ? "收藏" : "取消收藏");

        list_type = DataSupport.findAll(MessageTypeBean.class);
        if (list_type != null) {
            for (int i = 0; i < list_type.size(); i++) {
                list.add(list_type.get(i).getType());
            }
        }

    }

    @OnClick({R.id.imgv_back, R.id.tv_time, R.id.tv_type, R.id.tv_bt, R.id.tv_modify, R.id.tv_shoucang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_back:
                onBackPressed();
                break;
            case R.id.tv_time:
                selectTime();
                break;

            case R.id.tv_type:
                if (list.size() > 0) {
                    ItemChooseUtil.showItemWheel(this, list, null, 0, new I_itemSelectedListener() {
                        @Override
                        public void onItemSelected(int currentPosition) {
                            type = list.get(currentPosition);
                            tv_type.setText(type);
                        }
                    });
                } else {
                    ToastUtil.showToast(this, "暂无类型，快快去设置中添加吧");
                }

                break;

            case R.id.tv_modify:
                modify();
                break;

            case R.id.tv_bt:
                del();
                break;
            case R.id.tv_shoucang:
                if (bean.getIsShoucang() == 0) {
                    ContentValues values = new ContentValues();
                    values.put("isShoucang", 1);
                    DataSupport.updateAll(MessageBean.class, values, "message_id = ?", message_id);
                    bean.setIsShoucang(1);
                    ToastUtil.showToast(this, "收藏成功");
                } else {
                    ContentValues values = new ContentValues();
                    values.put("isShoucang", 0);
                    DataSupport.updateAll(MessageBean.class, values, "message_id = ?", message_id);
                    bean.setIsShoucang(0);
                    ToastUtil.showToast(this, "取消收藏");
                }
                tv_shoucang.setText(bean.getIsShoucang() == 0 ? "收藏" : "取消收藏");
                EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
                break;
        }
    }

    private void modify() {
        if (StringUtil.isEmpty(edtName.getText().toString()) || StringUtil.isEmpty(type) || StringUtil.isEmpty(time) || StringUtil.isEmpty(edtDetail.getText().toString())) {
            ToastUtil.showToast(this, "请完善信息");
            return;
        }
        ContentValues values = new ContentValues();
        values.put("message", edtName.getText().toString());
        values.put("messageTime", time);
        values.put("detail", edtDetail.getText().toString());
        values.put("message_type", type);
        DataSupport.updateAll(MessageBean.class, values, "message_id=?", message_id);
        EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
        onBackPressed();
    }

    private void del() {
        customDialog = DialogUtil.showDialog(this, customDialog, 2, dialog_title, dialog_content, "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                DataSupport.deleteAll(MessageBean.class, "message_id=?", message_id);
                ContentValues values = new ContentValues();
                values.put("isDel", 1);
                DataSupport.updateAll(MessageBean.class, values, "message_id = ?", message_id);
                EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
                finish();
            }
        });

        if (customDialog != null && !customDialog.isShowing()) {
            customDialog.show();
        }


        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                customDialog = null;
            }
        });
    }


    private void selectTime() {
        TimePickerUtil mTimePicker = new TimePickerUtil();
        mTimePicker.setDatePicker(this, TimePickerView.Type.YEAR_MONTH_DAY, null, null, null, new TimePickerCallback() {
            @Override
            public void setTimeCallback(int order, int year, int month, int day, int hour, int min, String idcard) {
                time = year + "-" + addZero(month) + "-" + addZero(day) + " " + addZero(hour) + ":" + addZero(min);
                date = year + "-" + addZero(month) + "-" + addZero(day);
                tv_time.setText(time);
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

}
