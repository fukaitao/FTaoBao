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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weitao_item, null);
            holder = new ViewHolder();
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_subtitle = (TextView) convertView.findViewById(R.id.tv_subtitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundResource(R.color.white);

        WeitaoItem weitaoItem = weitaoItemList.get(position);
        holder.iv_photo.setImageResource(weitaoItem.getPhotoId());
        holder.tv_username.setText(weitaoItem.getUsername());
        holder.tv_title.setText(weitaoItem.getTitle());
        holder.tv_subtitle.setText(weitaoItem.getSubTitle());

        return convertView;
    }

    class ViewHolder {
        ImageView iv_photo;
        TextView tv_username;
        TextView tv_title;
        TextView tv_subtitle;
    }
}
