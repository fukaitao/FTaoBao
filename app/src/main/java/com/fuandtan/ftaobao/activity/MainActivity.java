package com.fuandtan.ftaobao.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fuandtan.ftaobao.fragment.NavAskFragment;
import com.fuandtan.ftaobao.fragment.NavCartFragment;
import com.fuandtan.ftaobao.fragment.NavHomeFragment;
import com.fuandtan.ftaobao.fragment.NavMyFragment;
import com.fuandtan.ftaobao.fragment.NavWeitaoFragment;
import com.fuandtan.ftaobao.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private NavHomeFragment navHomeFragment;
    private NavWeitaoFragment navWeitaoFragment;
    private NavAskFragment navAskFragment;
    private NavCartFragment navCartFragment;
    private NavMyFragment navMyFragment;
    private RadioGroup radioGroup;
    private RadioButton radioNavHome;
    private RadioButton radioNavWeitao;
    private RadioButton radioNavAsk;
    private RadioButton radioNavCart;
    private RadioButton radioNavMy;
    private Animation navScaleAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置透明状态栏
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
//        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
//        getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        initView();
    }

    private void initView() {
        //初始化点击导航项动画
        navScaleAnim = AnimationUtils.loadAnimation(this, R.anim.nav_scale_anim);
        fragmentManager = getFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioNavHome = (RadioButton) findViewById(R.id.rb_nav_home);
        radioNavWeitao = (RadioButton) findViewById(R.id.rb_nav_weitao);
        radioNavAsk = (RadioButton) findViewById(R.id.rb_nav_ask);
        radioNavCart = (RadioButton) findViewById(R.id.rb_nav_cart);
        radioNavMy = (RadioButton) findViewById(R.id.rb_nav_my);
        radioGroup.setOnCheckedChangeListener(new onCheckedChanged());
        radioNavHome.setChecked(true);
    }

    /**
     * 导航项点击动画
     */
    private class onCheckedChanged implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideAllFragment(transaction);
            switch (checkedId) {
                case R.id.rb_nav_home:
                    if (navHomeFragment == null) {
                        navHomeFragment = new NavHomeFragment();
                        transaction.add(R.id.content, navHomeFragment);
                    } else {
                        transaction.show(navHomeFragment);
                    }
                    radioNavHome.startAnimation(navScaleAnim);
                    break;
                case R.id.rb_nav_weitao:
                    if (navWeitaoFragment == null) {
                        navWeitaoFragment = new NavWeitaoFragment();
                        transaction.add(R.id.content, navWeitaoFragment);
                    } else {
                        transaction.show(navWeitaoFragment);
                    }
                    radioNavWeitao.startAnimation(navScaleAnim);
                    break;
                case R.id.rb_nav_ask:
                    if (navAskFragment == null) {
                        navAskFragment = new NavAskFragment();
                        transaction.add(R.id.content, navAskFragment);
                    } else {
                        transaction.show(navAskFragment);
                    }
                    radioNavAsk.startAnimation(navScaleAnim);
                    break;
                case R.id.rb_nav_cart:
                    if (navCartFragment == null) {
                        navCartFragment = new NavCartFragment();
                        transaction.add(R.id.content, navCartFragment);
                    } else {
                        transaction.show(navCartFragment);
                    }
                    radioNavCart.startAnimation(navScaleAnim);
                    break;
                case R.id.rb_nav_my:
                    if (navMyFragment == null) {
                        navMyFragment = new NavMyFragment();
                        transaction.add(R.id.content, navMyFragment);
                    } else {
                        transaction.show(navMyFragment);
                    }
                    radioNavMy.startAnimation(navScaleAnim);
                    break;
            }
            transaction.commit();
        }
    }

    /**
     * 隐藏所有fragment
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if (navHomeFragment != null) {
            transaction.hide(navHomeFragment);
        }
        if (navWeitaoFragment != null) {
            transaction.hide(navWeitaoFragment);
        }
        if (navAskFragment != null) {
            transaction.hide(navAskFragment);
        }
        if (navCartFragment != null) {
            transaction.hide(navCartFragment);
        }
        if (navMyFragment != null) {
            transaction.hide(navMyFragment);
        }
    }
}
