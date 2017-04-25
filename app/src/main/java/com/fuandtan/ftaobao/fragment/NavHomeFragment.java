package com.fuandtan.ftaobao.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fuandtan.ftaobao.R;

import java.util.ArrayList;
import java.util.List;


public class NavHomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<String> mData;
    private RecyclerViewAdapter recycleViewAdapter;
    private int mOrientation;
    /*
    fragment 生命周期：onAttach -->onCreate-->onCreateView-->onActivityCreated-->onStart()-->onResume------fragment active
    onPause()-->onStop()-->onDestroyView()-->onDestroy()-->onDetach()-----fragment is Destroyed
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void getData(){
        mData = new ArrayList<String>();
        for(int i=0;i<100;i++){
            mData.add(""+i);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        getData();
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycleview);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recycleViewAdapter = new RecyclerViewAdapter());
        recyclerView.addItemDecoration(new RecyclerViewItem(getActivity(), LinearLayoutManager.VERTICAL));
    }
    class RecyclerViewItem extends RecyclerView.ItemDecoration{
        private int[] ATTRS = new int[]{android.R.attr.listDivider};
        private int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        private int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
        private Drawable mDivider;
        public RecyclerViewItem(Context context,int orientation){
            final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
            mDivider = typedArray.getDrawable(0);
            typedArray.recycle();
            setOrientation(orientation);
        }

        private void setOrientation(int orientation){
            mOrientation = orientation;
        }
        @Override
        public void onDraw(Canvas c, RecyclerView parent) {
            super.onDraw(c, parent);
            if(mOrientation == VERTICAL_LIST){
                drawVertical(c,parent);
            }else{
                drawHorizontal(c, parent);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if(mOrientation == VERTICAL_LIST){
                outRect.set(0,0,0,mDivider.getIntrinsicHeight());
            }else{
                outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
            }
        }

        private void drawVertical(Canvas c, RecyclerView parent){
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth()-parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for(int i = 0; i < childCount; i++){
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
                final int top = child.getBottom()+params.bottomMargin;
                final int bottom = top+mDivider.getIntrinsicHeight();
                mDivider.setBounds(left,top,right,bottom);
                mDivider.draw(c);
            }
        }

        private void drawHorizontal(Canvas c,RecyclerView parent){
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight()-parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for(int i = 0;i < childCount ;i++){
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight()+params.rightMargin;
                final int right = left+mDivider.getIntrinsicWidth();
                mDivider.setBounds(left,top,right,bottom);
                mDivider.draw(c);

            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.nav_home_fragment, container, false);
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.recycleview_item,parent,false));
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.textView.setText(mData.get(position));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"click ："+holder.textView.getText(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.itemText);
        }
    }
}
