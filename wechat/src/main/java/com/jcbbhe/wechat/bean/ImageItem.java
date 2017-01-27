package com.jcbbhe.wechat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by jcbbhe on 17/1/14.
 */
public class ImageItem {


    public ImageItem() {
    }

    public ImageItem(int imageId, String imagePath, int orientation) {
        this.imageId = imageId;
        this.imagePath = imagePath;
        this.orientation = orientation;
    }

    private int imageId;
    private String imagePath;
    private int orientation;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
