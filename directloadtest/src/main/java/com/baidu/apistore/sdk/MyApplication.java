package com.baidu.apistore.sdk;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        // FIR.init(this);
        ApiStoreSDK.init("961b692b31116fcba8a547288c7ae32b");
        CrashHandler.getInstance().init(context);
    }

    public static Context getContextObject() {
        return MyApplication.context;
    }
}
