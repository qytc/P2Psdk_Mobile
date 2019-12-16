package io.qytc.p2psdk.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.tencent.liteav.TXLiteAVCode;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

import java.lang.ref.WeakReference;

import io.qytc.p2psdk.SDKCore;
import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.UIEvent;
import io.qytc.p2psdk.R;

import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.http.DoHttpManager;
import io.qytc.p2psdk.http.UIEventStatus;
import io.qytc.p2psdk.utils.DevicesUtils;
import io.qytc.p2psdk.utils.SpUtil;
import io.qytc.p2psdk.utils.ToastUtils;
import io.qytc.p2psdk.widget.QyVideoView;
import io.qytc.p2psdk.widget.VideoLayout;

public class ActivityVideo extends Activity {

    private static final String TAG = "ActivityVideo";
    private TRTCCloud mTrtcCloud;
    private TRTCCloudDef.TRTCParams mTrtcParams;
    private String mUserId;
    private String mRoomNo;
    private VideoLayout videoLayout;
    private Dialog mExitVideoDialog;
    private ActivityVideo mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);

        mActivity = this;
        EventBusUtil.register(this);

        initView();

        initTrtcAndEnterRoom();
    }

    private void initView() {
        videoLayout = findViewById(R.id.video_layout);
    }

    private void initTrtcAndEnterRoom() {
        mUserId = SpUtil.getString(this, SpConstant.ID);
        String userSig = SpUtil.getString(this, SpConstant.USERSIG);
        mRoomNo = SpUtil.getString(this, SpConstant.JOIN_PMI);

        mTrtcParams = new TRTCCloudDef.TRTCParams(Integer.valueOf(SpConstant.APP_ID), mUserId, userSig, Integer.valueOf(mRoomNo), "", "");

        mTrtcCloud = SDKCore.getInstance(this).getTrtcCloud();
        mTrtcCloud.setListener(new TRTCCloudListenerImpl(this));

        QyVideoView videoView = videoLayout.getFreeVideoView(mUserId);
        mTrtcCloud.startLocalPreview(true, videoView.getVideoView());
        DevicesUtils.checkOritation(mTrtcCloud);
        mTrtcCloud.startLocalAudio();

        mTrtcCloud.enterRoom(mTrtcParams, TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL);
    }


    private static class TRTCCloudListenerImpl extends TRTCCloudListener implements TRTCCloudListener.TRTCVideoRenderListener {
        private WeakReference<ActivityVideo> mContext; //弱引用ScreenAv

        TRTCCloudListenerImpl(ActivityVideo activity) {
            super();
            mContext = new WeakReference<>(activity);
        }

        /**
         * 一些警告 WARNING
         * 大多是一些可以忽略的事件通知，SDK内部会启动一定的补救机制
         */
        @Override
        public void onWarning(int warningCode, String warningMsg, Bundle extraInfo) {
            Log.i(TAG, "--onWarning() , warningCode = " + warningCode + " warningMsg =" + warningMsg);
        }

        /**
         * 发生错误 ERROR
         * 大多是不可恢复的错误，需要通过 UI 提示用户
         */
        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Log.i(TAG, "--onError() , errCode = " + errCode + " errMsg =" + errMsg);
            ActivityVideo activity = mContext.get();
            if (activity != null) {
                ToastUtils.toast(activity, "发生错误: " + errMsg + "[" + errCode + "]",false);
                if (errCode == TXLiteAVCode.ERR_ROOM_ENTER_FAIL) {  //严重错误，退出房间
                    activity.exitRoom();
                }
            }
        }

        /**
         * 当前用户离开房间
         */
        @Override
        public void onExitRoom(int reason) {
            Log.i(TAG, "--onExitRoom() , reason =" + reason);
            ActivityVideo activity = mContext.get();
            if (activity != null) {
                activity.finish();
            }
        }

        @Override
        public void onUserExit(String userId, int reason) {
            Log.i(TAG, "--onUserExit() , userId = " + userId + " reason = " + reason);
            ActivityVideo activity = mContext.get();
            if (activity == null || TextUtils.isEmpty(userId)) return;

            activity.mTrtcCloud.stopRemoteView(userId);
            activity.videoLayout.onMemberLeave(userId);
        }

        /**
         * 当用户开始视频流或关闭视频流
         */
        @Override
        public void onUserVideoAvailable(final String userId, boolean available) {
            Log.i(TAG, "--onUserVideoAvailable() , userId = " + userId + " available = " + available);
            ActivityVideo activity = mContext.get();
            if (activity == null || TextUtils.isEmpty(userId)) return;

            if (available) {
                QyVideoView videoView = activity.videoLayout.onMemberEnter(userId);
                if (videoView != null) {
                    activity.mTrtcCloud.setRemoteViewFillMode(userId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FIT);//适应模式
                    activity.mTrtcCloud.setRemoteViewRotation(userId, TRTCCloudDef.TRTC_VIDEO_ROTATION_0);
                    activity.mTrtcCloud.startRemoteView(userId, videoView.getVideoView());
                }
            } else {
                activity.mTrtcCloud.stopRemoteView(userId);
                activity.videoLayout.onMemberLeave(userId);
            }
        }

        @Override
        public void onRenderVideoFrame(String s, int i, TRTCCloudDef.TRTCVideoFrame trtcVideoFrame) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                loadExitDialog();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loadExitDialog() {
        if (mExitVideoDialog == null) {
            mExitVideoDialog = new Dialog(this, R.style.dialogstyle);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_exit_video, null);
        view.findViewById(R.id.tv_exit).setOnClickListener(view1 -> {
            mExitVideoDialog.dismiss();
            DoHttpManager.getInstance().endP2PCall(mActivity, mRoomNo);
            exitRoom();
        });

        view.findViewById(R.id.tv_cancel).setOnClickListener(view12 -> mExitVideoDialog.dismiss());
        mExitVideoDialog.setContentView(view);
        mExitVideoDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mExitVideoDialog.show();
    }

    public void onEventMainThread(UIEvent event) {
        if (event == null || event.getStatus() != UIEventStatus.OK) {
            return;
        }

        switch (event.getId()) {
            case UIEventStatus.END_CONF:
                ToastUtils.toast(mActivity, getString(R.string.call_has_ended),false);
                exitRoom();
                break;
            case UIEventStatus.CONF_AUTO_CLOSE_NOTICE:
                String message = event.getData();
                ToastUtils.toast(mActivity, message,false);
                break;
        }
    }

    private void exitRoom() {
        if (mTrtcCloud != null) {
            mTrtcCloud.exitRoom();
        } else {
            finish();
        }

        SpUtil.saveString(mActivity, SpConstant.INROOM, "0");
        SpUtil.saveString(mActivity, SpConstant.JOIN_PMI, "0");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }
}
