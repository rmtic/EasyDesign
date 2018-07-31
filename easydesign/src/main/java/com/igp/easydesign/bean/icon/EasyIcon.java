package com.igp.easydesign.bean.icon;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * EasyIcon Design
 * Created by Sam on 2018/7/30.
 */

public class EasyIcon {
    private Bitmap       bitmap;
    private RectF        rectF;
    private EasyIconType easyIconType;

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
}
