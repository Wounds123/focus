package com.app.problem_collect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app.demo.R;

import uk.co.senab.photoview.PhotoView;

//单击错题选项进入的错题展示画面*****************************************************************************************************************
public class Problem_Show extends AppCompatActivity {

    Uri quri=null;
    Uri auri=null;
    String que=null;
    String ans=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_show);

        Bundle bd = getIntent().getExtras();

        que=bd.getString("uri_que");
       // Log.d("MainActivity",que);
        ans=bd.getString("uri_ans");
        //quri=Uri.parse(que);




        Button question=(Button)findViewById(R.id.question);
        Button answer=(Button)findViewById(R.id.answer);
        final PhotoView imageView=(PhotoView) findViewById(R.id.picture);
        if(que.equals("nothing")){
            imageView.setImageResource(R.drawable.nothing);
        }
        else{
            quri=Uri.parse(que);
            imageView.setImageURI(quri);
        }
        Context ctx=getBaseContext();

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(que.equals("nothing")){
                    imageView.setImageResource(R.drawable.nothing);
                }
                else{
                    quri=Uri.parse(que);
                    imageView.setImageURI(quri);
                }

            }
        });
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ans.equals("nothing")) {
                    imageView.setImageResource(R.drawable.nothing);
                } else {
                    auri = Uri.parse(ans);
                    imageView.setImageURI(auri);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //TODO something
        finish();
        super.onBackPressed();
    }
}
