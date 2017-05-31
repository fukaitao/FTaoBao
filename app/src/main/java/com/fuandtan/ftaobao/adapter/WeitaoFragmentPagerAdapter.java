package com.fuandtan.ftaobao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zexin.tan on 2017/5/24.
 */

public class WeitaoFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> fragmentTitleList = new ArrayList<String>();

    public WeitaoFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.d("txxz", "WeitaoFragmentPagerAdapter.WeitaoFragmentPagerAdapter()");
    }

    public WeitaoFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> fragmentTitleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fragmentTitleList = fragmentTitleList;
    }

    /**
     * 增加fragment
     *
     * @param fragment
     * @param title
     */
    public void addFragment(Fragment fragment, String title) {
        Log.d("txxz", "WeitaoFragmentPagerAdapter.addFragment2()");
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    /**
     * @param position
     * @return 指定fragment
     */
    @Override
    public Fragment getItem(int position) {
        Log.d("txxz", "WeitaoFragmentPagerAdapter.getItem()--position:" + position);
        return fragmentList.get(position);
    }

    /**
     * @return 页卡量
     */
    @Override
    public int getCount() {
        Log.d("txxz", "WeitaoFragmentPagerAdapter.getCount()");
        return null == fragmentList ? 0 : fragmentList.size();
    }

    /**
     * @param position
     * @return fragment标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d("txxz", "WeitaoFragmentPagerAdapter.getPageTitle()");
        return fragmentTitleList.get(position);
    }
}
