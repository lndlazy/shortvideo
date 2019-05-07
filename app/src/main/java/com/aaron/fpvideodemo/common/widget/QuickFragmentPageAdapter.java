package com.aaron.fpvideodemo.common.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by linaidao on 2019/4/28.
 */

public class QuickFragmentPageAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<T> mList;
    private String[] mStrings;

    /**
     * @param fm
     * @param list
     * @param titles PageTitles
     */
    public QuickFragmentPageAdapter(FragmentManager fm, List<T> list, String[] titles) {
        super(fm);
        mList = list;
        mStrings = titles;
    }
//    /**
//     * @param fm
//     * @param list
//     */
//    public QuickFragmentPageAdapter(FragmentManager fm, List<T> list) {
//        super(fm);
//        mList = list;
//    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings == null ? super.getPageTitle(position) : mStrings[position];
    }
}
