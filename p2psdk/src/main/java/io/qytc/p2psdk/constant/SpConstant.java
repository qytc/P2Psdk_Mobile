package io.qytc.p2psdk.constant;


/**
 * SharedPreference存储的常量
 */
public interface SpConstant {

    /**
     * 快速缓存文件名
     */
    String SP_FILENAME = "sp_filename";

    /**
     * 云台提示记录
     */
    String CAMERA_CONTROL_TIP = "camera_control_tip";
    /**
     * 服务器端口
     */
    String SERVER_PORT = "server_port";
    /**
     * 服务器路径
     */
    String SERVER_RUL = "server_url";
    /**
     * 服务器api完整路径
     */
    String BASE_URL = "base_url";
    /**
     * socket地址
     */
    String KEEP_ALIVE_URL = "keep_alive_url";

    /**
     * 极光推送ID
     */
    String JPUSH_DEVICE_ID = "jpush_device_id";

    /**
     * APP的ID
     */
    String APP_ID = "1400210901";

    /**
     * 4-专业终端
     */
    int type = 2;

    /**
     * accessToken
     */
    String ACCESS_TOKEN = "accessToken";

    /**
     * 终端序列号
     */
    String SERIAL = "serial";

    /**
     * 硬件版本号
     */
    String HARDWARE = "hardware";

    /**
     * sig
     */
    String USERSIG = "userSig";

    /**
     * 终端用户的租户房间号
     */
    String PMI = "pmi";

    /**
     * 进入房间后的房间号
     */
    String JOIN_PMI = "join_pmi";

    /**
     * 终端名称
     */
    String NAME = "name";

    /**
     * 部门ID
     */
    String DEPTID = "deptId";

    /**
     * 租户ID
     */
    String TENANTID = "tenantId";

    /**
     * 终端ID
     */
    String ID = "id";

    /**
     * 广播画面
     */
    String BROADCAST = "Broadcast";

    /**
     * 摄像头1.2.3
     */
    String CAMERA1 = "Camera1";
    String CAMERA2 = "Camera2";
    String CAMERA3 = "Camera3";

    /**
     * cpu状态
     */
    String CPUSTATS = "CpuStats";

    /**
     * 是否在房间
     */
    String INROOM = "InRoom";

    /**
     * 制造商
     */
    String MAKER = "Maker";

    /**
     * 内存状态
     */
    String MEMSTATS = "MemStats";

    /**
     * 麦克风开关
     */
    String MIC = "Mic";

    /**
     *
     */
    String SOCKETID = "SocketId";

    /**
     * 发言状态
     */
    String SPEAK = "Speak";

    /**
     * 发送数据
     */
    String SENDBYTES = "SendBytes";

    /**
     * 接收数据
     */
    String RECEIVEBYTES = "ReceiveBytes";

    /**
     * 用户头像
     */
    String IMAGE = "image";

    /**
     * 绑定的终端ID
     */
    String BINDTERM = "bindTerm";

    /**
     * 重要会议标识
     */
    String MAIN_CONFERENCE = "maincConference";
    /**
     * 音频输入标识
     */
    String AUDIO_INPUT = "audioInput";
    /**
     * 音频输出标识
     */
    String AUDIO_OUTPUT = "audioOutput";
    String AUDIO_OUT = "Audio-Out";
    String HDMI = "HDMI";
    String USB = "USB";


}
