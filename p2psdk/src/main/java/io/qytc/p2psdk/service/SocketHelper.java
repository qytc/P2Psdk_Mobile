package io.qytc.p2psdk.service;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.net.URISyntaxException;

import io.qytc.p2psdk.bean.TermStatsObject;
import io.qytc.p2psdk.constant.EventConst;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.constant.UrlConstant;
import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.UIEvent;
import io.qytc.p2psdk.http.UIEventStatus;
import io.qytc.p2psdk.utils.SpUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketHelper {
    private static final String TAG = "SocketHelper";
    private static SocketHelper mInstance;
    private Context mContext;
    private Handler mHandler;
    private Socket mSocket;
    private boolean isLinked = false;//连接状态

    public static SocketHelper getInstance() {
        if (mInstance == null) {
            mInstance = new SocketHelper();
        }
        return mInstance;
    }

    public void start(Context context) {
        mContext = context;
        mHandler = new Handler();

        newSocket();
        mHeartRunnable.run();
    }

    public void stop() {
        close();
        removeSocketListener();
        mSocket = null;
    }

    private void newSocket() {
        IO.Options options = new IO.Options();
        options.forceNew = false;
        options.reconnection = false;
        options.timeout = 3000;
        String userId = SpUtil.getString(mContext, SpConstant.ID);
        try {
            mSocket = IO.socket(UrlConstant.KEEP_ALIVE_URL + "?userId=" + userId, options);
            //连接
            open();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    private void initSocketListener() {
        mSocket.on(EventConst.HANGUP, onEndConf);
        mSocket.on(EventConst.CONF_AUTO_CLOSE_NOTICE, onConfAutoCloseNotice);
    }

    private void open() {
        if (mSocket != null) {
            mSocket.on(Socket.EVENT_CONNECT, connectListener);
            mSocket.on(Socket.EVENT_DISCONNECT, disconnectListener);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, connectErrorListener);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, connectTimeOutListener);

            mSocket.connect();
            mSocket.open();
        }
    }

    private void close() {
        if (mSocket != null) {
            mSocket.off(Socket.EVENT_CONNECT, connectListener);
            mSocket.off(Socket.EVENT_DISCONNECT, disconnectListener);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, connectErrorListener);
            mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, connectTimeOutListener);

            mSocket.close();
            mSocket.disconnect();
        }
    }

    private void removeSocketListener() {
        if (mSocket == null) return;

        mSocket.off(EventConst.HANGUP, onEndConf);
        mSocket.off(EventConst.CONF_AUTO_CLOSE_NOTICE, onConfAutoCloseNotice);
    }

    private Emitter.Listener connectListener = args -> {
        Log.i(TAG, "connect success");
        initSocketListener();
        isLinked = true;
    };

    private Emitter.Listener disconnectListener = args -> {
        Log.i(TAG, "socket disconnect");
        removeSocketListener();
        isLinked = false;
    };

    private Emitter.Listener connectErrorListener = args -> isLinked = false;
    private Emitter.Listener connectTimeOutListener = args -> isLinked = false;


    private Emitter.Listener onEndConf = args -> {
        Log.i(TAG, "onEndConf" + JSON.toJSONString(args));
        if (!isLocalJoinPmi(args)) {
            return;
        }
        UIEvent event = new UIEvent(UIEventStatus.END_CONF);
        event.setStatus(UIEventStatus.OK);
        EventBusUtil.post(event);

    };

    private Emitter.Listener onConfAutoCloseNotice = args -> {
        Log.i(TAG, "onConfAutoCloseNotice->" + JSON.toJSONString(args));
        if (!isLocalJoinPmi(args)) {
            return;
        }
        UIEvent event = new UIEvent(UIEventStatus.CONF_AUTO_CLOSE_NOTICE);
        event.setStatus(UIEventStatus.OK);
        String message = JSON.parseObject(String.valueOf(args[0])).getString("message");
        event.setData(message);
        EventBusUtil.post(event);
    };

    private boolean isLocalJoinPmi(Object[] args) {
        String currentPmi = JSON.parseObject(String.valueOf(args[0])).getString("pmi");
        Log.i(TAG, "isLocalJoinPmi   currentPmi=" + currentPmi);
        if (!TextUtils.isEmpty(currentPmi)) {
            return currentPmi.equalsIgnoreCase(SpUtil.getString(mContext, SpConstant.JOIN_PMI));
        } else {
            return true;
        }
    }

    private Runnable mHeartRunnable = new Runnable() {
        @Override
        public void run() {
            sendALiveData();
            mHandler.postDelayed(this, 3 * 1000);
        }
    };

    public void sendALiveData() {
        mSocket.emit(EventConst.TERM_STATS, JSON.toJSONString(initAliveData()));

        if (!isLinked) {
            open();//重新连接
        }
    }

    //初始化心跳数据
    private TermStatsObject initAliveData() {
        TermStatsObject termStatsObject = new TermStatsObject();
        termStatsObject.setId(Long.parseLong(SpUtil.getString(mContext, SpConstant.ID)));
        termStatsObject.setType(SpConstant.type);
        termStatsObject.setNumber(SpUtil.getString(mContext, SpConstant.SERIAL));
        termStatsObject.setName(SpUtil.getString(mContext, SpConstant.NAME));
        termStatsObject.setImage(SpUtil.getString(mContext, SpConstant.IMAGE));
        termStatsObject.setDeptId(Long.parseLong(SpUtil.getString(mContext, SpConstant.DEPTID)));
        termStatsObject.setTenantId(Long.parseLong(SpUtil.getString(mContext, SpConstant.TENANTID)));
        termStatsObject.setBindTerm(null);
        termStatsObject.setDeviceId(SpUtil.getString(mContext, SpConstant.JPUSH_DEVICE_ID));
        termStatsObject.setSocketId(SpUtil.getString(mContext, SpConstant.SOCKETID));
        termStatsObject.setPmi(SpUtil.getString(mContext, SpConstant.PMI));
        termStatsObject.setConfPmi(SpUtil.getString(mContext, SpConstant.JOIN_PMI));
        termStatsObject.setInRoom(Integer.parseInt(SpUtil.getString(mContext, SpConstant.INROOM)));
        termStatsObject.setSpeak(Integer.parseInt(SpUtil.getString(mContext, SpConstant.SPEAK)));
        termStatsObject.setCamera1(Integer.parseInt(SpUtil.getString(mContext, SpConstant.CAMERA1)));
        termStatsObject.setCamera2(Integer.parseInt(SpUtil.getString(mContext, SpConstant.CAMERA2)));
        termStatsObject.setCamera3(Integer.parseInt(SpUtil.getString(mContext, SpConstant.CAMERA3)));
        termStatsObject.setMic(Integer.parseInt(SpUtil.getString(mContext, SpConstant.MIC)));
        termStatsObject.setBroadcast(Integer.parseInt(SpUtil.getString(mContext, SpConstant.BROADCAST)));
        termStatsObject.setMaker(android.os.Build.MANUFACTURER);
        termStatsObject.setCpuStats(SpUtil.getString(mContext, SpConstant.CPUSTATS));
        termStatsObject.setMemStats(SpUtil.getString(mContext, SpConstant.MEMSTATS));
        termStatsObject.setSendByte(Double.parseDouble(SpUtil.getString(mContext, SpConstant.SENDBYTES)));
        termStatsObject.setReceiveByte(Double.parseDouble(SpUtil.getString(mContext, SpConstant.RECEIVEBYTES)));
        return termStatsObject;
    }
}
