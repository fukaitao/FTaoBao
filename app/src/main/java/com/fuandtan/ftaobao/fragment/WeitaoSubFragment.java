package com.fuandtan.ftaobao.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.adapter.WeitaoListViewAdapter;
import com.fuandtan.ftaobao.adapter.WeitaoRecyclerAdapter;
import com.fuandtan.ftaobao.listener.OnRefreshListener;
import com.fuandtan.ftaobao.model.WeitaoItem;
import com.fuandtan.ftaobao.view.WeitaoRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zexin.tan on 2017/5/25.
 */

public class WeitaoSubFragment extends Fragment implements OnRefreshListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "WEITAO";

    public static String ITEMS_COUNT_KEY = "WeitaoSubFragment$ItemsCount";
    // 页卡属性
    private WeitaoRefreshListView weitaoRefreshListView;
    private WeitaoItem weitaoItem;
    private List<WeitaoItem> weitaoItemList;
    private WeitaoListViewAdapter weitaoListViewAdapter;

    /**
     * 实例化WeitaoSubFragment
     *
     * @param itemCount
     * @return
     */
    public static WeitaoSubFragment newInstance(int itemCount) {
        if (DEBUG) Log.d(TAG, "WeitaoSubFragment.newInstance()");
        WeitaoSubFragment weitaoSubFragment = new WeitaoSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemCount);
        weitaoSubFragment.setArguments(bundle);
        return weitaoSubFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.d(TAG, "WeitaoSubFragment.onCreate()");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (DEBUG) Log.d(TAG, "WeitaoSubFragment.initData()");
        weitaoItemList = new ArrayList<WeitaoItem>();
        Bundle bundle = getArguments();
        int itemCount = 0;
        if (bundle != null) itemCount = bundle.getInt(ITEMS_COUNT_KEY);
        String str;
        for (int i = 1; i <= itemCount; i++) {
            str = "0000" + i;
            str = str.substring(str.length() - 3, str.length());
            weitaoItem = new WeitaoItem(R.drawable.ic_launcher, "第" + str + "项", "标题" + str, "副标题" + str);
            weitaoItemList.add(weitaoItem);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (DEBUG) Log.d(TAG, "WeitaoSubFragment.onCreateView()");
        View view = inflater.inflate(R.layout.weitao_viewpager1, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化布局
     */
    private void initView(View view) {
        if (DEBUG) Log.d(TAG, "WeitaoSubFragment.initView()");
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setBackgroundResource(R.color.green_light);// weitao_viewpager1背景色
//        WeitaoRecyclerAdapter weitaoRecyclerAdapter = new WeitaoRecyclerAdapter(weitaoItemList);
//        recyclerView.setAdapter(weitaoRecyclerAdapter);

        weitaoRefreshListView = (WeitaoRefreshListView) view.findViewById(R.id.weitaoRefreshListView);
        weitaoRefreshListView.setBackgroundResource(R.color.green_light);
        weitaoListViewAdapter = new WeitaoListViewAdapter(weitaoItemList);
        weitaoRefreshListView.setAdapter(weitaoListViewAdapter);
        weitaoRefreshListView.setOnRefreshListener(this);
    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (DEBUG) Log.d(TAG, "WeitaoSubFragment.onDownPullRefresh()--doInBackground()");
                SystemClock.sleep(2000);
                String str;
                for (int i = 1; i < 3; i++) {
                    str = "0000" + i;
                    str = str.substring(str.length() - 3, str.length());
                    weitaoItem = new WeitaoItem(R.drawable.ic_launcher, "上上上上上上拉刷新第" + str + "项", "标题" + str, "副标题" + str);
                    weitaoItemList.add(0, weitaoItem);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (DEBUG) Log.d(TAG, "WeitaoSubFragment.onDownPullRefresh()--onPostExecute()");
                weitaoListViewAdapter.notifyDataSetChanged();
                weitaoRefreshListView.hideHeaderView();// 控制头布局隐藏
            }
        }.execute(new Void[]{});
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (DEBUG) Log.d(TAG, "WeitaoSubFragment.onLoadingMore()--doInBackground()");
                SystemClock.sleep(1000);
                String str;
                for (int i = 1; i < 4; i++) {
                    str = "0000" + i;
                    str = str.substring(str.length() - 3, str.length());
                    weitaoItem = new WeitaoItem(R.drawable.ic_launcher, "下下下下下下拉加载第" + str + "项", "标题" + str, "副标题" + str);
                    weitaoItemList.add(weitaoItem);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (DEBUG) Log.d(TAG, "WeitaoSubFragment.onLoadingMore()--onPostExecute()");
                weitaoListViewAdapter.notifyDataSetChanged();
                weitaoRefreshListView.hideFooterView();// 控制脚布局隐藏
            }
        }.execute(new Void[]{});
    }
}
