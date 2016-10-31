package com.example.administrator.day1028.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.day1028.entity.NewsType;
import com.example.administrator.day1028.fragment.NewsListFragment;

/**
 * @author Administrator
 * @version $Rev$
 * @time 2016/10/28 17:53
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class NewsTypesPadapter extends FragmentPagerAdapter {
    NewsType newstype;

    public NewsTypesPadapter(FragmentManager fm) {
        super(fm);
    }

    public NewsTypesPadapter(FragmentManager frm, NewsType nt) {
        super(frm);
        this.newstype = nt;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(newstype.tList.get(position).tid, newstype.tList.get(position).tname);
    }

    @Override
    public int getCount() {
        return   newstype.tList == null ? 0 :newstype.tList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return newstype.tList.get(position).tname;
    }
}
