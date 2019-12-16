package io.qytc.p2psdk.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;

import io.qytc.p2psdk.widget.LoadingDailog;

public class LoadingDialogUtil {
    private static LoadingDailog dialog;
    private static LoadingDailog.Builder builder;

    public static final void show(Activity activity, String message) {
        Activity mActivity = new WeakReference<Activity>(activity).get();
        if (dialog == null) {
            builder = new LoadingDailog.Builder(mActivity).setMessage(message).setCancelable(true);
            dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        } else {
            updateMessage(message);
        }
    }

    public static final void show(Activity activity, int messageId) {
        Activity mActivity = new WeakReference<Activity>(activity).get();
        String message = activity.getString(messageId);
        show(mActivity, message);
    }

    public static final boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    public static final void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static final void updateMessage(String message) {
        if (builder != null) {
            builder.setMessage(message);
        }
    }

    public static final void updateMessage(int messageId) {
//        String message = TRTCApplication.mInstance.getApplicationContext().getResources().getString(messageId);
//        updateMessage(message);
    }

    /**
     * 设置此项,使其按手机返回键时不会消失
     */
    public static final void setCancelable(boolean isCancel) {
        if (null != dialog) {
            dialog.setCancelable(isCancel);
        }
    }
}
