package com.example.administrator.day1028.base;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/11/1.
 * 中间层，只实现懒加载功能，在和viewpager配合使用时，可以使用此父类
 */

public abstract class LazyBaseFragment extends BaseFragment {
    protected boolean isVisible, isPrepared, hasLoaded;//是否可见，视图是否已经创建完毕，数据是否已经加载完成
    //是否需要更新数据
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
        isVisible = isVisibleToUser;
        if (!isVisible || !isPrepared || hasLoaded) {
            return;
        }
        //加载数据
        hasLoaded = lazyLoad();//子类要实现的具体加载数据的方法

    }

    protected abstract boolean lazyLoad();

    @Override
    protected void initData() {
        isPrepared = true;
        if (!isVisible || !isPrepared || hasLoaded) {
            return;//不可见状态，和视图未准备好，或者已经加载过，都不进行数据加载
        }
        hasLoaded = lazyLoad();

    }

}
