package com.igp.easydesign.bean.icon;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.igp.easydesign.helper.EasyDesignHelper;

/**
 * EasyIcon Design
 * Created by Sam on 2018/7/30.
 */

public class EasyIcon {
    private Bitmap       bitmap;
    private RectF        rectF;
    private EasyIconType easyIconType;
    private int alpha = 255;

    public EasyIcon(Bitmap bitmap,EasyIconType easyIconType) {
        this.bitmap = bitmap;
        this.rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.easyIconType = easyIconType;
    }

    public EasyIcon(Bitmap bitmap, RectF rectF, EasyIconType easyIconType) {
        this.bitmap = bitmap;
        this.rectF = rectF;
        this.easyIconType = easyIconType;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public EasyIconType getEasyIconType() {
        return easyIconType;
    }

    public void setEasyIconType(EasyIconType easyIconType) {
        this.easyIconType = easyIconType;
    }

    public int getAlpha() {
        return alpha;
    }

    /**
     * Interval
     * min 0 max 255
     * @param alpha
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}
