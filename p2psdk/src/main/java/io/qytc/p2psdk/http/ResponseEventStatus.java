package io.qytc.p2psdk.http;

public class ResponseEventStatus {
    public static final int OK = 1;
    public static final int ERROR = 0;
    public static final int UNREGISTERED = -1;

    public static final int EVENT_BASE = 100000000;

    public static final int LOGIN_ID = EVENT_BASE + 1;

    public static final int CREAT_P2PCALL = EVENT_BASE + 2;

    public static final int CALL_ACCEPT = EVENT_BASE + 3;
}
