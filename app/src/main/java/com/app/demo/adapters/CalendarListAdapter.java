package com.app.demo.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.demo.R;
import com.app.demo.beans.MessageBean;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class CalendarListAdapter extends BaseQuickAdapter<MessageBean, CalendarListAdapter.ViewHolder> {

    public CalendarListAdapter(int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, MessageBean item) {

        helper.name.setText(StringUtil.getContent(item.getMessage()));
        helper.time.setText(StringUtil.getContent(item.getMessageTime()));

        long time_1 = TimeUtil.str2Long(item.getMessageTime(), "yyyy-MM-dd hh:mm");
        long time_now = System.currentTimeMillis();
        if (time_1 < time_now) {
            helper.tv_line.setVisibility(View.VISIBLE);
        } else {
            helper.tv_line.setVisibility(View.GONE);
        }

    }

    public class ViewHolder extends BaseViewHolder {

        TextView name;
        TextView time;
        TextView tv_line;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            time = view.findViewById(R.id.time);
            tv_line = view.findViewById(R.id.tv_line);
        }
    }
}
