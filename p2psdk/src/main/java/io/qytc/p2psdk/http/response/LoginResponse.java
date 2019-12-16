package io.qytc.p2psdk.http.response;

public class LoginResponse extends BaseResponse {
    /**
     * data : {"terminal":{"image":"","deptId":73,"type":4,"pmi":"918415947","number":"20191012090","password":"adbd7d864275039943cb721e30874eac","bindTerm":111,"name":"研发部测试一","tenantId":38,"id":169,"salt2":"680bf3db3f2eec6edbcb536bc334907e","createDate":"2019-11-25 19:58:36","status":1},"userSig":"eJw1j8kOgjAQQP*FszGFgtsNEbeIS1wQL02hRScCVqwIGv9dRMyc5r3DvHkpm9m6SYUARqgkOGVKT0FKo8I8F5ByQkPJ0xKrhmFoCP0tMJ5ICOHnWt0a3*BY7o7tWZOVFbWPs23ftzFPFnZ28DNzvM8H0W5kYc3JnMcTux33ELKBWE1O5nITFVMc68GOQr70zPn4ygo-yefa*QLrlhm6VhAPpefb7v8YO5Mq-pugI6SpqIvUWkqIeZXd1stRsVFzGgSXeyKJLASvvn1-AGnGT8o_","accessToken":"169.47c354dc4d3249e9a4ee88ae79e024b5"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * terminal : {"image":"","deptId":73,"type":4,"pmi":"918415947","number":"20191012090","password":"adbd7d864275039943cb721e30874eac","bindTerm":111,"name":"研发部测试一","tenantId":38,"id":169,"salt2":"680bf3db3f2eec6edbcb536bc334907e","createDate":"2019-11-25 19:58:36","status":1}
         * userSig : eJw1j8kOgjAQQP*FszGFgtsNEbeIS1wQL02hRScCVqwIGv9dRMyc5r3DvHkpm9m6SYUARqgkOGVKT0FKo8I8F5ByQkPJ0xKrhmFoCP0tMJ5ICOHnWt0a3*BY7o7tWZOVFbWPs23ftzFPFnZ28DNzvM8H0W5kYc3JnMcTux33ELKBWE1O5nITFVMc68GOQr70zPn4ygo-yefa*QLrlhm6VhAPpefb7v8YO5Mq-pugI6SpqIvUWkqIeZXd1stRsVFzGgSXeyKJLASvvn1-AGnGT8o_
         * accessToken : 169.47c354dc4d3249e9a4ee88ae79e024b5
         */

        private TerminalBean terminal;
        private String userSig;
        private String accessToken;

        public TerminalBean getTerminal() {
            return terminal;
        }

        public void setTerminal(TerminalBean terminal) {
            this.terminal = terminal;
        }

        public String getUserSig() {
            return userSig;
        }

        public void setUserSig(String userSig) {
            this.userSig = userSig;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public static class TerminalBean {
            /**
             * image :
             * deptId : 73
             * type : 4
             * pmi : 918415947
             * number : 20191012090
             * password : adbd7d864275039943cb721e30874eac
             * bindTerm : 111
             * name : 研发部测试一
             * tenantId : 38
             * id : 169
             * salt2 : 680bf3db3f2eec6edbcb536bc334907e
             * createDate : 2019-11-25 19:58:36
             * status : 1
             */

            private String image;
            private int deptId;
            private int type;
            private String pmi;
            private String number;
            private String password;
            private int bindTerm;
            private String name;
            private int tenantId;
            private int id;
            private String salt2;
            private String createDate;
            private int status;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getDeptId() {
                return deptId;
            }

            public void setDeptId(int deptId) {
                this.deptId = deptId;
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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getBindTerm() {
                return bindTerm;
            }

            public void setBindTerm(int bindTerm) {
                this.bindTerm = bindTerm;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSalt2() {
                return salt2;
            }

            public void setSalt2(String salt2) {
                this.salt2 = salt2;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
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
