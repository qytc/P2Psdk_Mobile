package io.qytc.p2psdk.bean;

/**
 * 邀请加入房间
 * 推送返回的数据
 */
public class InviteJoinRoomPushBean {
    private String chairmanName;
    private String cmd;
    private String conference;
    private String time;

    public String getChairmanName() {
        return chairmanName;
    }

    public void setChairmanName(String chairmanName) {
        this.chairmanName = chairmanName;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
