package com.app.problem_collect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.demo.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.http.I;

public class Problem_review extends AppCompatActivity{

    private MyDBOpenHelper myDBHelper;
    private SQLiteDatabase db;

    private Context mContext;
    private ReviewAdapter mAdapter=null;
    private ArrayList<HeroBean>mData=null;
    private ListView listView;

    private String gp_name;
    private String ch_name;
    private int reb[] = new int[]{1,2,3,8};

    private int now_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_review);
        mContext=Problem_review.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);
        listView=findViewById(R.id.review_list);

        InitList();
        InitItemClick();


    }

    private void InitList(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.query("problem", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            mData = new ArrayList<HeroBean>();
            do {

                String Ans_url = cursor.getString(cursor.getColumnIndex("personid"));
                String Groupname = cursor.getString(cursor.getColumnIndex("Groupname"));
                String Childname = cursor.getString(cursor.getColumnIndex("Childname"));
                String Pic_url = cursor.getString(cursor.getColumnIndex("Pic_url"));
                String data = cursor.getString(cursor.getColumnIndex("Date"));

                LocalDate then_date = LocalDate.parse(data, fmt);
                if(then_date.isAfter(LocalDate.now())) { }
                else{
                    mData.add(new HeroBean(Groupname,Childname,Pic_url,Ans_url));
                }
                }while (cursor.moveToNext());
            }
        mAdapter = new ReviewAdapter((ArrayList<HeroBean>) mData, mContext);
        listView.setAdapter(mAdapter);
        db.close();
    }

    private void InitItemClick()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Problem_review.this,Problem_Show.class);
                HeroBean heroBean=mData.get(position);
                gp_name=heroBean.getGp_name();
                ch_name=heroBean.getName();
                now_position=position;
                Bundle bd = new Bundle();
                bd.putString("uri_que", heroBean.getQuri());
                bd.putString("uri_ans", heroBean.getAuri());
                intent.putExtras(bd);
                //Toast.makeText(Problem_review.this,"AAAAAAA",Toast.LENGTH_SHORT).show();






                startActivityForResult(intent,25);

            }
        });
    }


    //重写返回响应
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 25:
                db = myDBHelper.getWritableDatabase();
                Cursor cursor = db.query("problem",null,"Groupname = ? and Childname=?",new String[]{gp_name,ch_name},null,null,null);
                if(cursor.moveToFirst())
                {
                    do{
                        updateDate(cursor);
                    }while (cursor.moveToNext());
                }

                mAdapter.remove(now_position);
                db.close();
                Log.d("review",gp_name+ch_name);

            }

        }


        //更新复习时间
        private void updateDate(Cursor cursor)
        {
            db = myDBHelper.getWritableDatabase();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            int num_level = cursor.getInt(cursor.getColumnIndex("Level"));
            if(num_level>3) {

                ContentValues values1=new ContentValues();
                values1.put("Date","3000-01-01");
                db.update("problem", values1, "Groupname = ? and Childname=?" ,new String[]{gp_name,ch_name});

            }
            else
            {

                LocalDate next_date = LocalDate.now();
                next_date=next_date.plusDays(reb[num_level]);
                String date_str = next_date.format(fmt);
                Log.d("review",date_str);
                Log.d("review",""+num_level);
                ContentValues values1=new ContentValues();
                values1.put("Level",num_level+1);
                values1.put("Date",date_str);
                db.update("problem", values1, "Groupname = ? and Childname=?" ,new String[]{gp_name,ch_name});
            }

        }
}