package com.fuandtan.ftaobao.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.view.SliderLayout;

import java.util.ArrayList;
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
        ITEM_TYPE_MARQUEE_TEXT,
        ITEM_TYPE_NORMAL
    }
    private SliderLayout layoutTaobao;
    private List<Object> listTaobao;//图片列表
    private Dialog dialog;//提示框
    private Activity activity;
    public RecyclerViewDifferentItemTypeAdapter(List datas,Activity activity){
        this.datas = datas;

        this.activity = activity;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof AddsViewHolder){
            initTaoBao();
//            ((AddsViewHolder) holder).textView.setText("adds---------------");
        }else if(holder instanceof ClassifyViewHolder){
//            ((ClassifyViewHolder) holder).textView.setText("ClassifyViewHolder---------------");
        }else if(holder instanceof SingleViewHolder){
            //上下翻动文字内容
            int addsNum = 3;
            String title[] = new String[]{"最新","热议","最新","最新","热议","最新"};
            String content[] = new String[]{
                    "一看就显廉价的衣服，都有这三个共同点",
                    "小米6需要插卡激活 黄牛要亏本了！",
                    "都说女人有体香，你知道体香是怎么来的吗",
                    "太厉害了我的小米6手机连接2个Wi-Fi",
                    "C1驾驶证使用新规定！看看有多少变化",
                    "仅售2399！最便宜iPhone诞生"
            };
            for(int i = 0; i < addsNum; i++){
                View view = View.inflate(activity,R.layout.nav_home_fragment_recyclerview_single_notice_layout,null);
                TextView titleOne = (TextView)view.findViewById(R.id.tv_marque_title_one_first);
                titleOne.setText(title[i*2]);
                TextView contentOne = (TextView)view.findViewById(R.id.tv_marque_content_one_first);
                contentOne.setText(content[i*2]);
                TextView titleTwo = (TextView)view.findViewById(R.id.tv_marque_title_two_first);
                titleTwo.setText(title[i*2+1]);
                TextView contentTwo = (TextView)view.findViewById(R.id.tv_marque_content_two_first);
                contentTwo.setText(content[i*2+1]);
                ((SingleViewHolder) holder).viewFlipper.addView(view);
            }
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
                //ITEM_TYPE.ITEM_TYPE_MARQUEE_TEXT.ordinal();
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
                type = ITEM_TYPE.ITEM_TYPE_MARQUEE_TEXT.ordinal();
                break;
            default:
                type = ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
        }
        return type;
    }
    /**
     * 初始化
     */
    private void initTaoBao(){
        layoutTaobao.setVisibility(View.VISIBLE);
        layoutTaobao.setPictureIndex(0);
        listTaobao = new ArrayList<>();
        listTaobao.add(R.drawable.pic4);
        listTaobao.add(R.drawable.pic5);
        listTaobao.add(R.drawable.pic6);
        listTaobao.add(R.drawable.pic7);
        listTaobao.add(R.drawable.pic8);
        listTaobao.add(R.drawable.pic9);
        layoutTaobao.setList(listTaobao);
        layoutTaobao.setListener(new SliderLayout.IOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(MainActivity.this,"第 "+(position+1)+" 张图片",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class AddsViewHolder extends RecyclerView.ViewHolder{
        //public TextView textView;
        public AddsViewHolder(View itemView){
            super(itemView);
            //textView = (TextView) itemView.findViewById(R.id.tv_adds);
            layoutTaobao = (SliderLayout) itemView.findViewById(R.id.main_layout_taobao);
            dialog = new ProgressDialog(activity);
        }
    }
    public class ClassifyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ClassifyViewHolder(View itemView){
            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.tv_classify);
        }
    }
    public class SingleViewHolder extends RecyclerView.ViewHolder{
        public ViewFlipper viewFlipper;
        public SingleViewHolder(View itemView){
            super(itemView);
            viewFlipper = (ViewFlipper) itemView.findViewById(R.id.vf_marquee_text);
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
