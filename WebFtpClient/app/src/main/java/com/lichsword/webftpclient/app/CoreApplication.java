package com.lichsword.webftpclient.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangyue.wy on 14-3-7.
 */
public class CoreApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
