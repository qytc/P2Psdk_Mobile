package io.qytc.p2psdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.jpush.android.api.JPushInterface;
import io.qytc.p2psdk.activity.ActivityCall;
import io.qytc.p2psdk.bean.ConfBean;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.UIEvent;
import io.qytc.p2psdk.http.UIEventStatus;
import io.qytc.p2psdk.utils.SpUtil;

/**
 * 自定义极光推送接收器
 */
public class MsgReceiver extends BroadcastReceiver {
    private static final String TAG = "MsgReceiver";
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();


        switch (action) {
            case JPushInterface.ACTION_REGISTRATION_ID:
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                SpUtil.saveString(context, SpConstant.JPUSH_DEVICE_ID, regId);
                break;
            case JPushInterface.ACTION_MESSAGE_RECEIVED:
                String string = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.d(TAG, "onReceive: " + string);

                JSONObject extra = JSON.parseObject(string);
                String cmd = extra.getString("cmd");
                Log.i(TAG, "cmd = " + cmd);

                handleOutRoom(extra);//处理未通话时的消息

                break;
            case JPushInterface.ACTION_CONNECTION_CHANGE:
                Log.i(TAG, "connected state change to " + intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false));
                break;
        }

    }

    private void handleOutRoom(JSONObject extra) {
        String cmd = extra.getString("cmd");
        if (TextUtils.isEmpty(cmd)) return;

        //超过30秒不处理
        String time = extra.getString("time");
        boolean timeOut = Long.valueOf(time) - System.currentTimeMillis() > 30 * 1000;
        if (timeOut) return;

        switch (cmd) {
            case "inviteJoinConf":
                joinCof(extra);
                break;
            case "acceptCall":
                acceptCall(extra);
                break;
        }
    }

    //被邀请加入会议
    private void joinCof(JSONObject extra) {
        String inRoom = SpUtil.getString(mContext, SpConstant.INROOM);
        if (!"0".equals(inRoom)) return;

        String chairmanName = extra.getString("chairmanName");
        String conference = extra.getString("conference");

        ConfBean bean = JSON.parseObject(conference, ConfBean.class);
        if (bean == null) return;

        Intent intent = new Intent(mContext, ActivityCall.class);

        intent.putExtra(ActivityCall.TARGET_NAME, chairmanName);
        intent.putExtra(ActivityCall.CALL_TYPE, ActivityCall.CALL_IN);
        intent.putExtra(ActivityCall.ROOM_PMI, bean.getPmi());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void acceptCall(JSONObject extra) {
        String inrRom = SpUtil.getString(mContext, SpConstant.INROOM);
        if (!"2".equals(inrRom)) return;

        String pmi = extra.getString("pmi");
        UIEvent event = new UIEvent(UIEventStatus.JPUSH_ACCEPT_CALL);
        event.setStatus(UIEventStatus.OK);
        event.setData(pmi);
        EventBusUtil.post(event);
    }
}
