package com.app.problem_collect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.demo.R;

import java.util.ArrayList;

public class MsExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<GroupBean> gData;
    private ArrayList<ArrayList<HeroBean>> iData;
    private Context mContext;
    private ArrayList<HeroBean> lData;

    public MsExpandableListAdapter(ArrayList<GroupBean> gData, ArrayList<ArrayList<HeroBean>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public GroupBean getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public HeroBean getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderGroup groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.problem_group_item, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.group_name = (TextView) convertView.findViewById(R.id.group_name);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.group_name.setText(gData.get(groupPosition).getName());
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem itemHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.problem_hero_list_item, parent, false);
            itemHolder = new ViewHolderItem();
            //itemHolder.hero_icon = (ImageView) convertView.findViewById(R.id.hero_icon);
            itemHolder.hero_name = (TextView) convertView.findViewById(R.id.hero_name);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
        //itemHolder.hero_icon.setImageResource(iData.get(groupPosition).get(childPosition).getIcon());
        itemHolder.hero_name.setText(iData.get(groupPosition).get(childPosition).getName());
        return convertView;


    }
    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolderGroup{
        private TextView group_name;
    }

    private static class ViewHolderItem{
        private ImageView hero_icon;
        private TextView hero_name;
    }

    public void add_gp(String str) {
        if (gData == null) {
            gData = new ArrayList<GroupBean>();
        }
        gData.add(new GroupBean(str));
        lData=new ArrayList<HeroBean>();
        lData.add(new HeroBean("长按分组名新增错题"));
        iData.add(lData);

        notifyDataSetChanged();
    }

    public void add_ch(String name,String uri,int gp){
        ArrayList<HeroBean> add_new = iData.get(gp);
        add_new.add(new HeroBean(name, uri));
        notifyDataSetChanged();
    }

}
