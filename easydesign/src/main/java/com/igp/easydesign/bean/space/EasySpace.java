package com.igp.easydesign.bean.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Created by qiu on 2018/7/27.
 * 设计区
 * Params params                          = new Gson().fromJson(mProduct.getDesign().getParams().get("front-0"),Params.class);
 * int                paramsWidthCM       = (int) Float.parseFloat(params.width);                                             //paramsWidth
 * int                paramsHeightCM      = (int) Float.parseFloat(params.height);                                            //paramsHeight
 */

public class EasySpace extends BaseEasySpace{

    public int bgColor = Color.TRANSPARENT;
    public int paramsWidthCM;
    public int paramsHeightCM;


    public EasySpace(Rect rect, Matrix matrix, Paint paint,int bgColor,int paramsWidthCM,int paramsHeightCM) {
        super(rect, matrix, paint);
        this.bgColor = bgColor;
        this.paramsHeightCM = paramsWidthCM;
        this.paramsWidthCM  = paramsHeightCM;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (rect != null) {
            paint.setColor(bgColor);
            canvas.drawRect(rect,paint);
        }
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
