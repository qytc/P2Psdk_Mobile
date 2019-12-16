package io.qytc.p2psdk.utils;

import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;

public class DevicesUtils {
    public static TRTCCloud checkOritation(TRTCCloud trtcCloud) {
        if (checkHardDevice()) {
            trtcCloud.setLocalViewRotation(TRTCCloudDef.TRTC_VIDEO_ROTATION_270);
            trtcCloud.setVideoEncoderRotation(TRTCCloudDef.TRTC_VIDEO_ROTATION_0);
            trtcCloud.setVideoEncoderMirror(true);//画面编码镜像
        }

        return trtcCloud;
    }

    public static boolean checkHardDevice() {
        String carrier = android.os.Build.MANUFACTURER;
        String deviceMode = android.os.Build.MODEL;
        if ("alps".equals(carrier) && "c4z".equals(deviceMode) ||
                "alps".equals(carrier) && "C8Z".equals(deviceMode)||
                "alps".equals(carrier) && "c4pro".equals(deviceMode)) {
            return true;
        }

        return false;
    }

    public static boolean isC4ZDevice(){
        String carrier = android.os.Build.MANUFACTURER;
        String deviceMode = android.os.Build.MODEL;
        if ("alps".equals(carrier) && "c4z".equals(deviceMode)) {
            return true;
        }

        return false;
    }
}
