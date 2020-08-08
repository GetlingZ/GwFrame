package com.getling.gwframe.scan;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BusUtils;
import com.getling.gwframe.R;
import com.getling.gwframe.bus.BusUtil;
import com.getling.gwframe.bus.event.MsgEvent;
import com.getling.gwframe.utils.StatusBarUtil;
import com.king.zxing.CaptureHelper;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;

import java.util.Objects;

/**
 * @CreateDate: 2020/7/18 16:04
 * @Author: getling
 * @Description: zxing 扫描页面
 */
public class ScanQRCodeActivity extends AppCompatActivity implements OnCaptureCallback {

    private CaptureHelper mCaptureHelper;

    private SurfaceView surfaceView;

    private ViewfinderView viewfinderView;

    private SoundPool soundPool;
    private boolean loadComplete;
    private int soundID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        soundPool = new SoundPool.Builder().build();
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> loadComplete = status == 0);
        soundID = soundPool.load(this, com.king.zxing.R.raw.zxl_beep, 1);
        initView();
        BusUtils.register(this);
    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar);

        surfaceView = findViewById(R.id.surfaceView);
        viewfinderView = findViewById(R.id.viewfinderView);
        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView, findViewById(R.id.iv_flash));
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.onCreate();
        mCaptureHelper.vibrate(false)
                .playBeep(false)
                //全屏扫码
                .fullScreenScan(true)
                //支持扫垂直条码，建议有此需求时才使用。
                .supportVerticalCode(true)
                .supportAutoZoom(false)
                .supportZoom(false)
                .continuousScan(false);

    }

    @Override
    public boolean onResultCallback(String result) {
        BusUtil.postEvent(new MsgEvent(result));
        if (loadComplete) {
            soundPool.play(soundID, 1, 1, 1, 0, 1);
        }
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureHelper.onDestroy();
        BusUtils.unregister(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCaptureHelper.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
