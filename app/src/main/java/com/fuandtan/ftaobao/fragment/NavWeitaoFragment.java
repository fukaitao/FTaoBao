package com.fuandtan.ftaobao.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.adapter.WeitaoViewPagerAdapter;
import com.fuandtan.ftaobao.adapter.WeitaoViewPagerItemAdapter1;
import com.fuandtan.ftaobao.model.WeitaoItem;
import com.fuandtan.ftaobao.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NavWeitaoFragment extends Fragment implements Animator.AnimatorListener {
    // 总布局
    private View weitaoView;

    // 标题栏布局
    private LinearLayout tf_app_bar_layout;
    private LinearLayout tf_action_bar_layout;
    private RelativeLayout tf_action_bar;
    private LinearLayout tf_tab_bar;
    // tab_bar图标
    private ImageView iv_weitao_dynamic;
    private ImageView iv_weitao_new;
    private ImageView iv_weitao_direct_seeding;
    private ImageView iv_weitao_hot_topic;
    //tab_bar文字
    private TextView tv_weitao_dynamic;
    private TextView tv_weitao_new;
    private TextView tv_weitao_direct_seeding;
    private TextView tv_weitao_hot_topic;

    // 数据页卡布局
    private ViewPager viewPager;
    private List<View> weitaoViewList;
    private View weitaoView1;
    private View weitaoView2;
    private View weitaoView3;
    private View weitaoView4;
    private WeitaoViewPagerAdapter weitaoViewPagerAdapter;// 页卡适配器

    // 数据页布局
    private List<WeitaoItem> weitaoItemList;
    private RecyclerView recyclerView;
    private WeitaoViewPagerItemAdapter1 weitaoViewPagerItemAdapter1;// 数据页构造器

    // 状态栏布局
    private LinearLayout statusBar;
    // 标题栏
    private boolean toTop;
    private boolean isActionbarHide;
    private boolean mIsAnim;
    private static int TIME_ANIMATION = 800;

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
        initData();//初始化数据
    }

    private void initData() {
        weitaoItemList = new ArrayList<WeitaoItem>();
        WeitaoItem weitaoItem;
        String str;
        for (int i = 0; i < 333; i++) {
            str = "0000" + i;
            str = str.substring(str.length() - 3, str.length());
            weitaoItem = new WeitaoItem(R.drawable.ic_launcher, "第" + str + "项", "标题" + i, "副标题" + i);
            weitaoItemList.add(weitaoItem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 总布局实例化
        weitaoView = inflater.inflate(R.layout.nav_weitao_fragment, container, false);
        initView();//初始化布局
        return weitaoView;
    }

    private void initView() {
        //得到状态栏布局对象
        statusBar = (LinearLayout) weitaoView.findViewById(R.id.ll_normal_status_bar);
        //动态de设置状态高度为得到的状态栏高度
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
        params.height = Utils.getStatusBarHeight(getActivity());
        statusBar.setLayoutParams(params);

        tf_app_bar_layout = (LinearLayout) weitaoView.findViewById(R.id.tf_app_bar_layout);// statusbar + actionbar + tabbar

        // 标题栏布局实例化
        tf_action_bar_layout = (LinearLayout) weitaoView.findViewById(R.id.tf_action_bar_layout);// actionbar + tabbar
        tf_action_bar = (RelativeLayout) weitaoView.findViewById(R.id.tf_action_bar);
        tf_tab_bar = (LinearLayout) weitaoView.findViewById(R.id.tf_tab_bar);
        // tab_bar图标实例化
        iv_weitao_dynamic = (ImageView) weitaoView.findViewById(R.id.iv_weitao_dynamic);
        iv_weitao_new = (ImageView) weitaoView.findViewById(R.id.iv_weitao_new);
        iv_weitao_direct_seeding = (ImageView) weitaoView.findViewById(R.id.iv_weitao_direct_seeding);
        iv_weitao_hot_topic = (ImageView) weitaoView.findViewById(R.id.iv_weitao_hot_topic);
        //tab_bar文字实例化
        tv_weitao_dynamic = (TextView) weitaoView.findViewById(R.id.tv_weitao_dynamic);
        tv_weitao_new = (TextView) weitaoView.findViewById(R.id.tv_weitao_new);
        tv_weitao_direct_seeding = (TextView) weitaoView.findViewById(R.id.tv_weitao_direct_seeding);
        tv_weitao_hot_topic = (TextView) weitaoView.findViewById(R.id.tv_weitao_hot_topic);

        // 页卡初始化
        weitaoViewList = new ArrayList<View>();
        weitaoView1 = View.inflate(getActivity(), R.layout.nav_weitao_viewpager1, null);
        weitaoView2 = View.inflate(getActivity(), R.layout.nav_weitao_viewpager2, null);
        weitaoView3 = View.inflate(getActivity(), R.layout.nav_weitao_viewpager3, null);
        weitaoView4 = View.inflate(getActivity(), R.layout.nav_weitao_viewpager4, null);
        weitaoViewList.add(weitaoView1);
        weitaoViewList.add(weitaoView2);
        weitaoViewList.add(weitaoView3);
        weitaoViewList.add(weitaoView4);
        weitaoViewPagerAdapter = new WeitaoViewPagerAdapter(getActivity(), weitaoViewList);

        viewPager = (ViewPager) weitaoView.findViewById(R.id.tf_tab_page);
        viewPager.setAdapter(weitaoViewPagerAdapter);

        initWeitaoView1();
    }

    private void initWeitaoView1() {
        // 页面初始化
        recyclerView = (RecyclerView) weitaoView1.findViewById(R.id.rv_weitao_viewpager1);
        // 设置布局显示方式，这里我使用都是垂直方式——LinearLayoutManager.VERTICAL
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        // 设置添加删除item的时候的动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        weitaoViewPagerItemAdapter1 = new WeitaoViewPagerItemAdapter1(getActivity(), weitaoItemList);
        recyclerView.setAdapter(weitaoViewPagerItemAdapter1);
//        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.d("txxz", "scrollX:" + scrollX + "  scrollY:" + scrollY + "  oldScrollX:" + oldScrollX + "  oldScrollY:" + oldScrollY);
//            }
//        });
//    }

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("txxz", "onScrollStateChanged()--newState=" + newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向下滚动
                    if (lastVisibleItem == (totalItemCount - 1) && !toTop) {
                        //加载更多功能的代码
                        Toast.makeText(getActivity(), "客官别扯了，我们是有底线的", Toast.LENGTH_SHORT).show();
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Log.d("txxz", "onScrollStateChanged()--toTop:" + toTop + "  isActionbarHide:" + isActionbarHide);
                    if (!toTop && !isActionbarHide) {// 向下滑，隐藏标题栏
                        Log.d("txxz", "onScrollStateChanged()--SCROLL_STATE_DRAGGING--向下滑");
                        ObjectAnimator animator1 = ObjectAnimator.ofFloat(tf_action_bar, "alpha", 1.0f, 0.0f);
                        ObjectAnimator animator2 = ObjectAnimator.ofFloat(tf_tab_bar, "translationY", 0.0f, -tf_action_bar.getHeight());
                        initAnimator(animator1);
                        initAnimator(animator2);
                        isActionbarHide = !isActionbarHide;
                    } else if (toTop && isActionbarHide) {// 向上滑，显示标题栏
                        Log.d("txxz", "onScrollStateChanged()--SCROLL_STATE_DRAGGING--向上滑");
                        ObjectAnimator animator1 = ObjectAnimator.ofFloat(tf_action_bar, "alpha", 0.0f, 1.0f);
                        ObjectAnimator animator2 = ObjectAnimator.ofFloat(tf_tab_bar, "translationY", -tf_action_bar.getHeight(), 0.0f);
                        initAnimator(animator1);
                        initAnimator(animator2);
                        isActionbarHide = !isActionbarHide;
                    } else if (!toTop && isActionbarHide) {
                        Toast.makeText(getActivity(), "上滑后再向下滑", Toast.LENGTH_SHORT).show();
                    } else if (toTop && !isActionbarHide) {
                        Toast.makeText(getActivity(), "下滑后再向上滑", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Toast.makeText(getActivity(), "dy:" + dy, Toast.LENGTH_SHORT).show();
                Log.d("txxz", "onScrolled()--toTop:" + toTop + "  isActionbarHide:" + isActionbarHide
                        + "  dy:" + dy);
                if (dy > 0) {// 向下滑，隐藏标题栏
                    toTop = false;
                } else if (dy < 0) {// 向上滑，显示标题栏
                    toTop = true;
                }
            }
        });
    }

    private void initAnimator(Animator animator) {
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(TIME_ANIMATION);
        animator.start();
        animator.addListener(NavWeitaoFragment.this);
    }

    /**
     * 设置标题栏变化高度
     *
     * @param page
     */
    public void setMarginTop(int page) {
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParam.setMargins(0, page, 0, 0);
        viewPager.setLayoutParams(layoutParam);
        viewPager.invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Log.d("txxz", "onAnimationEnd()--toTop:" + toTop + "  isActionbarHide:" + isActionbarHide
                + "  tf_app_bar_layout.getHeight():" + tf_app_bar_layout.getHeight() + "  tf_action_bar.getHeight():" + tf_action_bar.getHeight());
        if (toTop)
            setMarginTop(tf_app_bar_layout.getHeight());
        else
            setMarginTop(tf_app_bar_layout.getHeight() - tf_action_bar.getHeight());
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
