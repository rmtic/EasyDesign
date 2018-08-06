package com.igp.easydesign.bean.easydesign.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.igp.easydesign.bean.easydesign.BaseEasyDesign;

/**
 * Created by qiu on 2018/7/27.
 */

public class ImageEasyDesign extends BaseEasyDesign {

    public Bitmap bitmap;
    public String remotePhotoLink;
    public String localPhontoLink;
    public ImageEasyDesignType imageEasyDesignType;

    //提供判断模糊的属性
    public int originalWidthDp;
    public int originalHeightDp;


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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getRemotePhotoLink() {
        return remotePhotoLink;
    }

    public void setRemotePhotoLink(String remotePhotoLink) {
        this.remotePhotoLink = remotePhotoLink;
    }

    public String getLocalPhontoLink() {
        return localPhontoLink;
    }

    public void setLocalPhontoLink(String localPhontoLink) {
        this.localPhontoLink = localPhontoLink;
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

    /**
     * 获取图片的动态宽
     * @return
     */
    public int getDynamicWidthDp() {
        int width = 0;
        if (dstPs != null && dstPs.length > 0) {
            width  = (int) Math.round(Math.sqrt(Math.pow(dstPs[0] - dstPs[4],2)+ Math.pow(dstPs[1] - dstPs[5],2)));
        }
        return (int) width;
    }

    /**
     * 获取图片的动态高
     * @return
     */
    public int getDynamicHeightDp() {
        int height = 0;
        if (dstPs != null && dstPs.length > 0) {
        height = (int) Math.round(Math.sqrt(Math.pow(dstPs[4] - dstPs[8],2)+ Math.pow(dstPs[5] - dstPs[9],2)));
        }
        return  height;
    }



}
