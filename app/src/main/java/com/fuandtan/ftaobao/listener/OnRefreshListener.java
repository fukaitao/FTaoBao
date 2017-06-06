package com.fuandtan.ftaobao.listener;

/**
 * Created by zexin.tan on 2017/6/5.
 */

public interface OnRefreshListener {
    /**
     * 下拉刷新
     */
    void onDownPullRefresh();

    /**
     * 上拉加载更多
     */
    void onLoadingMore();
}
