package com.app.demo.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.adapters.ManagerAdapter;
import com.app.demo.beans.MessageTypeBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.beans.EventMessage;
import com.app.shop.mylibrary.utils.DialogUtil;
import com.app.shop.mylibrary.utils.UserManager;
import com.app.shop.mylibrary.widgts.CustomDialog;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeManageActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.rela_empty)
    RelativeLayout rela_empty;


    ManagerAdapter adapter;
    List<MessageTypeBean> list_all = new ArrayList<>();
    List<MessageTypeBean> list = new ArrayList<>();

    @BindView(R.id.tv_empty_title)
    TextView tvEmptyTitle;
    private CustomDialog customDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_manage);
        ButterKnife.bind(this);
        initData();
        tvEmptyTitle.setText("暂无类型，快快添加吧~");
    }

    private void initData() {

        list_all.clear();
        list.clear();
        list_all = DataSupport.findAll(MessageTypeBean.class);

        for (int i = 0; i < list_all.size(); i++) {
            if (list_all.get(i).getUser_id().equals(UserManager.getUserId(this))) {
                list.add(list_all.get(i));
            }
        }
        adapter = new ManagerAdapter(this, (ArrayList) list, R.layout.item_list);
        listview.setAdapter(adapter);
        showOrHideList();
    }


    @OnClick({R.id.imgv_return, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
                break;

            case R.id.tv_add:
                showActivity(this, AddTypeActivity.class);
                break;
        }

    }


    @Override
    public void onEvent(EventMessage msg) {
        super.onEvent(msg);
        if (msg.getMessageType() == EventMessage.Refresh) {
            initData();

        } else if (msg.getMessageType() == EventMessage.DEL) {
            int position = (int) msg.getmObject();
            del(position);
        }
    }


    public void del(int position) {
        customDialog = DialogUtil.showDialog(this, customDialog, 2, "删除？", "删除该项", "取消", "是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataSupport.deleteAll(MessageTypeBean.class, "type_id=?", list.get(position).getType_id());
                list.remove(position);
                adapter.notifyDataSetChanged();
                showOrHideList();
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

    private void showOrHideList() {
        if (list.size() > 0) {
            listview.setVisibility(View.VISIBLE);
            rela_empty.setVisibility(View.GONE);
        } else {
            listview.setVisibility(View.GONE);
            rela_empty.setVisibility(View.VISIBLE);
        }
    }

}
