package com.example.administrator.day1028.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.administrator.day1028.R;
import com.example.administrator.day1028.activities.MainActivity;
import com.example.administrator.day1028.adapters.NewsTypesPadapter;
import com.example.administrator.day1028.base.BaseFragment;
import com.example.administrator.day1028.entity.NewsType;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/28.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_indicator)
    TabPageIndicator mhi;
    @BindView(R.id.home_vpager)
    ViewPager mvp;
    private NewsTypesPadapter adp;

    @Override
    protected void initData() {
        Bundle b = getArguments();
        NewsType nt = (NewsType) b.getSerializable(MainActivity.KEY_TYPELIST);
        adp = new NewsTypesPadapter(getFragmentManager(),nt);
        mvp.setAdapter(adp);
        mhi.setViewPager(mvp);



    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home;
    }


    public static HomeFragment newinstance(Bundle b) {
        HomeFragment hf = new HomeFragment();
        hf.setArguments(b);
        return hf;
    }
}
