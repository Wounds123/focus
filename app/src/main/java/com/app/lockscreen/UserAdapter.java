package com.app.lockscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.demo.R;

import java.util.LinkedList;

public class UserAdapter extends BaseAdapter {
    private LinkedList<User_Name_List> mData;
    private Context mContext;

    public UserAdapter(LinkedList<User_Name_List> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.rank_item,parent,false);

        TextView user_name = (TextView) convertView.findViewById(R.id.user_name);
        TextView lock_time = (TextView) convertView.findViewById(R.id.lock_time);
        TextView rank_num = convertView.findViewById(R.id.rank_num);




        user_name.setText(mData.get(position).getUser_Name());
        lock_time.setText(""+mData.get(position).getLock_Time()+"min");
        int num = position+1;
        rank_num.setText(""+num);
        return convertView;
    }
}
