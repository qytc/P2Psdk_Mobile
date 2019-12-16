package io.qytc.p2psdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.ResponseEvent;
import io.qytc.p2psdk.eventcore.UIEvent;
import io.qytc.p2psdk.R;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.http.DoHttpManager;
import io.qytc.p2psdk.http.ResponseEventStatus;
import io.qytc.p2psdk.http.UIEventStatus;
import io.qytc.p2psdk.utils.SpUtil;
import io.qytc.p2psdk.utils.ToastUtils;

public class ActivityCall extends Activity implements View.OnClickListener {

    private MediaPlayer mMediaPlayer;
    public static final int CALL_OUT = 100;
    public static final int CALL_IN = 101;

    public static final String CALL_TYPE = "call_type";
    public static final String TARGET_NAME = "target_name";
    public static final String TARGET_PMI = "target_pmi";
    public static final String ROOM_PMI = "room_pmi";
    private int mCall_type;
    private String mTargetName;
    private Handler mHandler = new Handler();
    private String mRoomPmi;
    private String mTargetPmi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_call);

        EventBusUtil.register(this);

        initData();

        initView();
    }

    private void initData() {
        SpUtil.saveString(this, SpConstant.INROOM, "2");
        SpUtil.saveString(this, SpConstant.JOIN_PMI, "0");

        Intent intent = getIntent();
        mCall_type = intent.getIntExtra(CALL_TYPE, CALL_OUT);
        mTargetName = intent.getStringExtra(TARGET_NAME);
        mRoomPmi = intent.getStringExtra(ROOM_PMI);
        mTargetPmi = intent.getStringExtra(TARGET_PMI);

        mHandler.postDelayed(mTimeOutRunnable, 30 * 1000);
    }

    private void initView() {
        TextView tv_remote_number = findViewById(R.id.tv_remote_number);
        TextView tv_call_tip = findViewById(R.id.tv_call_tip);
        if (mCall_type == CALL_OUT) {
            tv_call_tip.setText("正在呼叫");
            tv_remote_number.setText(mTargetPmi);
        } else {
            tv_call_tip.setText("收到来电");
            tv_remote_number.setText(mTargetName);
        }

        ImageView iv_accept = findViewById(R.id.iv_accept);
        ImageView iv_hangup = findViewById(R.id.iv_hangup);

        if (mCall_type == CALL_OUT) {
            iv_accept.setVisibility(View.GONE);
        } else {
            iv_accept.setVisibility(View.VISIBLE);
        }

        iv_accept.setOnClickListener(this);
        iv_hangup.setOnClickListener(this);
    }

    //超时挂断
    private Runnable mTimeOutRunnable = new Runnable() {
        @Override
        public void run() {
            DoHttpManager.getInstance().refuseCall(ActivityCall.this, mRoomPmi);
            cancelCall();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimeOutRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAlarm();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAlarm();
    }

    private void startAlarm() {
        try {
            mMediaPlayer = MediaPlayer.create(this, R.raw.avchat_ring);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopAlarm() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.iv_accept){
            DoHttpManager.getInstance().acceptCall(this, mRoomPmi);

        }else if(view.getId()==R.id.iv_hangup){
            if (mCall_type == CALL_IN) {
                DoHttpManager.getInstance().refuseCall(this, mRoomPmi);
            } else {
                DoHttpManager.getInstance().endP2PCall(this, mRoomPmi);
            }
            cancelCall();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DoHttpManager.getInstance().refuseCall(ActivityCall.this, mRoomPmi);
            cancelCall();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onEventMainThread(ResponseEvent event) {
        if (event == null || event.getStatus() != ResponseEventStatus.OK) {
            return;
        }

        switch (event.getId()) {
            case ResponseEventStatus.CALL_ACCEPT:
                acceptCall();
                break;
        }
    }

    public void onEventMainThread(UIEvent event) {
        if (event == null || event.getStatus() != UIEventStatus.OK) {
            return;
        }

        switch (event.getId()) {
            case UIEventStatus.END_CONF:
                cancelCall();
            case UIEventStatus.JPUSH_REFUSE_CALL:
                ToastUtils.toast(ActivityCall.this, R.string.target_user_is_busy,false);
                cancelCall();
                break;
            case UIEventStatus.JPUSH_ACCEPT_CALL:
                acceptCall();
                break;
        }
    }

    private void acceptCall() {
        SpUtil.saveString(this, SpConstant.INROOM, "1");
        SpUtil.saveString(this, SpConstant.JOIN_PMI, mRoomPmi);
        finish();
        Intent intent = new Intent(this, ActivityVideo.class);
        startActivity(intent);
    }

    private void cancelCall() {
        SpUtil.saveString(this, SpConstant.INROOM, "0");
        SpUtil.saveString(this, SpConstant.JOIN_PMI, "0");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }
}
