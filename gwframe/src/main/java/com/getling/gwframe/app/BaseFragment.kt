package com.getling.gwframe.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.fragment.app.Fragment


/**
 * @CreateDate: 2019/11/4 15:31
 * @Author: getling
 * @Description: 基础fragment
 */
abstract class BaseFragment<B : ViewDataBinding> : Fragment(), View.OnClickListener {

    protected lateinit var myContext: Context
    protected lateinit var mDataBinding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, bindLayout(), container, false)
        initView()
        initData()
        return mDataBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.myContext = context
    }

    /**
     * 绑定布局
     *
     * @return int
     */
    abstract fun bindLayout(): Int

    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 业务操作
     */
    abstract fun initData()

    override fun onClick(v: View) {

    }
}
