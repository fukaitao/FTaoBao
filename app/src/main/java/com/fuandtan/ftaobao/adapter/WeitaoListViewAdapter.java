package com.fuandtan.ftaobao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.model.WeitaoItem;

import java.util.List;

/**
 * Created by zexin.tan on 2017/6/5.
 */

public class WeitaoListViewAdapter extends BaseAdapter {
    private List<WeitaoItem> weitaoItemList;

    public WeitaoListViewAdapter(List<WeitaoItem> weitaoItemList) {
        this.weitaoItemList = weitaoItemList;
    }

    /**
     * @return 数据量
     */
    @Override
    public int getCount() {
        return null == weitaoItemList ? 0 : weitaoItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return weitaoItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weitao_item, null);
        convertView.setBackgroundResource(R.color.white);

        ImageView iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
        TextView tv_username = (TextView) convertView.findViewById(R.id.tv_username);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_subtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);

        WeitaoItem weitaoItem = weitaoItemList.get(position);
        iv_photo.setImageResource(weitaoItem.getPhotoId());
        tv_username.setText(weitaoItem.getUsername());
        tv_title.setText(weitaoItem.getTitle());
        tv_subtitle.setText(weitaoItem.getSubTitle());

        return convertView;
    }

}
