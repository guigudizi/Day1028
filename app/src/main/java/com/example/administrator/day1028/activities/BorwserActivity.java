package com.example.administrator.day1028.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.day1028.R;
import com.example.administrator.day1028.fragment.NewsContentFragment;

public class BorwserActivity extends AppCompatActivity {

    public static final String KEY_DOCID = "docId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borwser);
        replaceFragment(NewsContentFragment.newInstance(getIntent().getStringExtra(KEY_DOCID)),false);
    }

    public void replaceFragment(Fragment f,boolean isAddToBk){
         FragmentTransaction fr=getSupportFragmentManager().beginTransaction().replace(R.id.activity_borwser,f,f.getClass().getSimpleName());
        if(isAddToBk){
            fr.addToBackStack(null);
        }
        fr.commit();
    }
    public static void start(Context con,String docId){
        Intent i=new Intent(con,BorwserActivity.class);
        i.putExtra(KEY_DOCID,docId);
        con.startActivity(i);
    }
}
