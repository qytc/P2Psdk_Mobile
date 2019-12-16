package io.qytc.p2psdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogBtUtils {
    public static View setBtFocus(Context context, int viewLayout, int sureTvLayout, int sureRlLayout, int cancelTvLayout, int cancelRlLayout) {
        View view = LayoutInflater.from(context).inflate(viewLayout, null);
        TextView sureTv = view.findViewById(sureTvLayout);
        RelativeLayout sureRl = view.findViewById(sureRlLayout);
        sureTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    sureRl.setSelected(true);
                } else {
                    sureRl.setSelected(false);
                }
            }
        });

        TextView cancelTv = view.findViewById(cancelTvLayout);
        RelativeLayout cancelRl = view.findViewById(cancelRlLayout);
        cancelTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    cancelRl.setSelected(true);
                } else {
                    cancelRl.setSelected(false);
                }
            }
        });
        return view;
    }

    public static void setDialogSize(Dialog dialog,Context context){
        Window window = dialog.getWindow();
        if(window != null){
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width= WindowUtils.getScreenWidth(context);
            attributes.height=WindowUtils.getScreenHeight(context);
            window.setAttributes(attributes);
        }

    }
}
