package com.example.administrator.day1028.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.day1028.R;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

                LogoActivity.start(GuideActivity.this);
                finish();


        }
}
