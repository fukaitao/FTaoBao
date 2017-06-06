package com.fuandtan.ftaobao.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.adapter.WeitaoFragmentPagerAdapter;

public class WeitaoFragment extends Fragment {
    // 正文页卡布局
    private int[] titleRid, iconRidSelected, iconRidUnselected;

    /**
     * fragment 生命周期：
     * onAttach -->
     * onCreate -->
     * onCreateView -->
     * onActivityCreated -->
     * onStart() -->
     * onResume------fragment active
     * onPause() -->
     * onStop() -->
     * onDestroyView() -->
     * onDestroy() -->
     * onDetach()-----fragment is Destroyed
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("txxz", "WeitaoFragment.onCreate()");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        titleRid = new int[]{R.string.weitao_dynamic, R.string.weitao_new, R.string.weitao_direct_seeding, R.string.weitao_hot_topic};
        iconRidUnselected = new int[]{R.drawable.weitao_dynamic_normal, R.drawable.weitao_new_normal, R.drawable.weitao_direct_seeding_normal, R.drawable.weitao_hot_topic_normal};
        iconRidSelected = new int[]{R.drawable.weitao_dynamic_selected, R.drawable.weitao_new_selected, R.drawable.weitao_direct_seeding_selected, R.drawable.weitao_hot_topic_selected};
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("txxz", "WeitaoFragment.onCreateView()");
        // 总布局实例化
        View view = inflater.inflate(R.layout.weitao_fragment, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化布局
     */
    private void initView(View view) {
        Log.d("txxz", "WeitaoFragment.initView()");
        initStatusBar(view);
        initToolbar(view);
        initViewPagerAndTabs(view);
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar(View view) {
        Log.d("txxz", "WeitaoFragment.initStatusBar()");
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
        coordinatorLayout.setFitsSystemWindows(true);
//        coordinatorLayout.setStatusBarBackgroundResource(R.drawable.actionbar_bg);//失效，原因不明
        coordinatorLayout.setBackgroundResource(R.drawable.actionbar_bg);// WeitaoFragment背景色
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(View view) {
        Log.d("txxz", "WeitaoFragment.initToolbar()");
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tf_action_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.setTitle(R.string.weitao);// 被xml配置文件属性所覆盖
//        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));// 被xml配置文件属性所覆盖
    }

    /**
     * 初始化页卡
     */
    private void initViewPagerAndTabs(View view) {
        Log.d("txxz", "WeitaoFragment.initViewPagerAndTabs()");
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        WeitaoFragmentPagerAdapter weitaoFragmentPagerAdapter = new WeitaoFragmentPagerAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(5), getActivity().getResources().getString(titleRid[0]));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(666), getActivity().getResources().getString(titleRid[1]));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(9), getActivity().getResources().getString(titleRid[2]));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(2), getActivity().getResources().getString(titleRid[3]));
        viewPager.setAdapter(weitaoFragmentPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tf_tab_bar);
        tabLayout.setupWithViewPager(viewPager);
        tabLayoutIconSwitch(viewPager, tabLayout);
    }

    /**
     * TabLayout icon切换
     *
     * @param tabLayout
     */
    private void tabLayoutIconSwitch(final ViewPager viewPager, final TabLayout tabLayout) {
        initTabIcon(tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("txxz", "WeitaoFragment.tabLayoutIconSwitch().OnTabSelectedListener().onTabSelected()--" + tab.getPosition());
                tab.setIcon(getActivity().getResources().getDrawable(iconRidSelected[tab.getPosition()]));
                viewPager.setCurrentItem(tab.getPosition());// 点击tabLayout切换view
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("txxz", "WeitaoFragment.tabLayoutIconSwitch().OnTabSelectedListener().onTabUnselected()--" + tab.getPosition());
                tab.setIcon(getActivity().getResources().getDrawable(iconRidUnselected[tab.getPosition()]));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("txxz", "WeitaoFragment.tabLayoutIconSwitch().OnTabSelectedListener().onTabReselected()--" + tab.getPosition());
            }
        });
    }

    /**
     * 初始化TabLayout icon
     *
     * @param tabLayout
     */
    private void initTabIcon(TabLayout tabLayout) {
        Log.d("txxz", "WeitaoFragment.initTabIcon()");
        for (int i = 0; i < tabLayout.getTabCount(); i++)
            if (i == tabLayout.getSelectedTabPosition())
                tabLayout.getTabAt(i).setIcon(getActivity().getResources().getDrawable(iconRidSelected[i]));
            else
                tabLayout.getTabAt(i).setIcon(getActivity().getResources().getDrawable(iconRidUnselected[i]));
    }
}
