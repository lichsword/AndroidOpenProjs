package com.lichsword.codeoffamilywang.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangyue.wy on 14-3-7.
 */
public class CoreApplication extends Application {

    public Context sContext;

    private boolean printLogOn = false;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
    }

    private void init() {
        printLogOn = getString(R.string.printLogOn).equals("1");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sContext = null;
    }
}
