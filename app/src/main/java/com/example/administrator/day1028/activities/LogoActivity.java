package com.example.administrator.day1028.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.day1028.R;
import com.example.administrator.day1028.biz.Xhttp;
import com.example.administrator.day1028.commom.IgnoreTypes;
import com.example.administrator.day1028.entity.NewsType;

import java.util.ArrayList;
import java.util.List;

public class LogoActivity extends AppCompatActivity implements Xhttp.NewsTypeListener {

    private NewsType newsType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Xhttp.getNewsTypeList(this);
    }
    public static void start(Context con) {
        Intent i = new Intent(con, LogoActivity.class);
        con.startActivity(i);
    }

    @Override
    public void onSuccess(NewsType newsType) {
        if(newsType!=null){
          this.newsType=newsType;
            ignore(newsType);
        }
    }

    @Override
    public void onFinish() {
        Bundle b=new Bundle();

    MainActivity.start(this,newsType);
        finish();
    }

    private void ignore(NewsType newsType) {
        List<NewsType.SubName> tobeDeleted = new ArrayList<>();
        for (int i = 0; i < IgnoreTypes.TYPES.length; i++) {
            for (int j = 0; j <newsType.tList.size(); j++) {
                if (IgnoreTypes.TYPES[i].equals(newsType.tList.get(j).tname)) {
                    tobeDeleted.add(newsType.tList.get(j));
                }
            }
        }
        newsType.tList.removeAll(tobeDeleted);
    }
}
