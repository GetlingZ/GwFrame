package com.getling.gwframe.sample.ui.login

import com.getling.gwframe.login.BaseManageActivity
import com.getling.gwframe.sample.R

class ManageActivity : BaseManageActivity() {

    override fun initData() {
        titleList.addAll(resources.getStringArray(R.array.HomePageTitles).asList())

        drawableList.add(R.drawable.icon_produce_plan)
        drawableList.add(R.drawable.icon_mat_stock_manage)
        drawableList.add(R.drawable.icon_send_manage)
        drawableList.add(R.drawable.icon_pro_stock_manage)
        drawableList.add(R.drawable.icon_pro_send)

        adapter.setDrawable(drawableList)
        adapter.data = titleList
    }
}