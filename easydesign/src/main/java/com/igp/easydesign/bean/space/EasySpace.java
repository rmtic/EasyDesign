package com.igp.easydesign.bean.space;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Created by qiu on 2018/7/27.
 * 设计区
 */

public class EasySpace extends BaseEasySpace{

    public EasySpace(Rect rect, Matrix matrix, Paint paint) {
        super(rect, matrix, paint);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (rect != null) {
            canvas.drawRect(rect,paint);
        }
    }


}
