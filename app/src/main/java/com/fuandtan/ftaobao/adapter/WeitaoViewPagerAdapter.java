package com.fuandtan.ftaobao.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fuandtan.ftaobao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zexin.tan on 2017/5/18.
 */

public class WeitaoViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> mViewList;

    public WeitaoViewPagerAdapter(Context context, List<View> viewList) {
        this.mContext = context;
        this.mViewList = viewList;
    }

    /**
     * 返回所有页卡的数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mViewList.size();
    }

    /**
     * 判断视图是否由对象产生
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 实例化页面
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        Toast.makeText(mContext,"position:"+position,Toast.LENGTH_SHORT).show();
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    /**
     * 删除页面
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
