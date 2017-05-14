package com.fuandtan.ftaobao.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.utils.Utils;

import java.util.List;

/**
 * Created by kaitao.fu on 2017/5/10/010.
 */

public class RecyclerViewDifferentItemTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //数据
    private List datas;
    //类别
    private static enum ITEM_TYPE{
        ITEM_TYPE_ADDS,
        ITEM_TYPE_CLASSIFY,
        ITEM_TYPE_SINGLE,
        ITEM_TYPE_NORMAL
    }
    public RecyclerViewDifferentItemTypeAdapter(List datas){
        this.datas = datas;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof AddsViewHolder){
//            ((AddsViewHolder) holder).textView.setText("adds---------------");
        }else if(holder instanceof ClassifyViewHolder){
            ((ClassifyViewHolder) holder).textView.setText("ClassifyViewHolder---------------");
        }else if(holder instanceof SingleViewHolder){
//            ((SingleViewHolder) holder).textView.setText("SingleViewHolder---------------");
        }else{
            ((NormalViewHolder) holder).textView.setText(datas.get(position-3)+"");
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //getItemViewType(position) == ITEM_TYPE.ITEM_TYPE_Theme.ordinal()
                   // ? gridManager.getSpanCount() : 1;
                    return 0;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case 0:
                //ITEM_TYPE.ITEM_TYPE_ADDS.ordinal();
                //广告
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_home_fragment_recyclerview_adds,parent,false);
                return new AddsViewHolder(view);
            case 1:
                //ITEM_TYPE.ITEM_TYPE_CLASSIFY.ordinal();
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_home_fragment_recyclerview_classify,parent,false);
                return new ClassifyViewHolder(view);
            case 2:
                //ITEM_TYPE.ITEM_TYPE_SINGLE.ordinal();
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_home_fragment_recyclerview_single,parent,false);
            return new SingleViewHolder(view);
            default:
                //ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_home_fragment_recyclerview_normal,parent,false);
                return new NormalViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        switch (position){
            case 0:
                type = ITEM_TYPE.ITEM_TYPE_ADDS.ordinal();
                break;
            case 1:
                type = ITEM_TYPE.ITEM_TYPE_CLASSIFY.ordinal();
                break;
            case 2:
                type = ITEM_TYPE.ITEM_TYPE_SINGLE.ordinal();
                break;
            default:
                type = ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
        }
        return type;
    }
    public class AddsViewHolder extends RecyclerView.ViewHolder{
        //public TextView textView;
        public AddsViewHolder(View itemView){
            super(itemView);
            //textView = (TextView) itemView.findViewById(R.id.tv_adds);
        }
    }
    public class ClassifyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ClassifyViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_classify);
        }
    }
    public class SingleViewHolder extends RecyclerView.ViewHolder{
        public SingleViewHolder(View itemView){
            super(itemView);
        }
    }
    public class NormalViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public NormalViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_normal);
        }
    }
}
