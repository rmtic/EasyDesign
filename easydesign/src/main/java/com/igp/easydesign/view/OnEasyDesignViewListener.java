package com.igp.easydesign.view;

import com.igp.easydesign.bean.easydesign.BaseEasyDesign;

public interface OnEasyDesignViewListener {
    public void onEasyDesignChange(BaseEasyDesign easyDesign,EasyEventType easyEventType);  //当前设计发生变化的情况
}