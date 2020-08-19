package com.getling.gwframe.http.utils

import android.content.Context
import com.blankj.utilcode.util.GsonUtils
import com.getling.gwframe.http.BaseUrl
import com.getling.gwframe.data.entity.BaseInfoEntity
import com.rxlife.coroutine.RxLifeScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope
import rxhttp.*
import java.io.File
import java.net.ConnectException

/**
 * @Author: getling
 * @CreateDate: 2020/8/3 8:45
 * @Description:
 */
class RxHttpSample {
    suspend fun get() {
        RxHttp.get("")
            .toClass<BaseInfoEntity>()
            .await()

        //统一处理返回码
        val life = RxLifeScope()
        life.launch({
            RxHttp.get("")
                .toResponse<BaseUrl>()
                .await()
        }, {
            it.message

        })
    }

    fun testRetry() {
        val life = RxLifeScope()
        life.launch {
            RxHttp.get("")
                .toResponse<BaseInfoEntity>()
//                .retry()    //无条件、不间断、一直重试
//                .retry(2)   //无条件、不间断、重试两次
//                .retry(2, 1000)   //无条件 间隔1s 重试2此
//                .retry { it is ConnectException } //有条件、不间断、一直重试
//                .retry(2) { it is ConnectException }  //有条件、不间断、重试2次
//                .retry(2, 1000) { it is ConnectException }  //有条件、间隔1s、重试2次
//                .retry(period = 1000) { it is ConnectException } //有条件、间断1s、一直重试
                .retry(2, 1000) {
                    //重试2次，每次间隔1s
                    //如果是网络异常就重试
                    it is ConnectException
                }
                .await()
        }
    }

    suspend fun testTimeOut() {
        val student = RxHttp.postForm("/service/...")
            .toResponse<BaseInfoEntity>()
            .timeout(3000)      //超时时长为3s
            .await()
    }

    suspend fun testAsync(context: Context) {
        //需要多个请求同时进行
        coroutineScope {
            //同时获取两个学生信息
            val asyncStudent1 = RxHttp.postForm("/service/...")
                .toResponse<BaseInfoEntity>()
                .async(this)   //this为CoroutineScope对象，这里会返回Deferred<Student>

            val asyncStudent2 = RxHttp.postForm("/service/...")
                .toResponse<BaseInfoEntity>()
                .async(this)   //this为CoroutineScope对象，这里会返回Deferred<Student>

            //随后调用await方法获取对象
            val student1 = asyncStudent1.await()
            val student2 = asyncStudent2.await()
        }
    }

    suspend fun testDelay() {
        val student = RxHttp.postForm("/service/...")
            .toResponse<BaseInfoEntity>()
            .delay(1000)      //请求回来后，延迟1s返回
            .await()

        val student1 = RxHttp.postForm("/service/...")
            .toResponse<BaseInfoEntity>()
            .startDelay(1000)     //延迟1s后再发送请求
            .await()
    }

    suspend fun testTryAwait() {
        //如果你不想在异常时返回默认值，又不想异常是影响程序的执行，
        // tryAwait就派上用场了，它会在异常出现时，返回null，如下：
        val student = RxHttp.postForm("/service/...")
            .toResponse<BaseInfoEntity>()
            .timeout(100)      //超时时长为100毫秒
            .tryAwait()     //这里返回 Student? 对象，即有可能为空
    }

    suspend fun testMap() {
        //将返回结果进行转换
        val student = RxHttp.postForm("/service/...")
            .toStr()
            .map {
                GsonUtils.fromJson(
                    it,
                    BaseInfoEntity::class.java
                )
            }//String转Int
            .tryAwait()     //这里返回 Student? 对象，即有可能为空
        student?.ip = ""
    }

    suspend fun testUpload() {
        val result = RxHttp.postForm("/service/...")
            .addFile("file", File("xxx/1.png"))        //添加单个文件
            .addFile("fileList", ArrayList<File>())    //添加多个文件
            .toResponse<String>()
            .await()

        coroutineScope {
            val result1 = RxHttp.postForm("/service/...")
                .addFile("file", File("xxx/1.png"))
                .addFile("fileList", ArrayList<File>())
                .upload(this) {    //此this为CoroutineScope对象，即当前协程对象
                    //it为Progress对象
                    val process = it.progress         //已上传进度  0-100
                    val currentSize = it.currentSize  //已上传size，单位：byte
                    val totalSize = it.totalSize      //要上传的总size  单位：byte
                }
                .toResponse<String>()
                .await()
        }
    }

    suspend fun testDown() {
        val localPath = "sdcard//android/data/..../1.apk"
        val student = RxHttp.get("/service/...")
            .toDownload(localPath)  //下载需要传入本地文件路径
            .await()

        coroutineScope {
            val localPath1 = "sdcard//android/data/..../1.apk"
            val student1 = RxHttp.get("/service/...")
                .toDownload(localPath1, this) {   //此this为CoroutineScope对象
                    //it为Progress对象
                    val process = it.progress        //已下载进度 0-100
                    val currentSize = it.currentSize //已下载size，单位：byte
                    val totalSize =
                        it.totalSize     //要下载的总size 单位：byte
                }
                .await()
        }

    }

    fun sendSerial() {
        val job = RxLifeScope()
            .launch {
                val student = getStudent()
                val persons = getPersons(student)
            }
        job.cancel()
    }

    private suspend fun getStudent(): BaseInfoEntity {
        return RxHttp.get("")
            .toClass<BaseInfoEntity>()
            .await()
    }

    private suspend fun getPersons(info: BaseInfoEntity): List<String> {
        return RxHttp.get("")
            .add("ip", info.ip)
            .toClass<List<String>>()
            .await()
    }

    fun sendParallel() {
        val job = RxLifeScope().launch {
            val banner = getBanners(this).await()
            val person = getPersons(this).await()
        }
        job.cancel()
    }

    suspend fun getBanners(scope: CoroutineScope): Deferred<List<BaseInfoEntity>> {
        return RxHttp.get("")
            .toClass<List<BaseInfoEntity>>()
            .async(scope)
    }

    suspend fun getPersons(scope: CoroutineScope): Deferred<List<BaseInfoEntity>> {
        return RxHttp.get("")
            .toClass<List<BaseInfoEntity>>()
            .async(scope)
    }
}