package io.qytc.p2psdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import io.qytc.p2psdk.SDKCore;


public class NetUtil {
    public static final int NET_ETHERNET = 1;
    public static final int NET_WIFI = 2;
    public static final int NET_MOBILE = 3;
    public static final int NET_NOCONNECT = 0;


    public static int isNetworkAvailable(Context context) {

        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ethNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if (ethNetInfo != null && ethNetInfo.isConnected()) {
            return NET_ETHERNET;
        } else if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
            return NET_WIFI;
        } else if (mobileNetInfo != null && mobileNetInfo.isConnected()) {
            return NET_MOBILE;
        } else {
            return NET_NOCONNECT;
        }
    }


    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        return isNetworkAvailable(context) != NET_NOCONNECT;
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
        // return null;
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * GPRS连接下的ip
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            return " 获取IP出错!\n" + ex.getMessage();
        }
        return null;
    }
}
