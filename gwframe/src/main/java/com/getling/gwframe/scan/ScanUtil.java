package com.getling.gwframe.scan;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.getling.gwframe.app.BaseApplication;
import com.getling.gwframe.constant.SPConstant;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.king.zxing.Intents;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: getling
 * @CreateDate: 2019/12/9 16:21
 * @Description:
 */
public class ScanUtil {
    public static BarcodeReader barcodeReader;
    private static AidcManager manager;

    public static final int REQUEST_CODE = 1433;
    public static final String SCAN_RESULT = Intents.Scan.RESULT;

    public static void startScanQRCode(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(context, ScanQRCodeActivity.class));
//        AndPermission.with(context)
//                .runtime()
//                .permission(Permission.CAMERA)
//                .onGranted(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//                    }
//                })
//                .onDenied(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//                        ToastUtils.showLong("没有权限无法扫描呦");
//                    }
//                }).start();
    }

    /**
     * 根据设备厂商，判断使用哪种扫描方式
     */
    public static void setScanMode() {
        int mode = SPUtils.getInstance().getInt(SPConstant.SP_SCAN_MODE, -1);
        if (mode == -1) {
            String manufacturer = DeviceUtils.getManufacturer().toLowerCase();
            if (manufacturer.equals(ScanConfig.Honeywell)) {
                ScanConfig.INSTANCE.setSCAN_MODE(ScanConfig.SCAN_MODE_HONEYWELL);
            } else if (manufacturer.equals(ScanConfig.ALMEX)) {
                ScanConfig.INSTANCE.setSCAN_MODE(ScanConfig.SCAN_MODE_ALMEX);
            } else if (ScanConfig.INSTANCE.getRom().contains(manufacturer)) {
                ScanConfig.INSTANCE.setSCAN_MODE(ScanConfig.SCAN_MODE_PHONE);
            } else {
                ScanConfig.INSTANCE.setSCAN_MODE(ScanConfig.SCAN_MODE_PDA);
            }
        } else {
            ScanConfig.INSTANCE.setSCAN_MODE(mode);
        }
    }

    public static void setScanMode(int mode) {
        ScanConfig.INSTANCE.setSCAN_MODE(mode);
    }

    public static void init() {
        if (ScanConfig.INSTANCE.getSCAN_MODE() == ScanConfig.SCAN_MODE_PHONE) {
            return;
        }
        if (barcodeReader != null) {
            return;
        }
        // create the AidcManager providing a Context and a
        // CreatedCallback implementation.
        AidcManager.create(BaseApplication.appContext, new AidcManager.CreatedCallback() {

            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
            }
        });

        if (barcodeReader != null) {

            // register bar code event listener
//            barcodeReader.addBarcodeListener(this);

            // set the trigger mode to client control
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_AUTO_CONTROL);
            } catch (UnsupportedPropertyException e) {
                e.printStackTrace();
                LogUtils.e("scan---------Failed to apply properties");
            }
            // register trigger state change listener
//            barcodeReader.addTriggerListener(this);

            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, false);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, false);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 10);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Enable bad read response
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
            // Apply the settings
            barcodeReader.setProperties(properties);
        }

    }

    public static void onResume() {
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
                LogUtils.e("Scanner unavailable");
            }
        }
    }

    public static void onPause() {
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.release();
        }
    }

    public static void removeListener(BarcodeReader.BarcodeListener listener) {
        if (barcodeReader != null) {
            // unregister barcode event listener
            barcodeReader.removeBarcodeListener(listener);

            // unregister trigger state change listener
//            barcodeReader.removeTriggerListener(this);
        }
    }

    public static void onDestroy() {
        if (barcodeReader != null) {
            // close BarcodeReader to clean up resources.
            barcodeReader.close();
            barcodeReader = null;
        }

        if (manager != null) {
            // close AidcManager to disconnect from the scanner service.
            // once closed, the object can no longer be used.
            manager.close();
        }
    }

}
