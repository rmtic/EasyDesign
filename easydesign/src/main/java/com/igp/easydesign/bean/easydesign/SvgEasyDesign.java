package com.igp.easydesign.bean.easydesign;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;

/**
 * Created by qiu on 2018/7/27.
 */

public class SvgEasyDesign extends BaseEasyDesign{

    public SvgEasyDesign(float[] srcPs, float[] dstPs, RectF srcRect, RectF dstRect, Matrix matrix) {
        super(srcPs, dstPs, srcRect, dstRect, matrix);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

    }


}
