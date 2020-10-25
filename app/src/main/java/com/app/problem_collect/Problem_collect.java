package com.app.problem_collect;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.app.demo.R;
import com.app.shop.mylibrary.utils.StringUtil;
import com.app.shop.mylibrary.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class Problem_collect extends AppCompatActivity {

    private ArrayList<GroupBean> gData = null;
    private ArrayList<ArrayList<HeroBean>> iData = null;
    private ArrayList<HeroBean> lData = null;
    private Context mContext;
    public String re = null;
    private ExpandableListView lol_hero_list;
    private MsExpandableListAdapter msAdapter = null;
    private HeroBean h;
    private Uri imageUri;
    private MyDBOpenHelper myDBHelper;
    private SQLiteDatabase db;
    private String db_group_name=null;
    private String db_child_name=null;
//对话框*************************************************
    private AlertDialog.Builder builder = null;
    private View view_custom;
    private AlertDialog alert = null;
    private String new_gp_name=null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_activity_main);
        mContext = Problem_collect.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);
        lol_hero_list = (ExpandableListView) findViewById(R.id.lol_hero_list);



        //获取需要的权限
        if(ContextCompat.checkSelfPermission(Problem_collect.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);





        //设置分组添加按钮**********************************************************************************
        Button gp_add=(Button)findViewById(R.id.problem_gp_add);
        gp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });

        //设置分组添加按钮**********************************************************************************




 //数据初始化**************************************************************************************************************************************************
        db = myDBHelper.getWritableDatabase();
        gData = new ArrayList<GroupBean>();
        iData = new ArrayList<ArrayList<HeroBean>>();


        //初始化错题列表，从本地数据库取出错题数据
        lData = new ArrayList<HeroBean>();
        Cursor cursorgp = db.query("gp_divide", null, null, null, null, null, null);
        Cursor cursor = db.query("problem", null, null, null, null, null, null);
        if(cursorgp.moveToFirst()) {
            do {
                String gpname = cursorgp.getString(cursorgp.getColumnIndex("Groupname"));
                gData.add(new GroupBean(gpname));
                if (cursor.moveToFirst()) {
                    lData = new ArrayList<HeroBean>();
                    do {

                        String Ans_url = cursor.getString(cursor.getColumnIndex("personid"));
                        String Groupname = cursor.getString(cursor.getColumnIndex("Groupname"));
                        String Childname = cursor.getString(cursor.getColumnIndex("Childname"));
                        String Pic_url = cursor.getString(cursor.getColumnIndex("Pic_url"));

                        if(gpname.equals(Groupname)){
                            lData.add(new HeroBean(Childname,Pic_url,Ans_url));
                        }
                    } while (cursor.moveToNext());
                    iData.add(lData);
                }
            }while (cursorgp.moveToNext());
        }
        cursor.close();
        cursorgp.close();
        //初始化错题列表，从本地数据库取出错题数据



        msAdapter = new MsExpandableListAdapter(gData, iData, mContext);
        lol_hero_list.setAdapter(msAdapter);

        //对话框**************************************************************************************
        //初始化Builder
        builder = new AlertDialog.Builder(mContext);

        //加载自定义的那个View,同时设置下
        final LayoutInflater inflater = Problem_collect.this.getLayoutInflater();
        view_custom = inflater.inflate(R.layout.view_dialog_custom, null,false);
        builder.setView(view_custom);
        builder.setCancelable(false);
        alert = builder.create();

        final EditText e=(EditText)view_custom.findViewById(R.id.edit_gp);


        view_custom.findViewById(R.id.gp_addcancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        view_custom.findViewById(R.id.gp_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_gp_name=e.getText().toString();
                if (StringUtil.isEmpty(new_gp_name)) {
                    ToastUtil.showToast(Problem_collect.this, "请输入错题分组名");
                    return;
                }
                else {
                    msAdapter.add_gp(new_gp_name);

                    msAdapter.notifyDataSetChanged();
                    ContentValues values1=new ContentValues(),values2=new ContentValues();
                    values1.put("Groupname",new_gp_name);
                    db.insert("gp_divide",null,values1);
                    values2.put("Groupname",new_gp_name);
                    values2.put("Childname","长按分组名新增错题");
                    values2.put("Pic_url","nothing");
                    values2.put("personid","nothing");
                    values2.put("Date","3000-01-01");
                    db.insert("problem",null,values2);
                    alert.dismiss();
                }
            }
        });
//对话框******************************************************************************************************************
        //为列表设置点击事件单击事件---------------------------------------------------------------------------------------------------------

        lol_hero_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(Problem_collect.this, Problem_Show.class);
                HeroBean heroBean = iData.get(groupPosition).get(childPosition);
                Bundle bd = new Bundle();
                bd.putString("uri_que", heroBean.getQuri());
                //Log.d("MainActivity",heroBean.getQuri());
                bd.putString("uri_ans", heroBean.getAuri());
                intent.putExtras(bd);
                startActivity(intent);
                //Toast.makeText(mContext, heroBean.getAuri(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
//-----------------------------------------------------------------------------------------------------------------------------------------------


        //为列表设置长按事件-------------------------------------------------------------------------------------------------------------------------------
        lol_hero_list.setOnItemLongClickListener(onItemLongClickListener);


    }


    //为列表设置长按事件的函数***********************************************************************************************************************************
    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            final long packedPosition = lol_hero_list.getExpandableListPosition(position);
            final int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
            final int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
            //长按的是group的时候，childPosition = -1

            //长按错题子项删除或修改答案
            if (childPosition != -1) {
                initPopWindow(view, groupPosition, childPosition);

                lol_hero_list.collapseGroup(groupPosition);
                lol_hero_list.expandGroup(groupPosition);

            }
            //长按错题子项删除或修改答案

            //长按组名增加错题
            else {
                Intent intent = new Intent(Problem_collect.this, Problem_Add.class);
                Bundle bd = new Bundle();
                bd.putInt("GP", groupPosition);
                intent.putExtras(bd);
                startActivityForResult(intent, 1);

            }
            //长按组名增加错题

           /* if(editText.equals("nothing")){
                Toast.makeText(getApplicationContext(), "创建取消", Toast.LENGTH_SHORT).show();
            }else{
                ArrayList<HeroBean>a_dd=iData.get(groupPosition);
                a_dd.add(new HeroBean(R.drawable.lol_icon3,editText));
            }*/
            return true;

        }

    };

//为列表设置长按事件的函数*****************************************************************************************************************************************************************


    //长按事件出现的悬浮窗******************************************************************************************************************
    private void initPopWindow(View v, final int groupPosition, final int childPosition) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.problem_item_popip, null, false);
        Button btn_xixi = (Button) view.findViewById(R.id.btn_xixi);
        Button btn_ans = (Button) view.findViewById(R.id.btn_ans);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);


        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件


        //删除错题
        btn_xixi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HeroBean> a = iData.get(groupPosition);
                h = iData.get(groupPosition).get(childPosition);
                String auri=h.getAuri(),quri=h.getQuri();
                db.delete("problem", "Groupname = ? and Childname=?", new String[]{gData.get(groupPosition).getName(),h.getName()});
                deleteUri(mContext,auri);
                deleteUri(mContext,quri);
                a.remove(childPosition);
                if(msAdapter.getChildrenCount(groupPosition)==0) {
                    db.delete("gp_divide","Groupname=?",new String[]{gData.get(groupPosition).getName()});
                    gData.remove(groupPosition);

                }
                lol_hero_list.collapseGroup(groupPosition);
                lol_hero_list.expandGroup(groupPosition);
                popWindow.dismiss();
            }
        });
        //删除错题

        //修改答案
        btn_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h = iData.get(groupPosition).get(childPosition);
                db_group_name=gData.get(groupPosition).getName();
                db_child_name=h.getName();
                File outputImage = new File(getExternalFilesDir("DirectoryPictures"), System.currentTimeMillis() + ".jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(Problem_collect.this, "com.app.demo.FileProvider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 5);
                popWindow.dismiss();

            }
        });
        //修改答案


    }
//长按事件出现的悬浮窗**************************************************************************************************************************


    //启动添加错题，答案活动的返回响应********************************************************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {
                    String ssssss="nothing";
                    Bundle bd = data.getExtras();
                    int gp = bd.getInt("GP");
                    //ArrayList<HeroBean> add_new = iData.get(gp);
                    //add_new.add(new HeroBean(bd.getString("name"), bd.getString("uri")));
                    msAdapter.add_ch(bd.getString("name"),bd.getString("uri"),gp);
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate today = LocalDate.now();
                    today=today.plusDays(1);
                    ContentValues values1 = new ContentValues();
                    values1.put("Groupname", gData.get(gp).getName());
                    values1.put("Childname",bd.getString("name"));
                    values1.put("Pic_url",bd.getString("uri"));
                    values1.put("personid",ssssss);
                    String dateStr = today.format(fmt);
                    values1.put("Date",dateStr);
                    values1.put("Level",0);
                    //参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据
                    db.insert("problem", null, values1);

                    //Log.d("MainActivity", bd.getString("uri"));
                    lol_hero_list.collapseGroup(gp);
                    lol_hero_list.expandGroup(gp);
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    h.setAuri(imageUri.toString());
                    ContentValues values1=new ContentValues();
                    values1.put("personid",imageUri.toString());
                    db.update("problem", values1, "Groupname = ? and Childname=?" ,new String[]{db_group_name,db_child_name});
                    //Toast.makeText(mContext, db_child_name+db_group_name, Toast.LENGTH_SHORT).show();

                }
                break;
            default:
        }
    }


//启动添加错题，答案活动的返回响应********************************************************************************


//根据uri删除文件**************************************************************************************************************
public void deleteUri(Context context, String suri) {

    if(suri==null) return;
    Uri uri=Uri.parse(suri);
    if (uri.toString().startsWith("content://")) {
        // content://开头的Uri
        context.getContentResolver().delete(uri, null, null);
    } else {
        File file = new File(getRealFilePath(context,uri));
        if (file.exists()&& file.isFile()){
            file.delete();
        }
    }
}

 public static String getRealFilePath(final Context context, final Uri uri ) {
     if ( null == uri ) return null;
     final String scheme = uri.getScheme();
     String data = null;
     if ( scheme == null )
         data = uri.getPath();
     else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
         data = uri.getPath();
     } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
         Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
         if ( null != cursor ) {
             if ( cursor.moveToFirst() ) {
                 int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                 if ( index > -1 ) {
                     data = cursor.getString( index );
                 }
             }
             cursor.close();
         }
     }
     return data;
 }

    //根据uri删除文件**************************************************************************************************************
////菜单********************************************************************************************
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
//
//        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
//
//    }
//    /**
//     *菜单的点击事件
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.id_add_item:
//                alert.show();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//
//    //菜单********************************************************************************

}



