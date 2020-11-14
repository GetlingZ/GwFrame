package com.getling.gwframe.login

import android.content.Intent
import android.graphics.Color
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.getling.gwframe.R
import com.getling.gwframe.app.BaseActivity
import com.getling.gwframe.constant.SPConstant
import com.getling.gwframe.databinding.ActivityManageBaseBinding
import com.getling.gwframe.rv.adapter.ManageIconAdapter
import com.getling.gwframe.rv.decoration.GridDecoration
import com.getling.gwframe.scan.ScanConfig
import com.getling.gwframe.scan.ScanUtil
import com.getling.gwframe.setting.BaseSettingActivity
import com.getling.gwframe.update.UpdateUtil
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main_nav_header.view.*

/**
 * @CreateDate: 2020/10/8 9:30
 * @Author: getling
 * @Description: 首页
 */
open class BaseManageActivity : BaseActivity<ActivityManageBaseBinding>(),
    NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var adapter: ManageIconAdapter

    override fun bindLayout(): Int {
        return R.layout.activity_manage_base
    }

    override fun initParam() {
        ScanUtil.setScanMode(ScanConfig.SCAN_MODE_PHONE)
        UpdateUtil.checkUpdate(this, false)
    }

    override fun initView() {

        val toolbar = mDataBinding.barMain.toolbarMain
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val window = activity.window
        val decorView = window.decorView
        //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

        val navView: NavigationView = mDataBinding.navViewMain
        navView.setNavigationItemSelectedListener(this)

        val drawerLayout: DrawerLayout = mDataBinding.drawerLayoutMain
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.syncState()
        drawerLayout.addDrawerListener(toggle)

        mDataBinding.tvAppVersion.text = AppUtils.getAppVersionName()
        mDataBinding.navViewMain.getHeaderView(0).tv_user_name.text =
            SPUtils.getInstance().getString(SPConstant.SP_USER_NICK_NAME)
        mDataBinding.navViewMain.getHeaderView(0).tv_factory_main.text =
            SPUtils.getInstance().getString(SPConstant.SP_FACTORY_NAME)

        adapter = ManageIconAdapter()
        adapter.setOnItemClickListener { _, position ->
            if (activityList.size > position) {
                context.startActivity(Intent(context, activityList[position]))
            }
        }

        mDataBinding.barMain.contentMain.rvMain.adapter = adapter
        mDataBinding.barMain.contentMain.rvMain.addItemDecoration(GridDecoration(context, 3))
    }

    var activityList: ArrayList<Class<*>> = arrayListOf()
    var titleList: ArrayList<String> = arrayListOf()
    var drawableList: ArrayList<Int> = arrayListOf()
    override fun initData() {

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_nav_setting -> {
                startActivity(Intent(context, BaseSettingActivity::class.java))
            }
        }
        mDataBinding.drawerLayoutMain.closeDrawer(GravityCompat.START)
        return true
    }

    private var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (mDataBinding.drawerLayoutMain.isDrawerOpen(GravityCompat.START)) {
                mDataBinding.drawerLayoutMain.closeDrawer(GravityCompat.START)
                return true
            }
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showShort("再按一次退出")
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        ToastUtils.cancel()
    }
}
