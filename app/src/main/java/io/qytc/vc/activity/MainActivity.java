package io.qytc.vc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.ResponseEvent;
import io.qytc.p2psdk.http.DoHttpManager;
import io.qytc.p2psdk.http.ResponseEventStatus;
import io.qytc.p2psdk.http.response.LoginResponse;
import io.qytc.p2psdk.service.SocketHelper;
import io.qytc.p2psdk.utils.SpUtil;
import io.qytc.p2psdk.utils.ToastUtils;
import io.qytc.vc.R;

public class MainActivity extends Activity {

    @BindView(R.id.login_ed)
    EditText loginEd;
    @BindView(R.id.pwd_ed)
    EditText pwdEd;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.tv_login_tip)
    TextView tvLoginTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBusUtil.register(this);
    }

    @OnClick({R.id.cancel_btn, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.login_btn:
                String loginStr = loginEd.getText().toString().trim();
                if (TextUtils.isEmpty(loginStr)) {
                    ToastUtils.toast(MainActivity.this, "请输入终端或手机号", false);
                    return;
                }
                String pwdStr = pwdEd.getText().toString().trim();
                if (TextUtils.isEmpty(pwdStr)) {
                    ToastUtils.toast(MainActivity.this, "请输入密码", false);
                    return;
                }
                login(loginStr, pwdStr);
                break;
        }
    }

    private void login(String loginStr, String pwdStr) {

        DoHttpManager.getInstance().auth(this, loginStr, pwdStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    public void onEventMainThread(ResponseEvent event) {
        if (event == null) return;

        if (event.getId() != ResponseEventStatus.LOGIN_ID) return;

        switch (event.getStatus()) {
            case ResponseEventStatus.OK:
                LoginResponse loginResponse = event.getData();
                LoginResponse.DataBean data = loginResponse.getData();

                String userSig = data.getUserSig();
                String accessToken = data.getAccessToken();

                LoginResponse.DataBean.TerminalBean terminalBean = data.getTerminal();
                int deptId = terminalBean.getDeptId();
                String pmi = terminalBean.getPmi();
                int tenantId = terminalBean.getTenantId();
                String name = terminalBean.getName();
                int id = terminalBean.getId();

                SpUtil.saveString(this, SpConstant.USERSIG, userSig);
                SpUtil.saveString(this, SpConstant.ACCESS_TOKEN, accessToken);
                SpUtil.saveString(this, SpConstant.PMI, pmi);
                SpUtil.saveString(this, SpConstant.NAME, name);
                SpUtil.saveString(this, SpConstant.DEPTID, deptId + "");
                SpUtil.saveString(this, SpConstant.TENANTID, tenantId + "");
                SpUtil.saveString(this, SpConstant.ID, id + "");

                SocketHelper.getInstance().stop();
                SocketHelper.getInstance().start(this);

                ToastUtils.toast(this, R.string.login_success, false);

                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
            case ResponseEventStatus.UNREGISTERED:
                ToastUtils.toast(this, R.string.unregister, false);
                tvLoginTip.setText(R.string.unregister);
                break;
            case ResponseEventStatus.ERROR:
                ToastUtils.toast(this, event.getMessage(), false);
                tvLoginTip.setText(event.getMessage());
                break;
        }
    }
}
