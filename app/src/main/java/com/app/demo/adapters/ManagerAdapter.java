package com.app.demo.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.beans.MessageTypeBean;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
import com.app.shop.mylibrary.beans.EventMessage;


import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * @anthor : 大海
 * 每天进步一点点
 */


public class ManagerAdapter extends CommonAdapter {

    public ManagerAdapter(Context context, ArrayList datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setView(ViewHolder holder, Object o, int position) {
        MessageTypeBean bean = (MessageTypeBean) o;
        TextView textView = holder.getView(R.id.tv_list_item);
        TextView tv_del = holder.getView(R.id.tv_del);
        textView.setText(bean.getType());
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventMessage(EventMessage.DEL, holder.mPositon));
            }
        });
    }
}
