package com.app.problem_collect;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {super(context, "my.db", null, 1); }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {




        String ans="Ans_uri";
        db.execSQL("CREATE TABLE problem(personid CHAR(10000),Groupname VARCHAR(100),Childname VARCHAR(100),Pic_url VARCHAR(10000),Date VARCHAR(100),Level Integer)");
        db.execSQL("CREATE TABLE gp_divide(Groupname VARCHAR(100))");
        for(int i=0;i<5;i++) {
            ContentValues values1 = new ContentValues();
            values1.put("Groupname", "点击右下角添加新错题分类");
            values1.put("Childname", "长按分组名新增错题"+i);
            values1.put("Pic_url", "nothing");
            values1.put("personid", "nothing");
            values1.put("Date", "3000-01-01");
            values1.put("Level", 0);

            db.insert("problem", null, values1);
        }
        ContentValues values2 = new ContentValues();
        values2.put("Groupname","点击右下角添加新错题分类");
        db.insert("gp_divide",null,values2);

    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }
}
