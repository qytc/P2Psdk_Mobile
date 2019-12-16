package io.qytc.vc;

import androidx.multidex.MultiDexApplication;

import io.qytc.p2psdk.SDKCore;

public class TRTCApplication extends MultiDexApplication {

    public static TRTCApplication mInstance;
    public SDKCore sdkCore;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sdkCore=new SDKCore(mInstance);
        sdkCore.initJpush();
    }
}
