package com.app.demo.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.demo.R;
import com.app.demo.adapters.ManagerAdapter;
import com.app.demo.beans.MessageBean;
import com.app.demo.beans.MessageTypeBean;
import com.app.shop.mylibrary.base.BaseActivity;
import com.app.shop.mylibrary.base.CommonAdapter;
import com.app.shop.mylibrary.base.ViewHolder;
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
import de.greenrobot.event.EventBus;

public class HuishouzhanActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.rela_empty)
    RelativeLayout rela_empty;


    MyAdapter adapter;
    List<MessageBean> list_all = new ArrayList<>();
    List<MessageBean> list = new ArrayList<>();

    @BindView(R.id.tv_empty_title)
    TextView tvEmptyTitle;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huishouzhan);

        ButterKnife.bind(this);
        initData();
        tvEmptyTitle.setText("暂无类型");
    }

    private void initData() {

        list_all.clear();
        list.clear();
        list_all = DataSupport.findAll(MessageBean.class);

        for (int i = 0; i < list_all.size(); i++) {

            if (list_all.get(i).getIsDel() == 1) {
                list.add(list_all.get(i));
            }
        }
        adapter = new MyAdapter(this, (ArrayList) list, R.layout.item_list2);
        listview.setAdapter(adapter);
        showOrHideList();
    }


    @OnClick({R.id.imgv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgv_return:
                onBackPressed();
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
        }else if (msg.getMessageType() == EventMessage.HUANYUAN) {

            int position = (int) msg.getmObject();
            ContentValues values = new ContentValues();
            values.put("isDel", 0);
            DataSupport.updateAll(MessageBean.class, values, "message_id = ?",list.get(position).getMessage_id());

            list_all.clear();
            list.clear();
            list_all = DataSupport.findAll(MessageBean.class);
            for (int i = 0; i < list_all.size(); i++) {

                if (list_all.get(i).getIsDel() == 1) {
                    list.add(list_all.get(i));
                }
            }
            showOrHideList();
            EventBus.getDefault().post(new EventMessage(EventMessage.Refresh));
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
//                DataSupport.deleteAll(MessageBean.class, "type_id=?", list.get(position).getType_id());
                DataSupport.deleteAll(MessageBean.class, "message_id=?", list.get(position).getMessage_id());
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

    class MyAdapter extends CommonAdapter {

        public MyAdapter(Context context, ArrayList datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(ViewHolder holder, Object o, int position) {
            MessageBean bean = (MessageBean) o;
            TextView textView = holder.getView(R.id.tv_list_item);
            TextView tv_del = holder.getView(R.id.tv_del);
            TextView tv_huanyuan = holder.getView(R.id.tv_huanyuan);
            textView.setText(bean.getMessage());
            tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventMessage(EventMessage.DEL, holder.mPositon));
                }
            });

            tv_huanyuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventMessage(EventMessage.HUANYUAN, holder.mPositon));
                }
            });
        }
    }

}
