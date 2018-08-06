package com.igp.easydesign.view;

import com.igp.easydesign.bean.easydesign.BaseEasyDesign;

public interface OnEasyDesignViewListener {
    public void onEasyDesignChange(BaseEasyDesign easyDesign);  //当前设计发生变化的情况
    public void onEasyDesignSelected(BaseEasyDesign easyDesign);//设计被选中的情况
}
