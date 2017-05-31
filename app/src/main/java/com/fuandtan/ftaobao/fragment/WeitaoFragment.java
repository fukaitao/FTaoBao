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
    // 总布局
    private View weitaoView;

    // 标题栏布局
    private Toolbar toolbar;

    // 正文页卡布局
    private ViewPager viewPager;
    private WeitaoFragmentPagerAdapter weitaoFragmentPagerAdapter;
    private int[] iconRidSelected, iconRidUnselected;

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

    private void initData() {
        iconRidUnselected = new int[]{R.drawable.weitao_dynamic_normal, R.drawable.weitao_new_normal, R.drawable.weitao_direct_seeding_normal, R.drawable.weitao_hot_topic_normal};
        iconRidSelected = new int[]{R.drawable.weitao_dynamic_selected, R.drawable.weitao_new_selected, R.drawable.weitao_direct_seeding_selected, R.drawable.weitao_hot_topic_selected};
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("txxz", "WeitaoFragment.onCreateView()");
        // 总布局实例化
        weitaoView = inflater.inflate(R.layout.weitao_fragment, container, false);
        initView();//初始化布局
        return weitaoView;
    }

    private void initView() {
        Log.d("txxz", "WeitaoFragment.initView()");
        initStatusBar();
        initToolbar();
        initViewPagerAndTabs();
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        Log.d("txxz", "WeitaoFragment.initStatusBar()");
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) weitaoView.findViewById(R.id.coordinatorLayout);
        coordinatorLayout.setFitsSystemWindows(true);
//        coordinatorLayout.setStatusBarBackgroundResource(R.drawable.actionbar_bg);//失效，原因不明
        coordinatorLayout.setBackgroundResource(R.drawable.actionbar_bg);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        Log.d("txxz", "WeitaoFragment.initToolbar()");
        toolbar = (Toolbar) weitaoView.findViewById(R.id.tf_action_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        getActivity().setTitle(R.string.weitao);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }

    /**
     * 初始化页卡
     */
    private void initViewPagerAndTabs() {
        Log.d("txxz", "WeitaoFragment.initViewPagerAndTabs()");
        viewPager = (ViewPager) weitaoView.findViewById(R.id.viewPager);
        weitaoFragmentPagerAdapter = new WeitaoFragmentPagerAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(9), getActivity().getResources().getString(R.string.weitao_dynamic));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(666), getActivity().getResources().getString(R.string.weitao_new));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(6), getActivity().getResources().getString(R.string.weitao_direct_seeding));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.newInstance(2), getActivity().getResources().getString(R.string.weitao_hot_topic));
        viewPager.setAdapter(weitaoFragmentPagerAdapter);

        TabLayout tabLayout = (TabLayout) weitaoView.findViewById(R.id.tf_tab_bar);
        tabLayout.setupWithViewPager(viewPager);
        tabLayoutIconSwitch(tabLayout);
    }

    /**
     * TabLayout icon切换
     *
     * @param tabLayout
     */
    private void tabLayoutIconSwitch(TabLayout tabLayout) {
        initTabIcon(tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("txxz", "WeitaoFragment.tabLayoutIconSwitch().OnTabSelectedListener().onTabSelected()--" + tab.getPosition());
                tab.setIcon(getActivity().getResources().getDrawable(iconRidSelected[tab.getPosition()]));
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