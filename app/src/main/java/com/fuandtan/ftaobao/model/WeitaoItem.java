package com.fuandtan.ftaobao.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Pareto909 on 2017/5/22.
 */

public class WeitaoItem {
    private int photoId;// 用户头像
    private String username;// 用户昵称
    private String title;// 微淘标题
    private String subTitle;// 微淘副标题

    public WeitaoItem(int photoId, String username, String title, String subTitle) {
        this.photoId = photoId;
        this.username = username;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
