package com.fuandtan.ftaobao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.adapter.WeitaoRecyclerAdapter;
import com.fuandtan.ftaobao.model.WeitaoItem;

import java.util.ArrayList;
import java.util.List;

import static com.fuandtan.ftaobao.R.id.recyclerView;

/**
 * Created by zexin.tan on 2017/5/25.
 */

public class WeitaoSubFragment extends Fragment {
    public static String ITEMS_COUNT_KEY = "WeitaoSubFragment$ItemsCount";
    // 页卡属性
    private List<WeitaoItem> weitaoItemList;

    /**
     * 实例化WeitaoSubFragment
     *
     * @param itemCount
     * @return
     */
    public static WeitaoSubFragment newInstance(int itemCount) {
        Log.d("txxz", "WeitaoSubFragment.newInstance()");
        WeitaoSubFragment weitaoSubFragment = new WeitaoSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemCount);
        weitaoSubFragment.setArguments(bundle);
        return weitaoSubFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("txxz", "WeitaoSubFragment.onCreate()");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Log.d("txxz", "WeitaoSubFragment.initData()");
        weitaoItemList = new ArrayList<WeitaoItem>();
        Bundle bundle = getArguments();
        WeitaoItem weitaoItem;
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
        Log.d("txxz", "WeitaoSubFragment.onCreateView()");
        View view = inflater.inflate(R.layout.weitao_viewpager1, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化布局
     */
    private void initView(View view) {
        Log.d("txxz", "WeitaoSubFragment.initView()");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setBackgroundResource(R.color.green_light);// weitao_viewpager1背景色

        WeitaoRecyclerAdapter weitaoRecyclerAdapter = new WeitaoRecyclerAdapter(weitaoItemList);
        recyclerView.setAdapter(weitaoRecyclerAdapter);
    }
}
