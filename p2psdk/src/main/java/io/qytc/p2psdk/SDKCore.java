package io.qytc.p2psdk;

import android.content.Context;
import android.os.Bundle;

import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;

import cn.jiguang.api.JCoreManager;
import cn.jpush.android.api.JPushInterface;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.utils.SpUtil;

public class SDKCore {

    private Context context;
    private static TRTCCloud mTrtcCloud;
    private static SDKCore sdkCore;
    public SDKCore(Context context){
        this.context=context;
        resetData();
    }

    public static SDKCore getInstance(Context context){
        if(sdkCore==null){
            sdkCore=new SDKCore(context);
        }
        return sdkCore;
    }

    private void resetData() {

        SpUtil.saveString(context, SpConstant.INROOM, "0");
        SpUtil.saveString(context, SpConstant.BROADCAST, "0");
        SpUtil.saveString(context, SpConstant.CAMERA1, "0");
        SpUtil.saveString(context, SpConstant.CAMERA2, "-1");
        SpUtil.saveString(context, SpConstant.CAMERA3, "-1");
        SpUtil.saveString(context, SpConstant.MIC, "0");
        SpUtil.saveString(context, SpConstant.SPEAK, "0");

        SpUtil.saveString(context, SpConstant.SENDBYTES, "0");
        SpUtil.saveString(context, SpConstant.RECEIVEBYTES, "0");

        SpUtil.saveString(context, SpConstant.JOIN_PMI, "0");
        SpUtil.saveString(context, SpConstant.INROOM, "0");

    }

    public TRTCCloud getTrtcCloud() {
        if (mTrtcCloud == null) {
            mTrtcCloud = TRTCCloud.sharedInstance(context);
        }
        // 预览前配置默认参数
        TRTCCloudDef.TRTCVideoEncParam encParam = new TRTCCloudDef.TRTCVideoEncParam();     // 大画面的编码器参数设置
        encParam.videoResolution = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_960_540;//分辨率
        encParam.videoFps = 15;//帧率
        encParam.videoBitrate = 1500;//码率
        encParam.videoResolutionMode = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE;
        mTrtcCloud.setVideoEncoderParam(encParam);

        TRTCCloudDef.TRTCNetworkQosParam qosParam = new TRTCCloudDef.TRTCNetworkQosParam();
        qosParam.controlMode = TRTCCloudDef.VIDEO_QOS_CONTROL_SERVER;//服务端控制
        qosParam.preference = TRTCCloudDef.TRTC_VIDEO_QOS_PREFERENCE_SMOOTH;//流畅 清晰
        mTrtcCloud.setNetworkQosParam(qosParam);

        mTrtcCloud.setPriorRemoteVideoStreamType(TRTCCloudDef.TRTC_VIDEO_STREAM_TYPE_BIG);//设置大画面
        mTrtcCloud.setLocalViewFillMode(TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FIT);//适应模式
        mTrtcCloud.setAudioRoute(TRTCCloudDef.TRTC_AUDIO_ROUTE_SPEAKER);//音频线路为：扬声器
        mTrtcCloud.setGSensorMode(TRTCCloudDef.TRTC_GSENSOR_MODE_DISABLE);//UI 不适应布局 （无重力感应）

        return mTrtcCloud;
    }

    public void initJpush() {
        Bundle bundle = new Bundle();
        // 设置心跳3s
        bundle.putInt("heartbeat_interval", 3);
        JCoreManager.setSDKConfigs(context, bundle);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
    }
}
