package io.qytc.p2psdk.constant;


/**
 * 服务器常量
 */
public interface UrlConstant {


    /**
     * 服务器主路径IP
     */
    String MAIN_IP = "meeting.whqunyu.com";

    /**
     * 服务器端口
     */
    String PORT = "8882";

    /**
     * API地址
     */
    String BASE_URL = "http://" + MAIN_IP + ":" + PORT + "/api/v1/confcontrol/";

    /**
     * socket.IO
     */
    String SOCKET_PORT = "8880";

    /**
     * socket地址
     */
    String KEEP_ALIVE_URL = "http://" + MAIN_IP + ":" + SOCKET_PORT;
}
