package com.fuandtan.ftaobao.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.adapter.WeitaoFragmentPagerAdapter;

public class WeitaoFragment extends Fragment {
    // 总布局
    private View weitaoView;

    // 状态栏布局
    private LinearLayout statusBar;

    // 标题栏布局
    private Toolbar toolbar;

    // 正文页卡布局
    private ViewPager viewPager;
    private WeitaoFragmentPagerAdapter weitaoFragmentPagerAdapter;
    private TabLayout tabLayout;

    // 标题栏属性


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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 总布局实例化
        weitaoView = inflater.inflate(R.layout.weitao_fragment, container, false);
        initView();//初始化布局
        return weitaoView;
    }

    private void initView() {
        initStatusBar();
        initToolbar();
        initViewPagerAndTabs();
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) weitaoView.findViewById(R.id.coordinatorLayout);
        coordinatorLayout.setFitsSystemWindows(true);
//        coordinatorLayout.setStatusBarBackgroundResource(R.drawable.actionbar_bg);
        coordinatorLayout.setBackgroundResource(R.drawable.actionbar_bg);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        toolbar = (Toolbar) weitaoView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(R.string.weitao);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }

    /**
     * 初始化页卡
     */
    private void initViewPagerAndTabs() {
        viewPager = (ViewPager) weitaoView.findViewById(R.id.viewPager);
        weitaoFragmentPagerAdapter = new WeitaoFragmentPagerAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.createInstance(666), getActivity().getResources().getString(R.string.weitao_dynamic));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.createInstance(9), getActivity().getResources().getString(R.string.weitao_new));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.createInstance(6), getActivity().getResources().getString(R.string.weitao_direct_seeding));
        weitaoFragmentPagerAdapter.addFragment(WeitaoSubFragment.createInstance(5), getActivity().getResources().getString(R.string.weitao_hot_topic));
        viewPager.setAdapter(weitaoFragmentPagerAdapter);

        tabLayout = (TabLayout) weitaoView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
