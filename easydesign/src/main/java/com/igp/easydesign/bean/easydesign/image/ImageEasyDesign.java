package com.igp.easydesign.bean.easydesign.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;

/**
 * Created by qiu on 2018/7/27.
 * ImageEasyDesign 图片设计
 * 概览
 * *************************************************************************************************
 * 属性：
 *      1.图片 - 类型
 *      1.图片 - 原始宽度
 *      2.图片 - 原始高度
 *      3.图片 - 线上地址
 *      4.图片 - 手机本地
 *      5.图片 - Bitmap
 *      6.图片 - 是否模糊
 *
 * 方法：
 *      01.绘制 - 图片    （）
 *      02.设置 - 图片类型（）
 *      03.获取 - 图片类型（）
 *      04.设置 - 原始宽度（）
 *      05.获取 - 原始宽度（）
 *      06.设置 - 原始高度（）
 *      07.获取 - 原始高度（）
 *      08.设置 - 线上地址（）
 *      09.获取 - 线上地址（）
 *      10.设置 - 手机本地（）
 *      11.获取 - 手机本地（）
 *      12.设置 - Bitmap  （）
 *      13.获取 - Bitmap  （）
 *      14.设置 - 是否模糊（）
 *      15.获取 - 是否模糊（）
 * *************************************************************************************************
 */
public class ImageEasyDesign extends BaseEasyDesign {

    public ImageEasyDesignType imageEasyDesignType;
    public int                 originalWidthDp    ;
    public int                 originalHeightDp   ;
    public String              remotePhotoLink    ;
    public String              localPhontoPath    ;
    public Bitmap              bitmap             ;
    public boolean             isBulr      = false;


    public ImageEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix, Bitmap bitmap, ImageEasyDesignType imageEasyDesignType) {
        super(srcPs, dstPs, srcRect, dstRect, matrix);
        this.imageEasyDesignType = imageEasyDesignType;
        this.bitmap = bitmap;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }

    public ImageEasyDesignType getImageEasyDesignType() {
        return imageEasyDesignType;
    }

    public void setImageEasyDesignType(ImageEasyDesignType imageEasyDesignType) {
        this.imageEasyDesignType = imageEasyDesignType;
    }

    public int getOriginalWidthDp() {
        return originalWidthDp;
    }

    public void setOriginalWidthDp(int originalWidthDp) {
        this.originalWidthDp = originalWidthDp;
    }

    public int getOriginalHeightDp() {
        return originalHeightDp;
    }

    public void setOriginalHeightDp(int originalHeightDp) {
        this.originalHeightDp = originalHeightDp;
    }

    public String getRemotePhotoLink() {
        return remotePhotoLink;
    }

    public void setRemotePhotoLink(String remotePhotoLink) {
        this.remotePhotoLink = remotePhotoLink;
    }

    public String getLocalPhontoPath() {
        return localPhontoPath;
    }

    public void setLocalPhontoPath(String localPhontoPath) {
        this.localPhontoPath = localPhontoPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isBulr() {
        return isBulr;
    }

    public void setBulr(boolean bulr) {
        isBulr = bulr;
    }

}
