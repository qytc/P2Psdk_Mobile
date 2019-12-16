package io.qytc.p2psdk.bean;

public class TermStatsObject {
    //终端ID
    private Long id;
    //1-STB  2-手机  3-微信  4-专业终端
    private Integer type;
    //终端号码（手机号、盒子卡号、微信openId）
    private String number;
    //终端名称
    private String name;
    //头像
    private String image;
    //部门ID
    private Long deptId;
    //租户ID
    private Long tenantId;
    //绑定的终端ID
    private Long bindTerm;
    //极光推送deviceId
    private String deviceId;
    //socket.io通讯key
    private String socketId;
    //个人pmi
    private String pmi;
    private int inRoom;
    private int speak;
    //所在会议室号码
    private String confPmi;
    private int camera1;
    private int camera2;
    private int camera3;
    private int mic;
    private int broadcast;
    private String maker;
    private String cpuStats;
    private String memStats;
    private double sendByte;
    private double receiveByte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getBindTerm() {
        return bindTerm;
    }

    public void setBindTerm(Long bindTerm) {
        this.bindTerm = bindTerm;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getPmi() {
        return pmi;
    }

    public void setPmi(String pmi) {
        this.pmi = pmi;
    }

    public int getInRoom() {
        return inRoom;
    }

    public void setInRoom(int inRoom) {
        this.inRoom = inRoom;
    }

    public int getSpeak() {
        return speak;
    }

    public void setSpeak(int speak) {
        this.speak = speak;
    }

    public String getConfPmi() {
        return confPmi;
    }

    public void setConfPmi(String confPmi) {
        this.confPmi = confPmi;
    }

    public int getCamera1() {
        return camera1;
    }

    public void setCamera1(int camera1) {
        this.camera1 = camera1;
    }

    public int getCamera2() {
        return camera2;
    }

    public void setCamera2(int camera2) {
        this.camera2 = camera2;
    }

    public int getCamera3() {
        return camera3;
    }

    public void setCamera3(int camera3) {
        this.camera3 = camera3;
    }

    public int getMic() {
        return mic;
    }

    public void setMic(int mic) {
        this.mic = mic;
    }

    public int getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(int broadcast) {
        this.broadcast = broadcast;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getCpuStats() {
        return cpuStats;
    }

    public void setCpuStats(String cpuStats) {
        this.cpuStats = cpuStats;
    }

    public String getMemStats() {
        return memStats;
    }

    public void setMemStats(String memStats) {
        this.memStats = memStats;
    }

    public double getSendByte() {
        return sendByte;
    }

    public void setSendByte(double sendByte) {
        this.sendByte = sendByte;
    }

    public double getReceiveByte() {
        return receiveByte;
    }

    public void setReceiveByte(double receiveByte) {
        this.receiveByte = receiveByte;
    }
}
