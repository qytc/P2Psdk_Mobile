package io.qytc.p2psdk.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 窗口工具类
 */
public class WindowUtils {
    /**
     * 关闭软键盘
     */
    public static void hideSoftInput(Window window, EditText softInput) {

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(softInput, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getScreenWidth(Context context) {
        if (context == null) return 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null) return 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static List<List<RelativeLayout.LayoutParams>> initMultiLayout(Context context) {
        List<List<RelativeLayout.LayoutParams>> list = new ArrayList<>();

        int screenWidth = getScreenWidth(context);
        int screenHeight = getScreenHeight(context);
        int screenWidth2 = screenWidth / 2;
        int screenHeight2 = screenHeight / 2;
        int screenWidth3 = screenWidth / 3;
        int screenHeight3 = screenHeight / 3;
        int screenWidth4 = screenWidth / 4;
        int screenHeight4 = screenHeight / 4;
        int screenWidth5 = screenWidth / 5;
        int screenHeight5 = screenHeight / 5;

        list.add(initLayoutParam1(screenWidth, screenHeight));
        list.add(initLayoutParam2_0(screenWidth4, screenHeight4));
        list.add(initLayoutParam2_1(screenWidth2, screenHeight2));
        list.add(initLayoutParam3(screenWidth2, screenHeight2));
        list.add(initLayoutParam4_0(screenWidth3, screenHeight3));
        list.add(initLayoutParam4_1(screenWidth2, screenHeight2));
        list.add(initLayoutParam5(screenWidth4, screenHeight4));
        list.add(initLayoutParam6_0(screenWidth3, screenHeight3));
        list.add(initLayoutParam6_1(screenWidth3, screenHeight3));
        list.add(initLayoutParam8(screenWidth4, screenHeight4));
        list.add(initLayoutParam9(screenWidth3, screenHeight3));
        list.add(initLayoutParam12(screenWidth4, screenHeight4));
        list.add(initLayoutParam13(screenWidth4, screenHeight4));
        list.add(initLayoutParam15(screenWidth5, screenHeight5));
        list.add(initLayoutParam16(screenWidth4, screenHeight4));

        return list;
    }

    //1画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam1(int screenWidth, int screenHeight) {
        List<RelativeLayout.LayoutParams> layoutParams1 = new ArrayList<>();
        layoutParams1.add(new RelativeLayout.LayoutParams(screenWidth, screenHeight));
        return layoutParams1;
    }

    //2_0画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam2_0(int screenWidth4, int screenHeight4) {
        List<RelativeLayout.LayoutParams> layoutParams2_0 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth4 * 4, screenWidth4 * 4);
        layoutParams2_0.add(lp0);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams2_0.add(lp1);

        return layoutParams2_0;
    }

    //2_1画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam2_1(int screenWidth2, int screenHeight2) {
        List<RelativeLayout.LayoutParams> layoutParams2_1 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp0.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams2_1.add(lp0);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp1.addRule(RelativeLayout.CENTER_VERTICAL);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams2_1.add(lp1);

        return layoutParams2_1;
    }

    //3画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam3(int screenWidth2, int screenHeight2) {
        List<RelativeLayout.LayoutParams> layoutParams3 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp0.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams3.add(lp0);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams3.add(lp1);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams3.add(lp2);

        return layoutParams3;
    }

    //4_0画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam4_0(int screenWidth3, int screenHeight3) {
        List<RelativeLayout.LayoutParams> layoutParams4_0 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth3 * 2, screenHeight3 * 2);
        lp0.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams4_0.add(lp0);

        for (int i = 0; i < 3; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.topMargin = i * screenHeight3;
            layoutParams4_0.add(lp);
        }

        return layoutParams4_0;
    }

    //4_1画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam4_1(int screenWidth2, int screenHeight2) {
        List<RelativeLayout.LayoutParams> layoutParams4_1 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        layoutParams4_1.add(lp0);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams4_1.add(lp1);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams4_1.add(lp2);

        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(screenWidth2, screenHeight2);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams4_1.add(lp3);

        return layoutParams4_1;
    }

    //5画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam5(int screenWidth4, int screenHeight4) {
        List<RelativeLayout.LayoutParams> layoutParams5 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth4 * 3, screenHeight4 * 3);
        lp0.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams5.add(lp0);

        for (int i = 0; i < 4; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.leftMargin = i * screenWidth4;
            layoutParams5.add(lp);
        }

        return layoutParams5;
    }

    //6_0画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam6_0(int screenWidth3, int screenHeight3) {
        List<RelativeLayout.LayoutParams> layoutParams6_0 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
            lp.leftMargin = i * screenWidth3;
            lp.topMargin = screenHeight3 / 3;
            layoutParams6_0.add(lp);
        }

        for (int i = 0; i < 3; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.leftMargin = i * screenWidth3;
            lp.bottomMargin = screenHeight3 / 3;
            layoutParams6_0.add(lp);
        }

        return layoutParams6_0;
    }

    //6_1画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam6_1(int screenWidth3, int screenHeight3) {
        List<RelativeLayout.LayoutParams> layoutParams6_1 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth3 * 2, screenHeight3 * 2);
        layoutParams6_1.add(lp0);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams6_1.add(lp1);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.topMargin = screenHeight3;
        layoutParams6_1.add(lp2);

        for (int i = 0; i < 3; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.rightMargin = i * screenWidth3;
            layoutParams6_1.add(lp);
        }

        return layoutParams6_1;
    }

    //8画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam8(int screenWidth4, int screenHeight4) {
        List<RelativeLayout.LayoutParams> layoutParams8 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth4 * 3, screenHeight4 * 3);
        layoutParams8.add(lp0);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams8.add(lp1);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.topMargin = screenHeight4;
        layoutParams8.add(lp2);

        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp3.topMargin = 2 * screenHeight4;
        layoutParams8.add(lp3);

        for (int i = 0; i < 4; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.rightMargin = i * screenHeight4;
            layoutParams8.add(lp);
        }

        return layoutParams8;
    }

    //9画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam9(int screenWidth3, int screenHeight3) {
        List<RelativeLayout.LayoutParams> layoutParams9 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
                lp.topMargin = i * screenHeight3;
                lp.leftMargin = j * screenWidth3;
                layoutParams9.add(lp);
            }
        }

        return layoutParams9;
    }

    //12画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam12(int screenWidth3, int screenHeight3) {
        List<RelativeLayout.LayoutParams> layoutParams12 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth3, screenHeight3);
                if (i == 1) {
                    lp.addRule(RelativeLayout.CENTER_VERTICAL);
                } else if (i == 2) {
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                }
                lp.leftMargin = j * screenWidth3;
                layoutParams12.add(lp);
            }
        }

        return layoutParams12;
    }

    //13画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam13(int screenWidth4, int screenHeight4) {
        List<RelativeLayout.LayoutParams> layoutParams13 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth4 * 2, screenHeight4 * 2);
        lp0.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams13.add(lp0);

        for (int i = 0; i < 4; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
            lp.leftMargin = i * screenWidth4;
            layoutParams13.add(lp);
        }

        RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp4.topMargin = screenWidth4;
        layoutParams13.add(lp4);

        RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp5.topMargin = screenWidth4;
        lp5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams13.add(lp5);

        RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp6.topMargin = 2 * screenWidth4;
        layoutParams13.add(lp6);

        RelativeLayout.LayoutParams lp7 = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
        lp7.topMargin = 2 * screenWidth4;
        lp7.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams13.add(lp7);

        for (int i = 0; i < 4; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.leftMargin = i * screenWidth4;
            layoutParams13.add(lp);
        }

        return layoutParams13;
    }

    //15画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam15(int screenWidth5, int screenHeight5) {
        List<RelativeLayout.LayoutParams> layoutParams15 = new ArrayList<>();

        RelativeLayout.LayoutParams lp0 = new RelativeLayout.LayoutParams(screenWidth5 * 3, screenHeight5 * 3);
        lp0.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams15.add(lp0);

        for (int i = 0; i < 4; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth5, screenHeight5);
            lp.leftMargin = i * screenWidth5;
            layoutParams15.add(lp);
        }

        RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(screenWidth5, screenHeight5);
        lp4.topMargin = screenWidth5;
        layoutParams15.add(lp4);

        RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(screenWidth5, screenHeight5);
        lp5.topMargin = screenWidth5;
        lp5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams15.add(lp5);

        RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(screenWidth5, screenHeight5);
        lp6.topMargin = 2 * screenWidth5;
        layoutParams15.add(lp6);

        RelativeLayout.LayoutParams lp7 = new RelativeLayout.LayoutParams(screenWidth5, screenHeight5);
        lp7.topMargin = 2 * screenWidth5;
        lp7.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams15.add(lp7);

        for (int i = 0; i < 4; i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth5, screenHeight5);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.leftMargin = i * screenHeight5;
            layoutParams15.add(lp);
        }

        return layoutParams15;
    }

    //16画面
    private static List<RelativeLayout.LayoutParams> initLayoutParam16(int screenWidth4, int screenHeight4) {
        List<RelativeLayout.LayoutParams> layoutParams16 = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(screenWidth4, screenHeight4);
                lp.topMargin = i * screenHeight4;
                lp.leftMargin = j * screenWidth4;
                layoutParams16.add(lp);
            }
        }

        return layoutParams16;
    }

}
