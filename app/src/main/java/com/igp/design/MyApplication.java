package com.igp.design;

import android.app.Application;

import com.drivemode.android.typeface.TypefaceHelper;

/**
 * AppLication
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceHelper.initialize(this);
    }
}
