package io.qytc.p2psdk.http;

import android.app.Activity;

import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.ResponseEvent;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.http.response.BaseResponse;
import io.qytc.p2psdk.http.response.CreatConfResponse;
import io.qytc.p2psdk.http.response.LoginResponse;
import io.qytc.p2psdk.service.SocketConnectService;
import io.qytc.p2psdk.utils.LoadingDialogUtil;
import io.qytc.p2psdk.utils.SpUtil;
import io.qytc.p2psdk.utils.ToastUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DoHttpManager {
    private volatile static DoHttpManager INSTANCE;

    //构造方法私有
    private DoHttpManager() {
    }

    //获取单例
    public static DoHttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DoHttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoHttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 登录
     */
    public void auth(Activity activity, String number, String password) {
        TerminalHttpService terminalHttpService = HttpManager.getInstance().getRetrofit().create(TerminalHttpService.class);
        String deviceId = SpUtil.getString(activity, SpConstant.JPUSH_DEVICE_ID);
        terminalHttpService.auth(SpConstant.APP_ID, String.valueOf(SpConstant.type), number, password, deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<LoginResponse>(activity) {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        ToastUtils.toast(activity, responeThrowable.message,false);
                        LoadingDialogUtil.dismiss();

                        ResponseEvent event = new ResponseEvent(ResponseEventStatus.LOGIN_ID);
                        event.setStatus(ResponseEventStatus.ERROR);
                        EventBusUtil.post(event);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        LoadingDialogUtil.dismiss();
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        ResponseEvent event = new ResponseEvent(ResponseEventStatus.LOGIN_ID);
                        if (!loginResponse.getCode().equalsIgnoreCase("0")) {
                            if ("终端号码未注册".equals(loginResponse.getMsg())) {
                                event.setStatus(ResponseEventStatus.UNREGISTERED);
                                event.setMessage(loginResponse.getMsg());
                            } else {
                                ToastUtils.toast(activity, loginResponse.getMsg(),false);
                                event.setStatus(ResponseEventStatus.ERROR);
                                event.setMessage(loginResponse.getMsg());
                            }
                        } else {
                            event.setStatus(ResponseEventStatus.OK);
                        }
                        event.setData(loginResponse);
                        EventBusUtil.post(event);
                    }
                });
    }

    /**
     * 呼叫联系人
     */
    public void p2pCall(Activity activity, String pmi) {
        TerminalHttpService terminalHttpService = HttpManager.getInstance().getRetrofit().create(TerminalHttpService.class);
        String accessToken = SpUtil.getString(activity, SpConstant.ACCESS_TOKEN);
        terminalHttpService.p2pCall(pmi, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<CreatConfResponse>(activity) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        SpUtil.saveString(activity, SpConstant.INROOM, "2");
                        SocketConnectService.getInstance().sendALiveData();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responseThrowable) {
                        ToastUtils.toast(activity, responseThrowable.message,false);
                        SpUtil.saveString(activity, SpConstant.INROOM, "0");
                        SocketConnectService.getInstance().sendALiveData();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onNext(CreatConfResponse creatConfResponse) {
                        if (!creatConfResponse.getCode().equalsIgnoreCase("0")) {
                            SpUtil.saveString(activity, SpConstant.INROOM, "0");
                            SocketConnectService.getInstance().sendALiveData();
                            ToastUtils.toast(activity, creatConfResponse.getMsg(),false);
                        }else {
                            ResponseEvent event = new ResponseEvent(ResponseEventStatus.CREAT_P2PCALL);
                            event.setStatus(ResponseEventStatus.OK);
                            event.setData(creatConfResponse);
                            EventBusUtil.post(event);
                        }
                    }
                });
    }

    /**
         * 同意接听
     */
    public void acceptCall(Activity activity, String pmi) {
        TerminalHttpService terminalHttpService = HttpManager.getInstance().getRetrofit().create(TerminalHttpService.class);
        String accessToken = SpUtil.getString(activity, SpConstant.ACCESS_TOKEN);
        terminalHttpService.acceptCall(pmi, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseResponse>(activity) {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responseThrowable) {
                        ToastUtils.toast(activity, responseThrowable.message,false);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getCode().equalsIgnoreCase("0")) {
                            ResponseEvent event = new ResponseEvent(ResponseEventStatus.CALL_ACCEPT);
                            event.setStatus(ResponseEventStatus.OK);
                            EventBusUtil.post(event);
                        }
                    }
                });
    }

    /**
     * 拒绝接听
     */
    public void refuseCall(Activity activity, String pmi) {
        TerminalHttpService terminalHttpService = HttpManager.getInstance().getRetrofit().create(TerminalHttpService.class);
        String accessToken = SpUtil.getString(activity, SpConstant.ACCESS_TOKEN);
        terminalHttpService.refuseCall(pmi, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseResponse>(activity) {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responseThrowable) {
                        SocketConnectService.getInstance().sendALiveData();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {

                    }
                });
    }

    /**
     * 结束点对点通话
     */
    public void endP2PCall(Activity activity, String pmi) {
        TerminalHttpService terminalHttpService = HttpManager.getInstance().getRetrofit().create(TerminalHttpService.class);
        String accessToken = SpUtil.getString(activity, SpConstant.ACCESS_TOKEN);
        terminalHttpService.endP2PCall(pmi, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseResponse>(activity) {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responseThrowable) {

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {

                    }
                });
    }
}
