package com.getling.gwframe.scan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.StringUtils
import com.getling.gwframe.R
import com.getling.gwframe.bus.BusUtil
import com.getling.gwframe.bus.event.MsgEvent
import com.getling.gwframe.bus.rxbus.RxBus
import com.getling.gwframe.constant.MsgConstant
import com.getling.gwframe.utils.NotifyUtil
import com.getling.gwframe.widget.CleanableEditText
import com.honeywell.aidc.BarcodeFailureEvent
import com.honeywell.aidc.BarcodeReadEvent
import com.honeywell.aidc.BarcodeReader

/**
 * @Author: getling
 * @CreateDate: 2019/11/11 9:46
 * @Description: 扫描框
 */
class ScanView : FrameLayout {

    private var tvScan: TextView? = null
    private var cetScan: CleanableEditText? = null
    private var hint: String? = ""

    private var barcodeReader: BarcodeReader? = null
    private var onScanListener: OnScanListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.view_scan, this)
        tvScan = findViewById(R.id.tv_scan)
        cetScan = findViewById(R.id.cet_scan)
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanView)
            val btnBg = typedArray.getResourceId(
                R.styleable.ScanView_scan_button_bg,
                R.drawable.ripple_btn_scan_radius
            )
            val contentBg = typedArray.getResourceId(
                R.styleable.ScanView_scan_content_bg,
                R.drawable.shape_edit_bg_white
            )
            hint = typedArray.getString(R.styleable.ScanView_scanHint)
            if (hint == null) {
                hint = ""
            }

            cetScan?.setMyHint(hint!!)
            cetScan?.setBackgroundResource(contentBg)
            tvScan?.setBackgroundResource(btnBg)
            typedArray.recycle()
        }
        when {
            ScanConfig.SCAN_MODE == ScanConfig.SCAN_MODE_ALMEX -> almexScan()
            ScanConfig.SCAN_MODE != ScanConfig.SCAN_MODE_PHONE -> setBarcodeReader(ScanUtil.barcodeReader)
            else -> BusUtils.register(this)
        }
    }

    fun setOnScanListener(onScanListener: OnScanListener?) {
        if (onScanListener != null) {
            this.onScanListener = onScanListener
            //点击扫描按钮
            ClickUtils.applySingleDebouncing(tvScan) {
                KeyboardUtils.hideSoftInput(this@ScanView)
                when (ScanConfig.SCAN_MODE) {
                    ScanConfig.SCAN_MODE_HONEYWELL -> pdaScan()
                    ScanConfig.SCAN_MODE_ALMEX -> pdaScan()
                    ScanConfig.SCAN_MODE_PDA -> pdaScan()
                    ScanConfig.SCAN_MODE_PHONE -> zxingScan()
                }
            }
        }
    }

    fun setInputText(text: String?) {
        cetScan?.setInputText(text)
    }

    fun getInputText(): String {
        return cetScan!!.getInputText()
    }

    interface OnScanListener {
        fun onScan(data: String)
    }

    /*---------------PDA扫描，如果手动点击按钮，调用该方法---------------*/
    private fun pdaScan() {
        val data = getInputText()
        if (StringUtils.isEmpty(data)) {
            NotifyUtil.showWarning("请输入$hint")
            return
        }
        onScanListener?.onScan(data)
    }

    /*---------------调用手机摄像头扫描---------------*/

    private fun zxingScan() {
        val data = getInputText()
        if (StringUtils.isEmpty(data)) {
            ScanUtil.startScanQRCode(context)
            BusUtil.subscribe(this, object : RxBus.BusCallback<MsgEvent>() {
                override fun onEvent(t: MsgEvent) {
                    setInputText(t.msg)
                    onScanListener?.onScan(t.msg)
                    BusUtil.unregister(this@ScanView)
                }
            })
        } else {
            onScanListener?.onScan(data)
        }
    }

    /*-------------Honeywell PDA扫描----------------*/

    private fun setBarcodeReader(barcodeReader: BarcodeReader?) {
        if (barcodeReader == null) {
            return
        }
        this.barcodeReader = barcodeReader
        this.barcodeReader?.addBarcodeListener(barcodeListener)
    }

    private var barcodeListener = object : BarcodeReader.BarcodeListener {

        override fun onFailureEvent(event: BarcodeFailureEvent) {
            NotifyUtil.showWarning(MsgConstant.ERROR_SCAN)
        }

        override fun onBarcodeEvent(event: BarcodeReadEvent) {
            post {
                val data = event.barcodeData
                if (StringUtils.isEmpty(data)) {
                    NotifyUtil.showWarning(MsgConstant.ERROR_SCAN)
                    return@post
                }
                setInputText(data)
                onScanListener?.onScan(data!!)
            }
        }
    }

    /*-------------almex PDA广播接收----------------*/
    private val scanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(ScanConfig.SCAN_ACTION_ALMEX)) {
                val data = intent?.getStringExtra(ScanConfig.SCAN_NAME_ALMEX)
                if (StringUtils.isEmpty(data)) {
                    NotifyUtil.showWarning(MsgConstant.ERROR_SCAN)
                    return
                }
                setInputText(data)
                onScanListener?.onScan(getInputText())
            } else {
                NotifyUtil.showWarning(MsgConstant.ERROR_SCAN)
            }
        }
    }

    private fun almexScan() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ScanConfig.SCAN_ACTION_ALMEX)
        intentFilter.priority = Integer.MAX_VALUE
        context.registerReceiver(scanReceiver, intentFilter)
    }

    /**
     * 销毁相关资源
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ScanUtil.removeListener(barcodeListener)
        if (ScanConfig.SCAN_MODE == ScanConfig.SCAN_MODE_PHONE) {
            BusUtils.unregister(this)
        }
        if (ScanConfig.SCAN_MODE == ScanConfig.SCAN_MODE_ALMEX) {
            context.unregisterReceiver(scanReceiver)
        }
    }
}
