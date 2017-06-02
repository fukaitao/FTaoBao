package com.fuandtan.ftaobao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.AbsListView;

import com.fuandtan.ftaobao.R;

/**
 * Created by zexin.tan on 2017/6/2.
 */

public class WeitaoRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private View header, footer;// 头部和底部刷新布局
    private int DOWN_PULL_REFRESH = 0;// 下拉刷新
    private int DATA_LOAD_COMPLETED = 2;// 数据加载完毕
    private int RELEASE_REFRESH = 3;// 松开刷新
    private int REFRESHING = 4;// 正在刷新...

    public WeitaoRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        initHeader();
        initFooter();
    }

    private void initHeader() {
        header = View.inflate(getContext(), R.layout.refresh_header, null);
    }

    private void initFooter() {
        header = View.inflate(getContext(), R.layout.refresh_footer, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
