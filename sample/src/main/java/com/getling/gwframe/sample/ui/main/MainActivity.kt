package com.getling.gwframe.sample.ui.main

import android.content.Intent
import com.getling.gwframe.app.BaseActivity
import com.getling.gwframe.rv.adapter.ManageIconAdapter
import com.getling.gwframe.rv.decoration.GridDecoration
import com.getling.gwframe.sample.R
import com.getling.gwframe.sample.databinding.ActivityMainBinding
import com.getling.gwframe.sample.ui.thread.ThreadActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initParam() {
    }

    private lateinit var adapter: ManageIconAdapter
    override fun initView() {
        setMyTitle("首页")

        adapter = ManageIconAdapter()
        mDataBinding.rvMain.adapter = adapter
        mDataBinding.rvMain.addItemDecoration(GridDecoration(context, 3))
    }

    override fun initData() {
        val titleList = mutableListOf<String>()
        titleList.add("线程")

        val activityList = mutableListOf<Class<*>>()
        activityList.add(ThreadActivity::class.java)

        adapter.data = titleList

        adapter.setOnItemClickListener { _, position ->
            startActivity(Intent(context, activityList[position]))
        }
    }

}
