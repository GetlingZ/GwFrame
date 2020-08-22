package com.getling.gwframe.sample.vm

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.getling.gwframe.data.source.DataSourceRepository
import com.getling.gwframe.data.vm.ViewModelFactory

/**
 * @Author: getling
 * @CreateDate: 2020/8/21 10:22
 * @Description:
 */
class MyVmFactory private constructor(dataSourceRepository: DataSourceRepository) :
    ViewModelFactory(dataSourceRepository) {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MyViewModel::class.java) ->
                    MyViewModel()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        @Synchronized
        fun getInstance(dataSourceRepository: DataSourceRepository): ViewModelFactory {
            if (INSTANCE == null) {
                INSTANCE = ViewModelFactory(dataSourceRepository)
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}