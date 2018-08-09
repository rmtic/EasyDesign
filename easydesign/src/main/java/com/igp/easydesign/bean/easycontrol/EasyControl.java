package com.igp.easydesign.bean.easycontrol;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.ImageEasyDesignType;
import com.igp.easydesign.bean.icon.EasyIcon;
import com.igp.easydesign.bean.icon.EasyIconType;
import com.igp.easydesign.helper.EasyDesignHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiu on 2018/7/27.
 */

public class EasyControl extends BaseEasyControl {


    private BaseEasyDesign easyDesign;
    private float []       srcPs   ,dstPs;
    public RectF           srcRect ,dstRect;
    public List<EasyIcon>  mEasyIconList = new ArrayList<>();
    private Bitmap         mBitmapWarning;


    @Override
    public BaseEasyDesign getBindEasyDesign() {
        return easyDesign;
    }

    @Override
    public void bindEasyDesign(BaseEasyDesign easyDesign) {
        this.easyDesign = easyDesign;
        /*if (onChangeEasyDesignListener != null) {
            onChangeEasyDesignListener.onBindEasyDesignListener(this.easyDesign);
        }*/
    }

    @Override
    public void drawDstRectLine(@NonNull Canvas canvas) {
        if (easyDesign == null)             {return;}
        if (easyDesign.getDstRect() == null){return;}
        if (canvas == null)                 {return;}

        dstRect = easyDesign.getDstRect();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAlpha(90);
        paint.setStrokeWidth(2);
        canvas.drawLine(  dstRect.left  , dstRect.top   ,  dstRect.right , dstRect.top   , paint);//绘制【左上往右上】线条
        canvas.drawLine(  dstRect.left  , dstRect.bottom,  dstRect.right , dstRect.bottom, paint);//绘制【左上往右上】线条
        canvas.drawLine(  dstRect.left  , dstRect.top   ,  dstRect.left  , dstRect.bottom, paint);//绘制【左上往右上】线条
        canvas.drawLine(  dstRect.right , dstRect.top   ,  dstRect.right , dstRect.bottom, paint);//绘制【左上往右上】线条
    }

    @Override
    public void drawDstRectBg(@NonNull Canvas canvas) {
        if (canvas == null) {return;}
        if (easyDesign == null){ return; }
        if (easyDesign.getDstRect() == null) {return;}
        Paint paintTip = new Paint();
        paintTip.setStyle(Paint.Style.FILL);
        paintTip.setColor(Color.WHITE);
        paintTip.setAlpha(20);
        paintTip.setAntiAlias(true);
        canvas.drawRect(easyDesign.getDstRect(), paintTip);
    }

    @Override
    public void drawDstPsbg(@NonNull Canvas canvas) {

    }

    @Override
    public void drawDstPsPoint(@NonNull Canvas canvas) {
        if (canvas == null) {return;}
        if (easyDesign == null){ return; }
        if (easyDesign.getDstPs() == null) {return;}
        if (easyDesign.getDstPs().length < 1){return;}
        dstPs = easyDesign.getDstPs();
        if (dstPs != null) {
            Paint paint2= new Paint();
            paint2.setColor(Color.WHITE);
            paint2.setAlpha(70);
            paint2.setStrokeWidth(20);
            for (int i = 0; i < dstPs.length; i+=2) {
                canvas.drawPoint(dstPs[i],dstPs[i+1],paint2);
            }
        }
    }

    @Override
    public void drawDstPsLine(@NonNull Canvas canvas) {
        if (canvas == null) {return;}
        if (easyDesign == null){ return; }
        if (easyDesign.getDstPs() == null) {return;}
        if (easyDesign.getDstPs().length < 1){return;}
        dstPs = easyDesign.getDstPs();
        if (dstPs != null) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAlpha(70);
            paint.setStrokeWidth(5);
            canvas.drawLine(dstPs[0], dstPs[1], dstPs[4], dstPs[5], paint);
            canvas.drawLine(dstPs[4], dstPs[5], dstPs[8], dstPs[9], paint);
            canvas.drawLine(dstPs[8], dstPs[9], dstPs[12], dstPs[13], paint);
            canvas.drawLine(dstPs[0], dstPs[1], dstPs[12], dstPs[13], paint);
        }
    }

    @Override
    public void drawGridLine(@NonNull Canvas canvas) {
        if (canvas == null) {return;}
        if (easyDesign == null){ return; }
        if (easyDesign.getDstRect() == null) {return;}

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        paint.setAlpha(90);
        drawGrid(canvas,easyDesign.getDstRect(),0.333f,2,paint);//绘制网格
    }

    @Override
    public void drawLockIcon(@NonNull Canvas canvas) {

    }

    @Override
    public void drawWarningIcon(@NonNull Canvas canvas) {

    }

    @Override
    public void drawLeftTopTips(@NonNull Canvas canvas) {
        if (easyDesign == null){ return; }
        if (dstPs == null) {return;}
        if (dstRect == null) { return; }

        float tipBoxTopDistance = 20;
        float tipBoxHeight      = 50;
        float tipBoxWidth       = 120;
        float tipBoxLeft        =( dstRect.centerX() - (float) (tipBoxWidth / 2));
        float tipBoxRight       = (dstRect.centerX() +(float)  (tipBoxWidth / 2));
        float tipBoxBottom      = dstRect.top - tipBoxTopDistance;
        float tipBoxTop         = dstRect.top - tipBoxTopDistance - tipBoxHeight;

        long width  = Math.round(Math.sqrt(Math.pow(dstPs[0] - dstPs[4],2)+ Math.pow(dstPs[1] - dstPs[5],2)));
        long height = Math.round(Math.sqrt(Math.pow(dstPs[4] - dstPs[8],2)+ Math.pow(dstPs[5] - dstPs[9],2)));
        Paint paintTip = new Paint();
        paintTip.setAlpha(100);
        paintTip.setTextSize(18);
        paintTip.setColor(Color.RED);
        //绘制角度信息
        float degree = (float) Math.floor(EasyDesignHelper.computeDegree(new Point((int)dstPs[2], (int)dstPs[3]),new Point((int)dstPs[16], (int)dstPs[17])));//点与点的垂直夹角
        canvas.drawText(String.format("[ degree = %s °][W:%sdp,H%sdp] [%s px,%s px]",degree,width,height, ConvertUtils.dp2px(width),ConvertUtils.dp2px(height)),dstRect.left,dstRect.top - tipBoxTopDistance - tipBoxHeight / 4 ,paintTip);

        //绘制图片设计的信息
        if (easyDesign instanceof ImageEasyDesign) {
            ImageEasyDesign imageEasyDesign = ((ImageEasyDesign) easyDesign);
            canvas.drawText(String.format("[ 图片类型 [ %s ],是否模糊[%s]",imageEasyDesign.getImageEasyDesignType(),imageEasyDesign.isBulr()),dstRect.left,dstRect.top - tipBoxTopDistance - tipBoxHeight / 4 - 20 ,paintTip);
            if (imageEasyDesign.getImageEasyDesignType() == ImageEasyDesignType.LOCAL_ALBUM || imageEasyDesign.getImageEasyDesignType() == ImageEasyDesignType.REMOTE_ALBUM) {
                canvas.drawText(String.format("[ 原始宽高 [ %s , %s ]",imageEasyDesign.getOriginalWidthDp(),imageEasyDesign.getOriginalHeightDp()),dstRect.left,dstRect.top - tipBoxTopDistance - tipBoxHeight / 4 - 40 ,paintTip);
            }
        }


    }



    @Override
    public EasyIcon getEasyIconByIconType(EasyIconType easyIconType) {
        for (EasyIcon mEasyIcon : getEasyIconList()) {
            if (mEasyIcon.getEasyIconType() == easyIconType) {
                return mEasyIcon;
            }
        }
        return null;
    }

    @Override
    public void setEasyIconList(List<EasyIcon> easyIconList) {
        this.mEasyIconList = easyIconList;
    }

    @Override
    public List<EasyIcon> getEasyIconList() {
        return mEasyIconList;
    }

    @Override
    public void drawEasyIcons(Canvas canvas) {
        if (getBindEasyDesign() != null && getBindEasyDesign().isSelected()) {

            for (int i = 0; i < getBindEasyDesign().getDstPs().length; i+=2) {
                if(i== 0 && i+1 == 1){
                    //左上角图标
                    if (getEasyIconByIconType(EasyIconType.LEFT_TOP) != null) {
                        getEasyIconByIconType(EasyIconType.LEFT_TOP).getRectF().offsetTo(dstPs[i]-getEasyIconByIconType(EasyIconType.LEFT_TOP).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.LEFT_TOP).getBitmap().getHeight()/2);
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.LEFT_TOP).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.LEFT_TOP).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.LEFT_TOP).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 2 && i+1 == 3){
                    //上中角图标
                    if (getEasyIconByIconType(EasyIconType.MIDDLE_TOP) != null) {
                        getEasyIconByIconType(EasyIconType.MIDDLE_TOP).getRectF().offsetTo(dstPs[i]-getEasyIconByIconType(EasyIconType.MIDDLE_TOP).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.MIDDLE_TOP).getBitmap().getHeight()/2);
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.MIDDLE_TOP).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.MIDDLE_TOP).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.MIDDLE_TOP).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 4 && i+1 == 5){
                    //右上角图标
                    if (getEasyIconByIconType(EasyIconType.RIGHT_TOP) != null) {
                        getEasyIconByIconType(EasyIconType.RIGHT_TOP).getRectF().offsetTo(dstPs[i]-getEasyIconByIconType(EasyIconType.RIGHT_TOP).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.RIGHT_TOP).getBitmap().getHeight());
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.RIGHT_TOP).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.RIGHT_TOP).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.RIGHT_TOP).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 6 && i+1 == 7){
                    //右中角图标
                    if (getEasyIconByIconType(EasyIconType.RIGHT_MIDDLE) != null) {
                        getEasyIconByIconType(EasyIconType.RIGHT_MIDDLE).getRectF().offsetTo( dstPs[i]-getEasyIconByIconType(EasyIconType.RIGHT_MIDDLE).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.RIGHT_MIDDLE).getBitmap().getHeight());
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.RIGHT_MIDDLE).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.RIGHT_MIDDLE).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.RIGHT_MIDDLE).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 8 && i+1 == 9){
                    //右下角图标
                    if (getEasyIconByIconType(EasyIconType.RIGHT_BOTTOM) != null) {
                        getEasyIconByIconType(EasyIconType.RIGHT_BOTTOM).getRectF().offsetTo(dstPs[i]-getEasyIconByIconType(EasyIconType.RIGHT_BOTTOM).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.RIGHT_BOTTOM).getBitmap().getHeight());
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.RIGHT_BOTTOM).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.RIGHT_BOTTOM).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.RIGHT_BOTTOM).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 10 && i+1 == 11){
                    //下中角图标
                    if (getEasyIconByIconType(EasyIconType.MIDDLE_BOTTOM) != null) {
                        getEasyIconByIconType(EasyIconType.MIDDLE_BOTTOM).getRectF().offsetTo( dstPs[i]-getEasyIconByIconType(EasyIconType.MIDDLE_BOTTOM).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.MIDDLE_BOTTOM).getBitmap().getHeight());
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.MIDDLE_BOTTOM).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.MIDDLE_BOTTOM).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.MIDDLE_BOTTOM).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 12 && i+1 == 13){
                    //左下角图标
                    if (getEasyIconByIconType(EasyIconType.LEFT_BOTTOM) != null) {
                        getEasyIconByIconType(EasyIconType.LEFT_BOTTOM).getRectF().offsetTo(dstPs[i]-getEasyIconByIconType(EasyIconType.LEFT_BOTTOM).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.LEFT_BOTTOM).getBitmap().getHeight());
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.LEFT_BOTTOM).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.LEFT_BOTTOM).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.LEFT_BOTTOM).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 14 && i+1 == 15){
                    //左中角图标
                    if (getEasyIconByIconType(EasyIconType.LEFT_MIDDLE) != null) {
                        getEasyIconByIconType(EasyIconType.LEFT_MIDDLE).getRectF().offsetTo(dstPs[i]-getEasyIconByIconType(EasyIconType.LEFT_MIDDLE).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.LEFT_MIDDLE).getBitmap().getHeight());
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.LEFT_MIDDLE).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.LEFT_MIDDLE).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.LEFT_MIDDLE).getBitmap().getHeight()/2, null);
                    }
                }
                else if(i== 16 && i+1 == 17){
                    //中间角图标
                    if (getEasyIconByIconType(EasyIconType.CENTER) != null) {
                        getEasyIconByIconType(EasyIconType.CENTER).getRectF().offsetTo(dstPs[i]-getEasyIconByIconType(EasyIconType.CENTER).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.CENTER).getBitmap().getHeight()/2);
                        canvas.drawBitmap(getEasyIconByIconType(EasyIconType.CENTER).getBitmap(), dstPs[i]-getEasyIconByIconType(EasyIconType.CENTER).getBitmap().getWidth()/2, dstPs[i+1]-getEasyIconByIconType(EasyIconType.CENTER).getBitmap().getHeight()/2, null);
                    }
                }
            }

        }
    }

    /**绘制网格线*/
    protected void drawGrid(Canvas canvas, RectF cropBounds, float scale, int lineNum, Paint paint) {
        float stepX = cropBounds.width() * scale;
        float stepY = cropBounds.height() * scale;
        float x = cropBounds.left;
        float y = cropBounds.top;
        for (int i = 0; i < lineNum; i++) {
            x += stepX;
            y += stepY;
            canvas.drawLine(x, cropBounds.top, x, cropBounds.bottom, paint);
            canvas.drawLine(cropBounds.left, y, cropBounds.right, y, paint);
        }
    }

    /**获取警告图标*/
    public Bitmap getBitmapWarning() {
        return mBitmapWarning;
    }

    /**设置警告图标*/
    public void setBitmapWarning(Bitmap bitmapWarning) {
        this.mBitmapWarning = mBitmapWarning;
    }

}
