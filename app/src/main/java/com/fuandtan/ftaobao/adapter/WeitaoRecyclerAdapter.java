package com.fuandtan.ftaobao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.model.WeitaoItem;

import java.util.List;

/**
 * Created by Pareto909 on 2017/5/22.
 */

public class WeitaoRecyclerAdapter extends RecyclerView.Adapter<WeitaoRecyclerAdapter.WeitaoViewHolder> {
    private List<WeitaoItem> weitaoItemList;
    private Context context;

    public WeitaoRecyclerAdapter(List<WeitaoItem> weitaoItemList) {
        this.weitaoItemList = weitaoItemList;
    }

    /**
     * @param parent
     * @param viewType
     * @return ViewHolder中的View
     */
    @Override
    public WeitaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.weitao_item, parent, false);
        view.setBackgroundResource(R.color.white);// weitao_item背景色
        return new WeitaoViewHolder(view);
    }

    /**
     * 绑定holder中的view数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(WeitaoViewHolder holder, int position) {
        WeitaoItem weitaoItem = weitaoItemList.get(position);
        holder.iv_photo.setImageResource(weitaoItem.getPhotoId());
        holder.tv_username.setText(weitaoItem.getUsername());
        holder.tv_title.setText(weitaoItem.getTitle());
        holder.tv_subtitle.setText(weitaoItem.getSubTitle());
    }

    /**
     * @return 数据量
     */
    @Override
    public int getItemCount() {
        return null == weitaoItemList ? 0 : weitaoItemList.size();
    }

    /**
     * 初始化ViewHolder
     */
    public class WeitaoViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_photo;
        private TextView tv_username;
        private TextView tv_title;
        private TextView tv_subtitle;

        public WeitaoViewHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_subtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
        }
    }
}
