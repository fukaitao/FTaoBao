package com.fuandtan.ftaobao.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.model.WeitaoItem;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Pareto909 on 2017/5/22.
 */

public class WeitaoViewPagerItemAdapter1 extends RecyclerView.Adapter<WeitaoViewPagerItemAdapter1.WeitaoViewHolder> {
    private Context context;
    private List<WeitaoItem> weitaoItemList;
    private LayoutInflater layoutInflater;

    public WeitaoViewPagerItemAdapter1(Context context, List<WeitaoItem> weitaoItemList) {
        this.context = context;
        this.weitaoItemList = weitaoItemList;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 初始化ViewHolder中的View
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public WeitaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.nav_weitao_viewpager_item, parent, false);
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
     * 返回数据量
     *
     * @return
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
