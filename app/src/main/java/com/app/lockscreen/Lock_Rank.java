package com.app.lockscreen;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.demo.R;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Lock_Rank extends AppCompatActivity {


    private List<User_Name_List> mData = null;
    private Context mContext;
    private UserAdapter mAdapter = null;
    private ListView listView;
    private User_Name_List a ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock__rank);
        Bmob.initialize(this, "e01dc61baeb809789bfb422d9e1ca30c");
        mContext=Lock_Rank.this;
        InitList();

    }

    private void InitList(){
        listView = (ListView) findViewById(R.id.Rank_List);
        mData = new LinkedList<User_Name_List>();
        LinkedList<User_Name_List> us =new LinkedList<User_Name_List>();

        //List<User_Name_List>us=new LinkedList<>();

        BmobQuery<User_Name_List> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereNotEqualTo("User_Name", "yuugdghkjle");
        categoryBmobQuery.setLimit(100);
        categoryBmobQuery.order("-Lock_Time");
        categoryBmobQuery.findObjects(new FindListener<User_Name_List>() {
            @Override
            public void done(List<User_Name_List> object, BmobException e) {
                if (e == null) {
                    int maxlen=50<object.size()?50:object.size();
                    for(int i=0;i<maxlen;i++) {
                        Message msg  = new Message();
                        msg.what=1;
                        msg.obj=object.get(i);
                        handler.sendMessage(msg);

                    }
                    Message msg  = new Message();
                    msg.what=2;
                    handler.sendMessage(msg);

                    //Toast.makeText(Lock_Rank.this, ""+object.get(0).getUser_Name(), Toast.LENGTH_SHORT).show();

                } else {
                    //Log.e("BMOB", e.toString());
                    //Toast.makeText(Lock_Rank.this, "查询失败，返回objectId为：", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        BmobQuery<User_Name_List> categoryBmobQuery = new BmobQuery<>();
//        categoryBmobQuery.addWhereEqualTo("User_Name", "lucky");
//        categoryBmobQuery.findObjects(new FindListener<User_Name_List>() {
//            @Override
//            public void done(List<User_Name_List> object, BmobException e) {
//                if (e == null) {
//                    if(object.size()>0)
//                    {
//                        User_Name_List user_name_list=object.get(0);
//                        Toast.makeText(Lock_Rank.this, "已存在"+user_name_list, Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                        Toast.makeText(Lock_Rank.this, "不存在，", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    //Log.e("BMOB", e.toString());
//                    Toast.makeText(Lock_Rank.this, "查询失败，返回objectId为：", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



        /*Collections.sort(us, new Comparator<User_Name_List>() {

            @Override
            public int compare(User_Name_List o1, User_Name_List o2) {
                int i = -(o1.getLock_Time() - o2.getLock_Time());

                return i;
            }
        });


        int maxLen=10<us.size()?10:us.size();
        for(int i=0;i<maxLen;i++)
        {
            mData.add(us.get(i));
        }*/



        //mAdapter = new UserAdapter((LinkedList<User_Name_List>) mData, mContext);
        //listView.setAdapter(mAdapter);
    }
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    mData.add((User_Name_List) msg.obj);
                    break;
                case 2:
                    mAdapter = new UserAdapter((LinkedList<User_Name_List>) mData, mContext);
                    listView.setAdapter(mAdapter);
                    break;
                default:break;




            }
        }
    };
}