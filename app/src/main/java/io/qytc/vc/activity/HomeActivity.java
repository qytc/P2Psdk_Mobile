package io.qytc.vc.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import io.qytc.p2psdk.activity.ActivityCall;
import io.qytc.p2psdk.constant.SpConstant;
import io.qytc.p2psdk.eventcore.EventBusUtil;
import io.qytc.p2psdk.eventcore.ResponseEvent;

import java.util.ArrayList;
import java.util.List;

import io.qytc.p2psdk.http.DoHttpManager;
import io.qytc.p2psdk.http.ResponseEventStatus;
import io.qytc.p2psdk.http.response.CreatConfResponse;
import io.qytc.p2psdk.utils.SpUtil;
import io.qytc.p2psdk.utils.ToastUtils;
import io.qytc.vc.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQ_PERMISSION_CODE = 1000;
    private EditText mEtTargetNumber;
    private TextView mTvPersonPmi;
    private String mTargetNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        EventBusUtil.register(this);

        checkPermission();
    }

    private void initView() {
        mEtTargetNumber = findViewById(R.id.et_target_number);

        mTvPersonPmi = findViewById(R.id.tv_person_pmi);
        String personPmi = SpUtil.getString(this, SpConstant.PMI);
        mTvPersonPmi.setText(personPmi);

        Button btCall = findViewById(R.id.btn_call);
        btCall.setOnClickListener(this);
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_call) {
            callTarget();
        }
    }

    private void callTarget() {
        mTargetNumber = mEtTargetNumber.getText().toString().trim();

        if (TextUtils.isEmpty(mTargetNumber)) {
            ToastUtils.toast(this, R.string.please_input_number,false);
            return;
        }

        DoHttpManager.getInstance().p2pCall(this, mTargetNumber);
    }

    public void onEventMainThread(ResponseEvent event) {
        if (event == null || event.getStatus() != ResponseEventStatus.OK) {
            return;
        }

        switch (event.getId()) {
            case ResponseEventStatus.CREAT_P2PCALL:
                CreatConfResponse bean = event.getData();
                CreatConfResponse.DataBean data = bean.getData();
                if (data == null) return;
                String pmi = data.getConference().getPmi();
                joinConf(pmi);
                break;
        }
    }

    private void joinConf(String roomPmi) {
        SpUtil.saveString(this, SpConstant.INROOM, "1");
        SpUtil.saveString(this, SpConstant.JOIN_PMI, roomPmi);

        Intent intent = new Intent(this, ActivityCall.class);
        intent.putExtra(ActivityCall.ROOM_PMI, roomPmi);
        intent.putExtra(ActivityCall.CALL_TYPE, ActivityCall.CALL_OUT);
        intent.putExtra(ActivityCall.TARGET_PMI, mTargetNumber);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }
}
