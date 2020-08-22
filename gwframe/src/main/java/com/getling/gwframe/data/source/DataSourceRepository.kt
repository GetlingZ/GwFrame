package com.getling.gwframe.data.source

/**
 * @Author: getling
 * @CreateDate: 2019/11/20 13:31
 * @Description: 获取本地和远程数据源
 */
class DataSourceRepository private constructor(
    var remoteDataSource: DataSource.Remote,
    var localDataSource: DataSource.Local
) {

    companion object {

        @Volatile
        private var INSTANCE: DataSourceRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         *
         * @return the [DataSourceRepository] instance
         */
        @JvmStatic
        @Synchronized
        fun getInstance(
            remoteDataSource: DataSource.Remote,
            localDataSource: DataSource.Local
        ): DataSourceRepository {
            if (INSTANCE == null) {
                INSTANCE = DataSourceRepository(remoteDataSource, localDataSource)
            }
            return INSTANCE!!
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}