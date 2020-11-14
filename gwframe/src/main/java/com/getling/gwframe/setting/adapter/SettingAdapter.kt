package com.getling.gwframe.setting.adapter

import android.util.SparseArray
import com.getling.gwframe.R
import com.getling.gwframe.rv.adapter.BaseRecyclerViewAdapter
import com.getling.gwframe.rv.viewholder.BaseViewHolder

/**
 * @Author: getling
 * @CreateDate: 2019/12/3 8:34
 * @Description:
 */
open class SettingAdapter : BaseRecyclerViewAdapter<String>() {

    private var contentArray: SparseArray<String>? = null

    override fun getItemLayoutId(): Int {
        return R.layout.item_setting_base
    }

    override fun convert(holder: BaseViewHolder?, item: String) {
        holder?.setText(R.id.tv_title_setting, item)
        val content = contentArray?.get(holder!!.adapterPosition)
        if (content != null) {
            holder?.setText(R.id.tv_content_setting, content)
        }
    }

    fun setContentArray(array: SparseArray<String>) {
        contentArray = array
        notifyDataSetChanged()
    }

    fun getContentArray(): SparseArray<String>? {
        return contentArray
    }
}