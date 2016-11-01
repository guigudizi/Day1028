package com.example.administrator.day1028.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.administrator.day1028.R;
import com.example.administrator.day1028.entity.NewsType;
import com.example.administrator.day1028.fragment.FavorFragment;
import com.example.administrator.day1028.fragment.HomeFragment;
import com.example.administrator.day1028.fragment.HotFragment;
import com.example.administrator.day1028.fragment.NewsListFragment;
import com.example.administrator.day1028.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, NewsListFragment.OnFragmentInteractionListener {
    public static final String KEY_TYPELIST = "keylist";
    HomeFragment mHomeFragment;
    HotFragment mHotFragment;
    FavorFragment mFavorFragment;
    SettingFragment mSettingFragment;
    @BindView(R.id.main_rg1)
    RadioGroup mMainRg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mHomeFragment = HomeFragment.newinstance(getIntent().getExtras());
        mHotFragment = new HotFragment();
        mSettingFragment = new SettingFragment();
        mFavorFragment = new FavorFragment();
        replaceFragment(R.id.main_frm1, mHomeFragment);
        mMainRg1.setOnCheckedChangeListener(this);
    }


    public void replaceFragment(int con, Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        t.replace(con, f, f.getClass().getSimpleName());
        t.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_rbt0:
                replaceFragment(R.id.main_frm1, mHomeFragment);
                break;
            case R.id.main_rbt1:
                replaceFragment(R.id.main_frm1, mHotFragment);
                break;
            case R.id.main_rbt2:
                replaceFragment(R.id.main_frm1, mFavorFragment);
                break;
            case R.id.main_rbt3:
                replaceFragment(R.id.main_frm1, mSettingFragment);
                break;
        }
    }

    public static void start(Context context, NewsType ns) {

        Intent starter = new Intent(context, MainActivity.class);
        //对象必须可序列化
        starter.putExtra(KEY_TYPELIST,ns);
        context.startActivity(starter);
    }




    @Override
    public void onFragmentInteraction(String docId) {
        //Toast.makeText(this,docId+"", Toast.LENGTH_SHORT).show();
        BorwserActivity.start(this,docId);
    }
}
