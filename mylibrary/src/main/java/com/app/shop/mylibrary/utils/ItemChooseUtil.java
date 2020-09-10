package com.app.shop.mylibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.app.shop.mylibrary.R;
import com.app.shop.mylibrary.interfaces.I_itemSelectedListener;
import com.app.shop.mylibrary.widgts.MyAlertDialog;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.List;

/**
 * Created by seven on 2017/2/22.
 */

public class ItemChooseUtil {

    public static void showItemWheel(Context context, final List list, String title, int position, final I_itemSelectedListener i_itemSelectedListener) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.loopview_item, null);
        final LoopView loopView = (LoopView) contentView.findViewById(R.id.loopview);
        loopView.setNotLoop();//设置是否循环播放
        loopView.setListener(new OnItemSelectedListener() {//滚动监听
            @Override
            public void onItemSelected(int index) {
            }
        });
        loopView.setDividerColor(Color.parseColor("#E3E3E3"));
        loopView.setCenterTextColor(Color.parseColor("#43496a"));
        loopView.setItems(list);//设置原始数据
        loopView.setCurrentPosition(position); //设置初始位置
        loopView.setTextSize(15);//设置字体大小
        final MyAlertDialog dialog1 = new MyAlertDialog(context)
                .builder()
                .setView(contentView);
        dialog1.setTitle(title);
        dialog1.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog1.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i_itemSelectedListener.onItemSelected(loopView.getSelectedItem());
            }
        });
        dialog1.show();
    }

//    public static void showItemWheel(Context context, final List list, String title, int position, final I_itemSelectedListener i_itemSelectedListener) {
//        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                i_itemSelectedListener.onItemSelected(options1);
//            }
//        })
//                .setTitleText(title)
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setSubCalSize(15)
//                .setContentTextSize(15)//滚轮文字大小
//                .setTitleSize(15)//标题文字大小
//                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
//                .setSubmitColor(Color.parseColor("#0088ff"))//确定按钮文字颜色
//                .setCancelColor(Color.parseColor("#666666"))//取消按钮文字颜色
//                .setDividerColor(Color.parseColor("#e3e3e3"))//设置分割线的颜色
//                .setTextColorCenter(Color.parseColor("#333333"))//设置分割线之间的文字的颜色
//                .setTextColorOut(Color.parseColor("#999999"))
//                .setSelectOptions(0)//默认选中项
//                .setBgColor(Color.WHITE)
//                .setLineSpacingMultiplier(1.6f)//越大越高
//                .setTitleBgColor(Color.WHITE)
//                .setTitleColor(Color.WHITE)
//                .setDividerType(WheelView.DividerType.FILL)//分割线是否分开
//                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setLabels("", "", "")
//                .setBackgroundId(Color.parseColor("#00000000")) //设置外部遮罩颜色
//                .build();
//        pvOptions.setSelectOptions(position);
//        pvOptions.setPicker(list);//一级选择器
//        pvOptions.show();
//    }


}

