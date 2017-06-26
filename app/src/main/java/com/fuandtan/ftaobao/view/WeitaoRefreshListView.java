package com.fuandtan.ftaobao.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.TextView;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.listener.OnRefreshListener;

import static com.fuandtan.ftaobao.R.id.tv_refresh_header;
import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by zexin.tan on 2017/6/2.
 */

public class WeitaoRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "WEITAO";

    private View headerView, footerView;// 头、脚刷新布局
    private TextView tv_refresh_header, tv_refresh_footer;// 头、脚布局文字状态
    private int headerViewHeight, footerViewHeight;// 头、脚刷新布局高度

    private final int DOWN_PULL_REFRESH = 0;// “下拉刷新”状态
    private final int RELEASE_REFRESH = 1;// “松开刷新”状态
    private final int REFRESHING = 2;// “正在刷新...”状态
    private final int DATA_LOAD_COMPLETED = 3;// “数据加载完毕”状态
    private int currentState = DOWN_PULL_REFRESH;//头布局状态，默认为下拉刷新状态

    private Animation upAnimation; // 向上旋转的动画
    private Animation downAnimation; // 向下旋转的动画

    private OnRefreshListener mOnRefershListener;
    private int firstVisibleItemPosition, lastVisibleItemPosition; // 屏幕显示在第一个、最后一个的item的索引
    private int downY;// 按下时y轴的偏移量
    private boolean isLoadingMore; // 是否正在加载更多中
    private boolean isScrollToBottom; // 是否滑动到底部

    public WeitaoRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.WeitaoRefreshListView()");
        initView();
        this.setOnScrollListener(this);
    }

    private void initView() {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.initView()");
        initHeader();
        initFooter();
    }

    /**
     * 初始化头刷新布局
     */
    private void initHeader() {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.initHeader()");
        headerView = View.inflate(getContext(), R.layout.refresh_header, null);
        tv_refresh_header = (TextView) headerView.findViewById(R.id.tv_refresh_header);

        headerView.measure(0, 0);//系统帮我们测量出headerView高度
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        this.addHeaderView(headerView);
    }

    /**
     * 初始化脚刷新布局
     */
    private void initFooter() {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.initFooter()");
        footerView = View.inflate(getContext(), R.layout.refresh_footer, null);
        tv_refresh_footer = (TextView) footerView.findViewById(R.id.tv_refresh_footer);

        footerView.measure(0, 0);// 系统会帮我们测量出headerView的高度
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        this.addFooterView(footerView);// 向ListView的底部添加一个view对象
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.onTouchEvent()--ACTION_DOWN");
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                // 移动中的y - 按下的y = 间距.
                int diff = (moveY - downY) / 2;
                // -头布局的高度 + 间距 = paddingTop
                int paddingTop = -headerViewHeight + diff;
                // 如果: -头布局的高度 > paddingTop的值 执行super.onTouchEvent(ev);
                if (firstVisibleItemPosition == 0 && -headerViewHeight < paddingTop) {
                    if (paddingTop > 0 && currentState == DOWN_PULL_REFRESH) { // 完全显示了——松开刷新
                        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.onTouchEvent()--ACTION_MOVE-松开刷新");
                        currentState = RELEASE_REFRESH;
                        refreshHeaderView();
                    } else if (paddingTop < 0 && currentState == RELEASE_REFRESH) { // 没有显示完全——下拉刷新
                        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.onTouchEvent()--ACTION_MOVE-下拉刷新");
                        currentState = DOWN_PULL_REFRESH;
                        refreshHeaderView();
                    }
                    // 下拉头布局
                    headerView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                // 判断当前的状态是松开刷新还是下拉刷新
                if (currentState == RELEASE_REFRESH) {
                    if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.onTouchEvent()--ACTION_UP-刷新数据");
                    Log.i(TAG, "刷新数据");
                    // 把头布局设置为完全显示状态
                    headerView.setPadding(0, 0, 0, 0);
                    // 进入“正在刷新...”状态
                    currentState = REFRESHING;
                    refreshHeaderView();

                    if (mOnRefershListener != null) {
                        mOnRefershListener.onDownPullRefresh(); // 调用使用者的监听方法
                    }
                } else if (currentState == DOWN_PULL_REFRESH) {
                    if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.onTouchEvent()--ACTION_UP-放弃下拉刷新");
                    // 隐藏头布局
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据currentState刷新头布局的状态
     */
    private void refreshHeaderView() {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.refreshHeaderView()");
        switch (currentState) {
            case DOWN_PULL_REFRESH: // “下拉刷新”状态
                tv_refresh_header.setText(R.string.down_pull_refresh);
                break;
            case RELEASE_REFRESH: // “松开刷新”状态
                tv_refresh_header.setText(R.string.release_refresh);
                break;
            case REFRESHING: // “正在刷新...”状态
                tv_refresh_header.setText(R.string.refreshing);
                break;
            case DATA_LOAD_COMPLETED: // “数据加载完毕”状态
                tv_refresh_header.setText(R.string.data_load_completed);
                break;
            default:
                break;
        }
    }

    /**
     * 当滚动状态改变时回调
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.onScrollStateChanged()");
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
            // 判断当前是否已经到了底部
            if (isScrollToBottom && !isLoadingMore) {
                isLoadingMore = true;
                // 当前到底部
                Log.i(TAG, "加载更多数据");
                footerView.setPadding(0, 0, 0, footerViewHeight);
                this.setSelection(this.getCount());

                if (mOnRefershListener != null) {
                    mOnRefershListener.onLoadingMore();
                }
            }
        }
    }

    /**
     * 当滚动时调用
     *
     * @param firstVisibleItem 当前屏幕显示在顶部的item的position
     * @param visibleItemCount 当前屏幕显示了多少个条目的总数
     * @param totalItemCount   ListView的总条目的总数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.onScroll()");
        firstVisibleItemPosition = firstVisibleItem;

        if (getLastVisiblePosition() == (totalItemCount - 1)) {
            isScrollToBottom = true;
        } else {
            isScrollToBottom = false;
        }
    }

    /**
     * 设置刷新监听事件
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefershListener = listener;
    }

    /**
     * 隐藏头刷新布局
     */
    public void hideHeaderView() {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.hideHeaderView()");
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        tv_refresh_header.setText(R.string.down_pull_refresh);
        currentState = DOWN_PULL_REFRESH;
    }

    /**
     * 隐藏脚刷新布局
     */
    public void hideFooterView() {
        if (DEBUG) Log.d(TAG, "WeitaoRefreshListView.hideFooterView()");
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        isLoadingMore = false;
    }
}
