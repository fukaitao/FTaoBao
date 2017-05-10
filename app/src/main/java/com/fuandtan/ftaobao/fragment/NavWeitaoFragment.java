package com.fuandtan.ftaobao.fragment;

import android.animation.Animator;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fuandtan.ftaobao.R;
import com.fuandtan.ftaobao.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class NavWeitaoFragment extends Fragment implements Animator.AnimatorListener {
    private View weitaoView;
    private View tf_action_bar;
    private LinearLayout tf_tab_bar;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private ListView tf_tab_page;
    private List<String> items;

    private int mOrientation;
    //状态栏布局
    private LinearLayout statusBar;

    /**
     * fragment 生命周期：
     * onAttach -->
     * onCreate -->
     * onCreateView -->
     * onActivityCreated -->
     * onStart() -->
     * onResume------fragment active
     * onPause() -->
     * onStop() -->
     * onDestroyView() -->
     * onDestroy() -->
     * onDetach()-----fragment is Destroyed
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();//初始化数据
    }

    private void initData() {
        items = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            items.add("" + i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        weitaoView = inflater.inflate(R.layout.nav_weitao_fragment, container, false);
        initView();//初始化布局
        return weitaoView;
    }

    private void initView() {
        tf_action_bar = weitaoView.findViewById(R.id.tf_action_bar);
        tf_tab_bar = (LinearLayout) weitaoView.findViewById(R.id.tf_tab_bar);
        recyclerView = (RecyclerView) weitaoView.findViewById(R.id.tf_tab_page);
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置RecyclerView布局显示方式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter = new RecyclerViewAdapter());
        //设置RecyclerView的分割线
        recyclerView.addItemDecoration(new RecyclerViewItem(getActivity(), LinearLayoutManager.VERTICAL));
        // 得到状态栏布局对象
        statusBar = (LinearLayout) weitaoView.findViewById(R.id.ll_normal_status_bar);
        // 动态设置状态高度为得到的状态栏高度
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
        params.height = Utils.getStatusBarHeight(getActivity());
        statusBar.setLayoutParams(params);
    }

    /**
     * RecyclerView 分割线绘制
     */
    class RecyclerViewItem extends RecyclerView.ItemDecoration {
        private int[] ATTRS = new int[]{android.R.attr.listDivider};
        private int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        private int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
        private Drawable mDivider;

        public RecyclerViewItem(Context context, int orientation) {
            final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
            mDivider = typedArray.getDrawable(0);
            typedArray.recycle();
            setOrientation(orientation);
        }

        private void setOrientation(int orientation) {
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {
            super.onDraw(c, parent);
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }

        /**
         * 竖直方向的分割线
         *
         * @param c
         * @param parent
         */
        private void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        /**
         * 水平方向的分割线
         *
         * @param c
         * @param parent
         */
        private void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);

            }
        }
    }

    /**
     * itemList数据适配器
     */
    class RecyclerViewAdapter extends RecyclerView.Adapter<WeitaoViewHolder> {

        @Override
        public WeitaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            WeitaoViewHolder weitaoViewHolder = new WeitaoViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.nav_weitao_item, parent, false));
            return weitaoViewHolder;
        }

        @Override
        public void onBindViewHolder(final WeitaoViewHolder holder, int position) {
            holder.tv_item_weitao.setText(items.get(position));
            holder.tv_item_weitao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), holder.tv_item_weitao.getText(), Toast.LENGTH_LONG);
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    /**
     * itemView持有类
     */
    class WeitaoViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_weitao;

        public WeitaoViewHolder(View itemView) {
            super(itemView);
            tv_item_weitao = (TextView) itemView.findViewById(R.id.tv_item_weitao);
        }
    }

    /**
     * actionbar操作动画
     *
     * @param animation
     */
    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
