package com.gurug.education.view.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.zxing.Result;
import com.gurug.education.R;
import com.gurug.education.utill.PermissionUtil;
import com.gurug.education.utill.scanner.ZXingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QrScanActivity extends BaseActivity implements ZXingView.ResultHandler, PermissionUtil.PermissionAskListener {

    @BindView(R.id.zx_qr_code)
    ZXingView mZxQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_close)
    void onClickClose() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtil.checkPermission(QrScanActivity.this, Manifest.permission.CAMERA, this);
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(() -> mZxQrCode.resumeCameraPreview(QrScanActivity.this), 2000);
    }

    @Override
    public void onNeedPermission() {
        ActivityCompat.requestPermissions(QrScanActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
    }

    @Override
    public void onPermissionPreviouslyDenied() {

    }

    @Override
    public void onPermissionDisabled() {

    }

    @Override
    public void onPermissionGranted() {
        mZxQrCode.setResultHandler(this);
        mZxQrCode.startCamera();
    }
}
