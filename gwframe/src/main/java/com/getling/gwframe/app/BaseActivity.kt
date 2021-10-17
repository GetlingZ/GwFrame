package com.getling.gwframe.app

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.getling.gwframe.R
import com.getling.gwframe.scan.ScanUtil
import com.getling.gwframe.utils.DialogUtil
import com.getling.gwframe.utils.StatusBarUtil
import com.google.android.material.appbar.AppBarLayout

/**
 * @CreateDate: 2019/11/4 15:30
 * @Author: getling
 * @Description: 基础activity, 不要动，崩了别怪我
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), View.OnClickListener {

    protected lateinit var mDataBinding: B
    protected lateinit var context: Context
    protected lateinit var activity: AppCompatActivity
    protected lateinit var rootView: LinearLayout
    protected lateinit var wifiView: View

    /**
     * 当前Activity渲染的视图View
     */
    protected lateinit var mContentView: View
    protected var appBarLayout: AppBarLayout? = null
    protected var tvBaseTitle: TextView? = null
    protected lateinit var myToolbar: Toolbar
    protected lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        activity = this

        rootView = LinearLayout(context)
        rootView.orientation = LinearLayout.VERTICAL

        wifiView = layoutInflater.inflate(R.layout.view_wifi_error, rootView, false)
        rootView.addView(wifiView)
        mContentView = layoutInflater.inflate(bindLayout(), rootView, false)
//        mContentView.setBackgroundColor(Color.WHITE)
        mDataBinding = DataBindingUtil.bind(mContentView)!!
        mDataBinding.lifecycleOwner = this
        rootView.addView(mContentView)
        setContentView(rootView)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        )
        requestedOrientation = GwFrame.getInstance().factory.activityOrientation

        rootView.removeView(wifiView)
        wifiView.visibility = View.GONE

        progressDialog = DialogUtil.createProgressDialog(context, false)

        initParam()
        initView()
        initData()
    }
    /*-----------------------Honeywell PDA扫描生命周期-----------------------*/

    public override fun onResume() {
        super.onResume()
        ScanUtil.onResume()
    }

    public override fun onPause() {
        super.onPause()
        ScanUtil.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        ToastUtils.cancel()
    }
    /**
     * 绑定布局
     *
     * @return int
     */
    abstract fun bindLayout(): Int

    /**
     * 初始化Bundle参数
     */
    abstract fun initParam()

    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 获取数据
     */
    abstract fun initData()

    /**
     * 设置标题
     */
    fun setMyTitle(title: String) {
        if (appBarLayout == null) {
            appBarLayout = layoutInflater.inflate(
                R.layout.activity_base_tool_bar,
                rootView,
                false
            ) as AppBarLayout
            tvBaseTitle = appBarLayout!!.findViewById(R.id.tv_title_base)
            myToolbar = appBarLayout!!.findViewById(R.id.toolbar_base)
            setSupportActionBar(myToolbar)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            rootView.addView(
                appBarLayout,
                0,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }
        tvBaseTitle!!.text = title
    }

    fun setMyTitle(stringId: Int) {
        setMyTitle(getString(stringId))
    }

    fun setBarTitle(title: String) {
        if (tvBaseTitle != null) {
            tvBaseTitle!!.text = title
        }
    }

    fun setMyTitleSize(size: Float) {
        setMyTitleSize(TypedValue.COMPLEX_UNIT_PT, size)
    }

    fun setMyTitleSize(unit: Int, size: Float) {
        if (tvBaseTitle != null) {
            tvBaseTitle!!.setTextSize(unit, size)
        }
    }

    fun setRootBackground(@DrawableRes resId: Int) {
        rootView.setBackgroundResource(resId)
        if (appBarLayout != null) {
            appBarLayout!!.setBackgroundColor(ColorUtils.getColor(R.color.transparent))
//            appBarLayout!!.targetElevation = 0F
            //去除toolbar阴影
            appBarLayout!!.stateListAnimator = null
        }
        StatusBarUtil.setTranslucent(this, 0.0)
    }

    fun showProgressDialog(show: Boolean) {
        if (show) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    /**
     * 是否显示右上角添加按钮
     */
    fun showMenuItem(): Int {
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu.setGroupVisible(0, false)
        if (menu.findItem(showMenuItem()) != null) {
            menu.findItem(showMenuItem()).isVisible = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onClick(v: View) {

    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(
            super.getResources(),
            GwFrame.getInstance().factory.adaptWidth
        )
    }

}
