package sdk.pay.uc.com.hooklaunchactivity;

import android.app.Application;
import android.content.Context;

import sdk.pay.uc.com.library.SDK;

/**
 * Created by liuguangli on 17/12/7.
 */

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SDK.init();
    }
}
