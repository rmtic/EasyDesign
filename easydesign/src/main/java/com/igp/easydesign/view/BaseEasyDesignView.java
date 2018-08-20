package com.igp.easydesign.view;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.igp.easydesign.bean.easycontrol.EasyControl;
import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.easydesign.image.LocalImageEasyDesign;
import com.igp.easydesign.bean.easydesign.image.RemoteImageEasyDesign;
import com.igp.easydesign.bean.icon.EasyIcon;
import com.igp.easydesign.bean.icon.EasyIconType;
import com.igp.easydesign.bean.mask.EasyMask;
import com.igp.easydesign.bean.space.EasySpace;
import com.igp.easydesign.bean.tips.EasyTips;
import com.igp.easydesign.helper.EasyDesignHelper;

import java.util.List;

/**
 * Created by qiu on 2018/7/27.
 */

public abstract class BaseEasyDesignView extends View  {

    private boolean enableDrawDstRectBg        = true;                                              //是否允许绘制背景
    private boolean enableDrawEasySpace        = true;                                              //是否允许绘制设计区
    private boolean enableDrawEasyMask         = true;                                              //是否允许绘制遮罩
    private boolean enableDrawDstPsLine        = true;                                              //是否绘制小矩形边框
    private boolean enableDrawDstRectLine      = true;                                              //是否绘制矩阵范围的框
    private boolean enableDrawGridLine         = true;                                              //是否绘制矩阵范围的网格
    private boolean enableDrawDstpsPoint       = true;                                              //是否绘制矩阵点
    private boolean enableDrawLeetToptips      = true;                                              //是否绘制提示文字
    private boolean enableDrawEasyIcons        = true;                                              //是否绘制控制图标

    public OnEasyDesignViewListener onEasyDesignViewListener;


    //TODO 待开发 设计平台基本信息
    //会员手势操作行为 [ 无 , 拖拽 , 缩放 , 旋转 ]
    public int actionMode;
    public static final int ACTION_NONE             = 0;
    public static final int ACTION_DRAG             = 1;
    public static final int ACTION_SCALE            = 2;
    public static final int ACTION_ROTATE           = 3;
    public static final int ACTION_SCALE_AND_ROTATE = 4;

    public ScaleGestureDetector mScaleGestureDetector = null;
    public static float SCALE_MAX                   = 10.0f;
    public static float SCALE_MIN                   = 0.08f;
    public static float SCALE_MIN_LEN;
    public float preX, preY;


    public abstract EasyMask             getEasyMask();                                             //获取遮罩
    public abstract EasySpace            getEasySpace();                                            //获取设计区
    public abstract List<BaseEasyDesign> getBaseEasyDesigns();                                      //获取设计组
    public abstract void                 setEasyControl(EasyControl easyControl);                   //设置控制器
    public abstract EasyControl          getEasyControl();                                          //获取控制器

    int[] location = new int[2];
    public int drawDensity = 2;//绘制密度,数值越高图像质量越低、性能越好
    //记录点击的位置
    private float  curX, curY;
    float[] downPoint;


    public BaseEasyDesignView(Context context) {
        super(context);
        init();
    }

    public BaseEasyDesignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseEasyDesignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //是否允许触摸,默认允许
        setFocusable(true);
        //注册触摸事件
        if(isFocusable()){
            mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    onScaleAction(detector);
                    return true;
                }
                @Override
                public boolean onScaleBegin(ScaleGestureDetector detector) {
                    return true;
                }

                @Override
                public void onScaleEnd(ScaleGestureDetector detector) {

                }
            });
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /** 绘制控制层,绘制矩阵范围的背景 */
        if (getEasyControl() != null && getEasyControl().getBindEasyDesign() != null ) {
            if(enableDrawDstRectBg)  {getEasyControl() .drawDstRectBg(canvas);    }
        }
        /**绘制设计区域*/
        if (getEasySpace() != null) {
            if(enableDrawEasySpace)  {getEasySpace().draw(canvas);    }
        }

        /** 绘制绘制所有设计 */
        for (BaseEasyDesign baseEasyDesign : getBaseEasyDesigns()) {
            baseEasyDesign.getPaint().setAlpha(255);
            baseEasyDesign.draw(canvas);
        }
        /** 绘制遮罩蒙版 */
        if (getEasyMask() != null) {
            if(enableDrawEasyMask)  {getEasyMask().draw(canvas);    }
        }

        /** 绘制半透明所有设计 */
        for (BaseEasyDesign baseEasyDesign : getBaseEasyDesigns()) {
            if (baseEasyDesign.isSelected()) {
                baseEasyDesign.getPaint().setAlpha(20);
                baseEasyDesign.draw(canvas);
                baseEasyDesign.getPaint().setAlpha(255);
            }
        }

        /** 绘制控制层 */
        if (getEasyControl() != null && getEasyControl().getBindEasyDesign() != null ){
            if(enableDrawDstPsLine)  {getEasyControl().drawDstPsLine(canvas);    }
            if(enableDrawDstRectLine){getEasyControl().drawDstRectLine(canvas);  }                  //绘制矩阵范围的框
            if(enableDrawGridLine)   {getEasyControl().drawGridLine(canvas);     }                  //绘制矩阵范围的网格
            if(enableDrawDstpsPoint) {getEasyControl().drawDstPsPoint(canvas);   }                  //绘制矩阵范围的框
            if(enableDrawLeetToptips){getEasyControl().drawLeftTopTips(canvas);  }                  //绘制提示文字
            if(enableDrawEasyIcons)  {getEasyControl().drawEasyIcons(canvas);    }                  //绘制控制图标
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int evX = (int)event.getX();
        int evY = (int)event.getY();

        getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        curX = (event.getRawX() - location[0]) / drawDensity;
        curY = (event.getRawY() - location[1]) / drawDensity;
        downPoint = new float[]{curX * drawDensity, curY * drawDensity};//还原点倍数


        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //双手指处理
                //两手指按下的时候
                float downDistance = spacing(event);
                if (actionMode == ACTION_DRAG && downDistance > 10){
                    //防止误触
                    actionMode = ACTION_SCALE;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                /** 手指按下时有如下3中情况（由上至下排序 1. 控制按钮 2.设计 3.平台背景） */
                actionDown();
                break;
            case MotionEvent.ACTION_MOVE:
                //拖动/旋转/放大/旋转+放大
                change();
                switch (actionMode) {
                    case ACTION_DRAG:
                        actionDrag();
                        break;
                    case ACTION_ROTATE:
                        //旋转
                        break;
                    case ACTION_SCALE:
                        //放大
                        //两个手指触摸放大
                        mScaleGestureDetector.onTouchEvent(event);
                        break;
                    case ACTION_SCALE_AND_ROTATE:
                        //旋转和放大功能
                        onActionScaleAndRotate();
                        break;
                    default:
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            default:
                break;
        }
        invalidate();
        preX = curX;
        preY = curY;
        return true;
    }

    //todo 待修改
    public void change(){
        /**判断是否模糊*/
        if (getSelectedEasyDesign() != null) {
            if( getSelectedEasyDesign() instanceof LocalImageEasyDesign ){
                int areaDesignWidthPx  =  ConvertUtils.dp2px(getEasySpace().getRect().width());
                int areaDesignHeightPx =  ConvertUtils.dp2px(getEasySpace().getRect().height());
                ((LocalImageEasyDesign) getSelectedEasyDesign()).setBulr(EasyDesignHelper.isBlurImage(((LocalImageEasyDesign) getSelectedEasyDesign()),((LocalImageEasyDesign) getSelectedEasyDesign()).getOriginalWidthDp(),((LocalImageEasyDesign) getSelectedEasyDesign()).getOriginalHeightDp(),areaDesignWidthPx,areaDesignHeightPx,getEasySpace().paramsWidthCM));
            }else if(getSelectedEasyDesign() instanceof RemoteImageEasyDesign){
                int areaDesignWidthPx  =  ConvertUtils.dp2px(getEasySpace().getRect().width());
                int areaDesignHeightPx =  ConvertUtils.dp2px(getEasySpace().getRect().height());
                ((RemoteImageEasyDesign) getSelectedEasyDesign()).setBulr(EasyDesignHelper.isBlurImage(((RemoteImageEasyDesign) getSelectedEasyDesign()),((RemoteImageEasyDesign) getSelectedEasyDesign()).getOriginalWidthDp(),((RemoteImageEasyDesign) getSelectedEasyDesign()).getOriginalHeightDp(),areaDesignWidthPx,areaDesignHeightPx,getEasySpace().paramsWidthCM));
            }
        }

        /** 当前设计发生变化会触发onEasyDesignChange（）*/
        if (onEasyDesignViewListener != null && getSelectedEasyDesign() != null) {
            onEasyDesignViewListener.onEasyDesignChange(getSelectedEasyDesign(),EasyEventType.RESIZE);
        }
    }



    private void onActionScaleAndRotate() {
        // 缩放 + 旋转
        actionScaleEasyDesign();
        actionRotateEasyDesign();
        this.invalidate();
    }

    public float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 按下的时候
     */
    private void actionDown() {
        if(isInEasyIcon(downPoint)){
            return;
        }
        //按下
        if (isInEasyDesign(downPoint)) {
            //在设计上
            actionMode = ACTION_DRAG;
            for (BaseEasyDesign baseEasyDesign : getBaseEasyDesigns()) {
                if(isInEasyDesignBitmap(baseEasyDesign.matrix,baseEasyDesign.getSrcRect(),downPoint)){//如果在图片上
                    setSelectedEasyDesign(baseEasyDesign);
                }
            }
        }else{
            actionMode = ACTION_NONE;
            if (getEasyControl() != null) {
                setSelectedEasyDesign(null);
            }
        }
        /** 当前设计发生变化会触发onEasyDesignChange（）*/
        if (onEasyDesignViewListener != null) {
            onEasyDesignViewListener.onEasyDesignChange(getSelectedEasyDesign(),EasyEventType.SELECTED);
        }
    }

    /**
     * 移动设计
     */
    private void actionDrag() {
        if (getSelectedEasyDesign() != null && isInEasyDesignBitmap(getSelectedEasyDesign().matrix,getSelectedEasyDesign().getSrcRect(),downPoint)) {
            getSelectedEasyDesign().postTranslate((curX - preX) * drawDensity, (curY - preY) * drawDensity);
        }
    }

    /**
     * 放大设计
     */
    public void actionScaleEasyDesign(){
        /**
         * Method for scale easydesign;
         * Check EasyDesign not null.Get Dest Points Array;
         *
         * CurX is finger point in display X;
         * CurY is finger point in display Y;
         *
         * Q:fingerToMatrixCenterXHypotenuse
         * A:
         * To take the distance of the right hyposenuse of the matrix center to finger point;
         * easyDesignDstps[16] is matrix centerX;
         * easyDesignDstps[17] is matrix centerY;
         * Formula for finger
         * equals sqrt(cux - centerX * enterX + cux - centerY + centerT ,2)
         *
         * Q: matrixHypotenuse
         * A:
         * To take the distance of the right hyposenuse of the matrix;
         *
         *
         * b equals
         * DesPint Array
         * 8 - 0 , 9 - 1
         * (0 ,1 ) (2 ,3 ) (4,5)
         * (14,15) (16,17) (6,7)
         * (12,13) (10,11) (8,9)
         *
         * Attention for onActionScaleAndRotate()is remember to refresh the dstPst[];
         *
         */
        if (getSelectedEasyDesign() == null) {return; }
        float[] easyDesignDstps = getSelectedEasyDesign().getDstPs();
        float fingerToMatrixCenterXHypotenuse = (float) Math.sqrt(Math.pow(curX * drawDensity - easyDesignDstps[16], 2) + Math.pow(curY * drawDensity - easyDesignDstps[17], 2));
        float matrixHypotenuse = (float) Math.sqrt(Math.pow(easyDesignDstps[8] - easyDesignDstps[0], 2) + Math.pow(easyDesignDstps[9] - easyDesignDstps[1], 2)) / 2;            //求出图片的斜边
        double photoLen = Math.sqrt(Math.pow(getSelectedEasyDesign().getDstRect().width(), 2) + Math.pow(getSelectedEasyDesign().getDstRect().height(), 2));
        if (fingerToMatrixCenterXHypotenuse >= photoLen / 2 * SCALE_MIN && fingerToMatrixCenterXHypotenuse >= SCALE_MIN_LEN && fingerToMatrixCenterXHypotenuse <= photoLen / 2 * SCALE_MAX) {
            float scale = fingerToMatrixCenterXHypotenuse / matrixHypotenuse;
            getSelectedEasyDesign().postScale(scale, scale);
            /*long width  = Math.round(Math.sqrt(Math.pow(easyDesignDstps[0] - easyDesignDstps[4],2)+ Math.pow(easyDesignDstps[1] - easyDesignDstps[5],2)));
            Log.i("print", String.format("width = %s",width));*/
        }
    }

    /**
     * 旋转设计
     */
    public void actionRotateEasyDesign(){
        if (getSelectedEasyDesign() == null) {return; }
        float[] easyDesignDstps = getSelectedEasyDesign().getDstPs();
        //旋转
        //根据移动坐标的变化构建两个向量，以便计算两个向量角度.
        PointF preVector = new PointF();
        PointF curVector = new PointF();
        preVector.set((preX * drawDensity - easyDesignDstps[16]) , preY * drawDensity - easyDesignDstps[17] );//旋转后向量
        curVector.set( curX * drawDensity - easyDesignDstps[16]  , curY * drawDensity - easyDesignDstps[17] );//旋转前向量
        //计算向量长度
        double preVectorLen = getVectorLength(preVector);
        double curVectorLen = getVectorLength(curVector);
        //计算两个向量的夹角.
        double cosAlpha = (preVector.x * curVector.x + preVector.y * curVector.y)  / (preVectorLen * curVectorLen);
        //由于计算误差，可能会带来略大于1的cos，例如
        if (cosAlpha > 1.0f) {
            cosAlpha = 1.0f;
        }
        //本次的角度已经计算出来。
        double dAngle = Math.acos(cosAlpha) * 180.0 / Math.PI;
        // 判断顺时针和逆时针.
        //判断方法其实很简单，这里的v1v2其实相差角度很小的。
        //先转换成单位向量
        preVector.x /= preVectorLen;
        preVector.y /= preVectorLen;
        curVector.x /= curVectorLen;
        curVector.y /= curVectorLen;
        //作curVector的逆时针垂直向量。
        PointF verticalVec = new PointF(curVector.y, -curVector.x);

        //判断这个垂直向量和v1的点积，点积>0表示俩向量夹角锐角。=0表示垂直，<0表示钝角
        float vDot = preVector.x * verticalVec.x + preVector.y * verticalVec.y;
        if (vDot > 0) {
            //v2的逆时针垂直向量和v1是锐角关系，说明v1在v2的逆时针方向。
        } else {
            dAngle = -dAngle;
        }
        getSelectedEasyDesign().postRotate((float) dAngle);//旋转设计(注意刷新)
    }


    /**
     * 获取p1到p2的线段的长度
     *
     * @return
     */
    public double getVectorLength(PointF vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }


    public abstract void setEasyIconList(List<EasyIcon> easyIconList);
    //public abstract void setEasyTipsList(List<EasyTips> easyTipsList);


    /**
     * 方法：选中设计
     * 说明：会员挑选的设计设置为选中，其他设置为不选中
     * @param easyDesign
     */
    public abstract void setSelectedEasyDesign(BaseEasyDesign easyDesign);                          //选中设计

    /**
     * 方法：获取选中的设计
     * 说明：获取会员选中的设计
     * @return
     */
    public abstract BaseEasyDesign getSelectedEasyDesign();                                         //获取选中的设计


    /**
     * 判断是否点击在设计模型的Bitmap范围内
     * 说明：创建逆向矩阵，即还原矩阵，设置手指对应的点，最后判断点是否在原有图片的位置上
     * @param matrix
     * @param rectF     原始图片的rectf
     * @param downPoint
     * @return
     */
    public boolean isInEasyDesignBitmap(Matrix matrix, RectF rectF, float[] downPoint){
        float[] invertPoint = new float[2];
        Matrix invertMatrix = new Matrix();
        matrix.invert(invertMatrix);
        invertMatrix.mapPoints(invertPoint, downPoint);
        return rectF.contains(invertPoint[0], invertPoint[1]);
    }

    /**
     * 判断是否点击在控制按钮上
     * @param downPoint
     * @return
     */
    private boolean isInEasyIcon(float[] downPoint) {
        if (getEasyControl() != null) {
            for (EasyIcon easyIcon : getEasyControl().getEasyIconList()) {
                if (easyIcon.getEasyIconType() == EasyIconType.RIGHT_BOTTOM && easyIcon.getRectF().contains(downPoint[0],downPoint[1])) {
                    //如果是右下角的按钮，设置为可以旋转和放大的功能；
                    actionMode = ACTION_SCALE_AND_ROTATE;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否点击在设计模型上
     * @param downPoint
     * @return
     */
    public boolean isInEasyDesign(float[] downPoint){
        for (BaseEasyDesign baseEasyDesign : getBaseEasyDesigns()) {
            if(isInEasyDesignBitmap(baseEasyDesign.matrix,baseEasyDesign.getSrcRect(),downPoint)){//如果在图片上
                return true;
            }
        }
        return false;
    }

    public void onScaleAction(ScaleGestureDetector detector) {
        if( null == getSelectedEasyDesign()){
            return;
        }
        float[] photoCorners = getSelectedEasyDesign().getDstPs();
        //目前图片对角线长度
        float len = (float) Math.sqrt(Math.pow(photoCorners[0] - photoCorners[8], 2) + Math.pow(photoCorners[1] - photoCorners[9], 2));
        double photoLen = Math.sqrt(Math.pow(getSelectedEasyDesign().getDstRect().width(), 2) + Math.pow(getSelectedEasyDesign().getDstRect().height(), 2));
        float scaleFactor = detector.getScaleFactor();
        //设置Matrix缩放参数
        if ((scaleFactor < 1 && len >= photoLen * SCALE_MIN && len >= SCALE_MIN_LEN) || (scaleFactor > 1 && len <= photoLen * SCALE_MAX)) {
            getSelectedEasyDesign().postScale(scaleFactor, scaleFactor);
        }
    }

    /**
     * 动画旋转到指定角度
     * @param angle
     */
    public void rotate2Degree(float angle){
        if (getSelectedEasyDesign() == null) {
            return;
        }
        float[] dstPs = getSelectedEasyDesign().getDstPs();
        float lastDegree = EasyDesignHelper.computeDegree(new Point((int) dstPs[2], (int) dstPs[3]), new Point((int) dstPs[16], (int) dstPs[17]));//点与点的垂直夹角
        float targetAngle =  angle - lastDegree;
        rotateValueAnimator(targetAngle);
    }

    /**
     * 动画旋转度数
     * @param angle
     */
    public void rotateValueAnimator(float angle) {
        if (getSelectedEasyDesign() == null) {
            return;
        }
        final BaseEasyDesign easyDesign = getSelectedEasyDesign();
        float[] dstPs = easyDesign.getDstPs();
        final float[] lastDegree = {EasyDesignHelper.computeDegree(new Point((int) dstPs[2], (int) dstPs[3]), new Point((int) dstPs[16], (int) dstPs[17]))};//点与点的垂直夹角
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(lastDegree[0], lastDegree[0] +angle);
        valueAnimator.setDuration(800);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (easyDesign != null) {
                    easyDesign.postRotate((float)animation.getAnimatedValue() - lastDegree[0]);
                    lastDegree[0] = (float)animation.getAnimatedValue();
                }
               invalidate();
            }
        });
        valueAnimator.start();
    }

    public void setOnEasyDesignViewListener(OnEasyDesignViewListener onEasyDesignViewListener) {
        this.onEasyDesignViewListener = onEasyDesignViewListener;
    }

    public boolean isEnableDrawDstRectBg() {
        return enableDrawDstRectBg;
    }

    public void setEnableDrawDstRectBg(boolean enableDrawDstRectBg) {
        this.enableDrawDstRectBg = enableDrawDstRectBg;
    }

    public boolean isEnableDrawEasySpace() {
        return enableDrawEasySpace;
    }

    public void setEnableDrawEasySpace(boolean enableDrawEasySpace) {
        this.enableDrawEasySpace = enableDrawEasySpace;
    }

    public boolean isEnableDrawEasyMask() {
        return enableDrawEasyMask;
    }

    public void setEnableDrawEasyMask(boolean enableDrawEasyMask) {
        this.enableDrawEasyMask = enableDrawEasyMask;
    }

    public boolean isEnableDrawDstPsLine() {
        return enableDrawDstPsLine;
    }

    public void setEnableDrawDstPsLine(boolean enableDrawDstPsLine) {
        this.enableDrawDstPsLine = enableDrawDstPsLine;
    }

    public boolean isEnableDrawDstRectLine() {
        return enableDrawDstRectLine;
    }

    public void setEnableDrawDstRectLine(boolean enableDrawDstRectLine) {
        this.enableDrawDstRectLine = enableDrawDstRectLine;
    }

    public boolean isEnableDrawGridLine() {
        return enableDrawGridLine;
    }

    public void setEnableDrawGridLine(boolean enableDrawGridLine) {
        this.enableDrawGridLine = enableDrawGridLine;
    }

    public boolean isEnableDrawDstpsPoint() {
        return enableDrawDstpsPoint;
    }

    public void setEnableDrawDstpsPoint(boolean enableDrawDstpsPoint) {
        this.enableDrawDstpsPoint = enableDrawDstpsPoint;
    }

    public boolean isEnableDrawLeetToptips() {
        return enableDrawLeetToptips;
    }

    public void setEnableDrawLeftToptips(boolean enableDrawLeetToptips) {
        this.enableDrawLeetToptips = enableDrawLeetToptips;
    }

    public boolean isEnableDrawEasyIcons() {
        return enableDrawEasyIcons;
    }

    public void setEnableDrawEasyIcons(boolean enableDrawEasyIcons) {
        this.enableDrawEasyIcons = enableDrawEasyIcons;
    }
}
