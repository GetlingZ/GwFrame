package com.getling.gwframe.sample.adapter

import com.getling.gwframe.rv.adapter.BaseBindingRecyclerViewAdapter
import com.getling.gwframe.rv.viewholder.BaseBindingViewHolder
import com.getling.gwframe.sample.R

/**
 * @Author: getling
 * @CreateDate: 2020/8/10 14:55
 * @Description:
 */
class MainAdapter:
    BaseBindingRecyclerViewAdapter<String>(1) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_main
    }

    override fun convert(holder: BaseBindingViewHolder, item: String, position: Int) {
    }
}