package io.qytc.p2psdk.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.utils.SpUtil;
import io.qytc.p2psdk.utils.WindowUtils;


public class VideoLayout extends RelativeLayout {
    private static final String TAG = "VideoLayout";
    private Context mContext;
    private List<QyVideoView> mVideoViewList;//所有视频控件的集合
    private int maxCount = 2;//支持最大画面数量
    private String myUserId;
    private List<LayoutParams> mLayoutParamsList;

    public VideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        myUserId = SpUtil.getString(mContext, SpConstant.ID);

        initLayoutParam();

        initTXCloudVideoView();
    }

    private void initLayoutParam() {
        mLayoutParamsList = new ArrayList<>();
        int screenWidth = WindowUtils.getScreenWidth(mContext);
        int screenHeight = WindowUtils.getScreenHeight(mContext);

        LayoutParams lp0 = new LayoutParams(screenWidth, screenHeight);
        mLayoutParamsList.add(lp0);

        LayoutParams lp1 = new LayoutParams(screenWidth / 4, screenHeight / 4);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mLayoutParamsList.add(lp1);
    }

    private void initTXCloudVideoView() {
        mVideoViewList = new ArrayList<>();
        removeAllViews();
        for (int i = 0; i < maxCount; i++) {
            QyVideoView qyVideoView = new QyVideoView(mContext);
            qyVideoView.setLayoutParams(mLayoutParamsList.get(i));
            mVideoViewList.add(qyVideoView);
            addView(qyVideoView);
        }
    }

    //根据画面数量刷新布局
    public void applyLayoutParam() {
        Log.i(TAG, "--applyLayoutParam--");

        int videoViewCount = 0;
        for (QyVideoView qyVideoView : mVideoViewList) {
            if (!TextUtils.isEmpty(qyVideoView.getUserId())) {
                videoViewCount++;
            }
        }

        if (videoViewCount <= 1) {
            QyVideoView selfVideoView = getVideoViewByUseId(myUserId);
            selfVideoView.setLayoutParams(mLayoutParamsList.get(0));
            selfVideoView.bringToFront();
        } else {
            for (QyVideoView qyVideoView : mVideoViewList) {
                if (myUserId.equals(qyVideoView.getUserId())) {
                    qyVideoView.setLayoutParams(mLayoutParamsList.get(1));
                    qyVideoView.bringToFront();
                } else {
                    qyVideoView.setLayoutParams(mLayoutParamsList.get(0));
                }
            }
        }
    }

    /**
     * 其他用户加入房间
     */
    public QyVideoView onMemberEnter(String userId) {
        Log.i(TAG, "--onMemberEnter--,userId=" + userId);
        if (TextUtils.isEmpty(userId)) return null;

        QyVideoView videoView = getVideoViewByUseId(userId);
        if (videoView != null) {
            return videoView;
        }

        QyVideoView cloudVideoView = getFreeVideoView(userId);
        if (cloudVideoView != null) {
            applyLayoutParam();
            return cloudVideoView;
        }

        return null;
    }

    /**
     * 用户离开房间
     */
    public void onMemberLeave(String userId) {
        Log.i(TAG, "--onMemberLeave--,userId=" + userId);
        if (TextUtils.isEmpty(userId)) return;

        QyVideoView videoView = getVideoViewByUseId(userId);
        if (videoView != null) {
            videoView.setUserId(null, null);
            videoView.setVisibility(GONE);
        }
        applyLayoutParam();
    }

    /**
     * 通过用户id获取videoView
     */
    public QyVideoView getVideoViewByUseId(String userId) {
        Log.i(TAG, "--getVideoViewByUseId--,userId=" + userId);
        if (userId == null) return null;

        for (QyVideoView videoView : mVideoViewList) {
            String tempUserID = videoView.getUserId();
            if (!TextUtils.isEmpty(tempUserID) && tempUserID.equalsIgnoreCase(userId)) {
                videoView.setUserId(userId, null);
                return videoView;
            }
        }

        return null;
    }

    /**
     * 获取一个空的videoView
     */
    public QyVideoView getFreeVideoView(String userId) {
        Log.i(TAG, "--getFreeVideoView--,userId=" + userId);
        for (QyVideoView videoView : mVideoViewList) {
            String tempUserID = videoView.getUserId();
            if (TextUtils.isEmpty(tempUserID)) {
                videoView.setUserId(userId, null);
                return videoView;
            }
        }

        return null;
    }
}
