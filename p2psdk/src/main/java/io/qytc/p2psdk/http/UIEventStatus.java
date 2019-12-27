package io.qytc.p2psdk.http;

public class UIEventStatus {
    public static final int OK = 1;

    //socket.io相关数据传递
    private static final int SOCKET_IO_BASE_ID = 2000;
    public static final int END_CONF = SOCKET_IO_BASE_ID + 1;

    public static final int CONF_AUTO_CLOSE_NOTICE = SOCKET_IO_BASE_ID + 16;

    public static final int J_PUSH_BASE_ID = 2100;

    public static final int JPUSH_ACCEPT_CALL = J_PUSH_BASE_ID + 2;

}
