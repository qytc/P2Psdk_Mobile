package io.qytc.p2psdk.http.response;

public class CreatConfResponse extends BaseResponse {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ConferenceBean conference;

        public ConferenceBean getConference() {
            return conference;
        }

        public void setConference(ConferenceBean conference) {
            this.conference = conference;
        }

        public static class ConferenceBean {
            private int talkTime;
            private int rtmpEnable;
            private String joinPwd;
            private int type;
            private String pmi;
            private int layouMode;
            private int duration;
            private int dvbEnable;
            private int id;
            private String beginTime;
            private String chairPwd;
            private int mcgEnable;
            private int csEnable;
            private int chairman;
            private int videoEnable;
            private String name;
            private int tenantId;
            private int status;

            public int getTalkTime() {
                return talkTime;
            }

            public void setTalkTime(int talkTime) {
                this.talkTime = talkTime;
            }

            public int getRtmpEnable() {
                return rtmpEnable;
            }

            public void setRtmpEnable(int rtmpEnable) {
                this.rtmpEnable = rtmpEnable;
            }

            public String getJoinPwd() {
                return joinPwd;
            }

            public void setJoinPwd(String joinPwd) {
                this.joinPwd = joinPwd;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getPmi() {
                return pmi;
            }

            public void setPmi(String pmi) {
                this.pmi = pmi;
            }

            public int getLayouMode() {
                return layouMode;
            }

            public void setLayouMode(int layouMode) {
                this.layouMode = layouMode;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getDvbEnable() {
                return dvbEnable;
            }

            public void setDvbEnable(int dvbEnable) {
                this.dvbEnable = dvbEnable;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getChairPwd() {
                return chairPwd;
            }

            public void setChairPwd(String chairPwd) {
                this.chairPwd = chairPwd;
            }

            public int getMcgEnable() {
                return mcgEnable;
            }

            public void setMcgEnable(int mcgEnable) {
                this.mcgEnable = mcgEnable;
            }

            public int getCsEnable() {
                return csEnable;
            }

            public void setCsEnable(int csEnable) {
                this.csEnable = csEnable;
            }

            public int getChairman() {
                return chairman;
            }

            public void setChairman(int chairman) {
                this.chairman = chairman;
            }

            public int getVideoEnable() {
                return videoEnable;
            }

            public void setVideoEnable(int videoEnable) {
                this.videoEnable = videoEnable;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getTenantId() {
                return tenantId;
            }

            public void setTenantId(int tenantId) {
                this.tenantId = tenantId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
