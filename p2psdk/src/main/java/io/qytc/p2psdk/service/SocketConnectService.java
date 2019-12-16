package io.qytc.p2psdk.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import java.net.URISyntaxException;

import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.UIEvent;
import io.qytc.p2psdk.bean.TermStatsObject;
import io.qytc.p2psdk.constant.EventConst;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.constant.UrlConstant;
import io.qytc.p2psdk.http.UIEventStatus;
import io.qytc.p2psdk.utils.SpUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketConnectService extends Service {
    private final String TAG = "SocketConnectService";
    private Context mContext;
    private Handler mHandler = new Handler();
    private Socket mSocket;
    private boolean mConnectError = false;//是否连接错误
    private TermStatsObject mTermStatsObject;
    @SuppressLint("StaticFieldLeak")
    private static SocketConnectService mInstance;
    private IO.Options mOptions;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static SocketConnectService getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this;

        mHandler = new Handler();

        initConnectOptionSetting();

        connectSocket();

        mHeartRunnable.run();
    }

    private void initConnectOptionSetting() {
        //初始化连接设置
        mOptions = new IO.Options();
        mOptions.timeout = 5000;//设置连接超时时间
        mOptions.multiplex = true;
        mOptions.reconnection = true;
        mOptions.reconnectionDelay = 500;
        mOptions.reconnectionAttempts = 10;
    }

    private void connectSocket() {
        String userId = SpUtil.getString(this, SpConstant.ID);

        try {
            mSocket = IO.socket(UrlConstant.KEEP_ALIVE_URL + "?userId=" + userId, mOptions);
            mConnectError = false;

            if (mSocket != null) {
                mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
                mSocket.on(EventConst.OTHER_LOGIN, onOthersLogin);
                mSocket.on(EventConst.HANGUP, onEndConf);
                mSocket.on(EventConst.CONF_AUTO_CLOSE_NOTICE, onConfAutoCloseNotice);
                mSocket.connect();
            }

        } catch (URISyntaxException e) {
            mConnectError = true;
            e.printStackTrace();
        }
    }

    private Emitter.Listener onConnectError = args -> {
        Log.i(TAG, "connect error");
        mConnectError = true;
    };

    private Runnable mHeartRunnable = new Runnable() {
        @Override
        public void run() {
            sendALiveData();

            mHandler.postDelayed(this, 3 * 1000);
        }
    };

    public void sendALiveData() {
        initAliveData();
        if (mSocket != null && !mConnectError) {
            mSocket.emit(EventConst.TERM_STATS, JSON.toJSONString(mTermStatsObject));
        } else {
            connectSocket();
        }
    }

    //初始化心跳数据
    private void initAliveData() {
        mTermStatsObject = new TermStatsObject();
        mTermStatsObject.setId(Long.parseLong(SpUtil.getString(mContext, SpConstant.ID)));
        mTermStatsObject.setType(SpConstant.type);
        mTermStatsObject.setNumber(SpUtil.getString(mContext, SpConstant.SERIAL));
        mTermStatsObject.setName(SpUtil.getString(mContext, SpConstant.NAME));
        mTermStatsObject.setImage(SpUtil.getString(mContext, SpConstant.IMAGE));
        mTermStatsObject.setDeptId(Long.parseLong(SpUtil.getString(mContext, SpConstant.DEPTID)));
        mTermStatsObject.setTenantId(Long.parseLong(SpUtil.getString(mContext, SpConstant.TENANTID)));
        mTermStatsObject.setBindTerm(null);
        mTermStatsObject.setDeviceId(SpUtil.getString(mContext, SpConstant.JPUSH_DEVICE_ID));
        mTermStatsObject.setSocketId(SpUtil.getString(mContext, SpConstant.SOCKETID));
        mTermStatsObject.setPmi(SpUtil.getString(mContext, SpConstant.PMI));
        mTermStatsObject.setConfPmi(SpUtil.getString(mContext, SpConstant.JOIN_PMI));
        mTermStatsObject.setInRoom(Integer.parseInt(SpUtil.getString(mContext, SpConstant.INROOM)));
        mTermStatsObject.setSpeak(Integer.parseInt(SpUtil.getString(mContext, SpConstant.SPEAK)));
        mTermStatsObject.setCamera1(Integer.parseInt(SpUtil.getString(mContext, SpConstant.CAMERA1)));
        mTermStatsObject.setCamera2(Integer.parseInt(SpUtil.getString(mContext, SpConstant.CAMERA2)));
        mTermStatsObject.setCamera3(Integer.parseInt(SpUtil.getString(mContext, SpConstant.CAMERA3)));
        mTermStatsObject.setMic(Integer.parseInt(SpUtil.getString(mContext, SpConstant.MIC)));
        mTermStatsObject.setBroadcast(Integer.parseInt(SpUtil.getString(mContext, SpConstant.BROADCAST)));
        mTermStatsObject.setMaker(SpUtil.getString(mContext, SpConstant.MAKER));
        mTermStatsObject.setCpuStats(SpUtil.getString(mContext, SpConstant.CPUSTATS));
        mTermStatsObject.setMemStats(SpUtil.getString(mContext, SpConstant.MEMSTATS));
        mTermStatsObject.setSendByte(Double.parseDouble(SpUtil.getString(mContext, SpConstant.SENDBYTES)));
        mTermStatsObject.setReceiveByte(Double.parseDouble(SpUtil.getString(mContext, SpConstant.RECEIVEBYTES)));
    }

    private Emitter.Listener onOthersLogin = args -> {
        Log.i(TAG, "onOthersLogin" + args[0]);

    };

    private Emitter.Listener onEndConf = args -> {
        if (!isLocalJoinPmi(args))
            return;
        Log.i(TAG, "onEndConf" + JSON.toJSONString(args));
        //["{\"time\":\"1574825292278\"}"]
        UIEvent event = new UIEvent(UIEventStatus.END_CONF);
        event.setStatus(UIEventStatus.OK);
        EventBusUtil.post(event);
    };

    private Emitter.Listener onConfAutoCloseNotice = args -> {
        if (!isLocalJoinPmi(args))
            return;
        Log.i(TAG, "onConfAutoCloseNotice" + JSON.toJSONString(args));
        UIEvent event = new UIEvent(UIEventStatus.CONF_AUTO_CLOSE_NOTICE);
        event.setStatus(UIEventStatus.OK);
        String message = JSON.parseObject(String.valueOf(args[0])).getString("message");
        event.setData(message);
        EventBusUtil.post(event);
    };

    private boolean isLocalJoinPmi(Object[] args) {
        Log.i(TAG, "SocketConnectService" + JSON.toJSONString(args));
        String currentPmi = JSON.parseObject(String.valueOf(args[0])).getString("pmi");
        if (!TextUtils.isEmpty(currentPmi)) {
            return currentPmi.equalsIgnoreCase(SpUtil.getString(mContext, SpConstant.JOIN_PMI));
        } else {
            return true;
        }
    }
}
