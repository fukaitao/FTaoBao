package com.fuandtan.ftaobao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
    }

//    public WeitaoFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> fragmentTitleList) {
//        super(fm);
//        this.fragmentList = fragmentList;
//        this.fragmentTitleList = fragmentTitleList;
//    }

    /**
     * 增加fragment
     *
     * @param fragment
     * @param title
     */
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    /**
     * @param position
     * @return 指定fragment
     */
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * @return 页卡量
     */
    @Override
    public int getCount() {
        return null == fragmentList ? 0 : fragmentList.size();
    }

    /**
     * @param position
     * @return fragment标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
