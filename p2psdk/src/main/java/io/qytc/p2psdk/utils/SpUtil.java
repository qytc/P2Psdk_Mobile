package io.qytc.p2psdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import io.qytc.p2psdk.constant.SpConstant;

/**
 * sharedPreference工具类
 */
public class SpUtil {
    private static SharedPreferences mSp = null;
    private static SharedPreferences.Editor mEdit = null;

    public static void saveString(Context context, String key, String text) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SpConstant.SP_FILENAME, Context.MODE_PRIVATE);
        }
        if (mEdit == null) {
            mEdit = mSp.edit();
        }
        mEdit.putString(key, text);
        mEdit.commit();
    }

    public static String getString(Context context, String key) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SpConstant.SP_FILENAME, Context.MODE_PRIVATE);
        }
        return mSp.getString(key, "");
    }
}