package com.fuandtan.ftaobao.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by kaitao.fu on 2017/5/7/007.
 */

public class Utils {
    /**
     * 通过反射获取状态栏高度
     * @return int 高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
}
