package io.qytc.p2psdk.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.qytc.p2psdk.R;

/**
 * 提示工具类
 */
public class ToastUtils {
    private static Toast mToast;

    public static void toast(Context context, int resId,boolean b) {
        Toast toast = initToast(context,b);
        TextView textView = toast.getView().findViewById(R.id.toast_tv);
        textView.setText(context.getResources().getString(resId));
        toast.show();
    }

    public static void toast(Context context, String message,boolean b) {
        Toast toast = initToast(context,b);
        TextView textView = toast.getView().findViewById(R.id.toast_tv);
        textView.setText(message);
        toast.show();
    }

    public static void toast(Context context, int message, int duration,boolean b) {
        Toast toast = initToast(context,b);
        toast.setDuration(duration);
        TextView textView = toast.getView().findViewById(R.id.toast_tv);
        textView.setText(message);
        toast.show();
    }

    /**
     *
     * @param context
     * @param b true上false下
     * @return
     */
    private static Toast initToast(Context context,boolean b) {
        // 创建一个Toast提示信息
        Toast toast = new Toast(context);
        View view = null;
        if(b){
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
            view = LayoutInflater.from(context).inflate(R.layout.toast_top, null);
        }else{
            toast.setGravity(Gravity.CENTER, 0, 0);
            view = LayoutInflater.from(context).inflate(R.layout.toast, null);
        }
        // 设置Toast显示自定义View
        toast.setView(view);
        // 设置Toast的显示时间
        toast.setDuration(Toast.LENGTH_SHORT);

        return toast;
    }
}
