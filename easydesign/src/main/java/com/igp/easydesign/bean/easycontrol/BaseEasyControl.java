package com.igp.easydesign.bean.easycontrol;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.igp.easydesign.bean.easydesign.BaseEasyDesign;
import com.igp.easydesign.bean.icon.EasyIcon;
import com.igp.easydesign.bean.icon.EasyIconType;
import com.igp.easydesign.bean.tips.EasyTips;

import java.util.List;

/**
 * Created by qiu on 2018/7/27.
 */

public abstract class BaseEasyControl {

    public abstract BaseEasyDesign getBindEasyDesign();                                             //获取绑定的设计
    public abstract void bindEasyDesign(BaseEasyDesign easyDesign);                                 //绑定的设计

    public abstract void drawDstRectLine(@NonNull Canvas canvas);                                   //绘制[最大范围矩阵]的线条  ,（提示：无论图片怎么旋转都会在这个范围内）
    public abstract void drawDstRectBg  (@NonNull Canvas canvas);                                   //绘制[最大范围矩阵]的背景  ,（提示：无论图片怎么旋转都会在这个范围内）
    public abstract void drawDstPsbg    (@NonNull Canvas canvas);                                   //绘制[范围矩阵上点阵]的背景,
    public abstract void drawDstPsPoint (@NonNull Canvas canvas);                                   //绘制[范围矩阵上点阵]的点  , (提示：坐标信息，可用于绘制图片的控制图标)
    public abstract void drawDstPsLine  (@NonNull Canvas canvas);                                   //绘制[范围矩阵上点阵]的线条
    public abstract void drawGridLine   (@NonNull Canvas canvas);                                   //绘制网格线条
    public abstract void drawLockIcon   (@NonNull Canvas canvas);                                   //绘制锁定图标
    public abstract void drawWarningIcon(@NonNull Canvas canvas);                                   //绘制警告图标
    public abstract void drawLeftTopTips(@NonNull Canvas canvas);                                   //绘制左上角提示文本

    public abstract EasyIcon getEasyIconByIconType(EasyIconType easyIconType);                      //根据类型,获取图标列表的图标
    public abstract void setEasyIconList(List<EasyIcon> easyIconList);                              //设置图标列表
    public abstract List<EasyIcon> getEasyIconList();                                               //获取图标列表
    public abstract void drawEasyIcons(Canvas canvas);                                              //绘制所有图标
    //public abstract List<EasyTips> getEasyTipsList();                                               //获取提示语列表
    //public abstract void setEasyTipsList(List<EasyTips> tipsList);                                  //设置提示语列表
}
