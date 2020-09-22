package com.getling.gwframe.imagepreview

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.getling.gwframe.R
import com.getling.gwframe.config.PathConfig
import com.getling.gwframe.constant.MsgConstant
import com.getling.gwframe.constant.StringConstant
import com.getling.gwframe.http.observer.SimpleObserver
import com.getling.gwframe.utils.NotifyUtil
import com.github.chrisbanes.photoview.PhotoView
import rxhttp.RxHttp

/**
 * @CreateDate: 2020/9/3 18:31
 * @Author: getling
 * @Description: 图片预览
 */
class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var imageList: Array<String>

    companion object {
        const val INTENT_NAME_IMAGE_PREVIEW = "intent_name_image_preview"
        fun start(context: Context, imageList: Array<String>) {
            val intent = Intent(context, ImagePreviewActivity::class.java)
            intent.putExtra(INTENT_NAME_IMAGE_PREVIEW, imageList)
            context.startActivity(intent)
        }
    }

    private lateinit var toolbar: Toolbar
    private lateinit var tvSave: TextView
    private lateinit var tvPosition: TextView
    private lateinit var vp: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_image_preview)

        toolbar = findViewById(R.id.toolbar_ip)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvSave = findViewById(R.id.tv_save_ip)
        tvPosition = findViewById(R.id.tv_position_ip)
        vp = findViewById(R.id.vp_ip)

        tvSave.setOnClickListener {
            saveImage()
        }

        imageList = intent.getStringArrayExtra(INTENT_NAME_IMAGE_PREVIEW)!!
        vp.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, o: Any): Boolean {
                return view == o
            }

            override fun getCount(): Int {
                return imageList.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val item = layoutInflater.inflate(
                    R.layout.item_image_preview, container, false
                )
                val photoView = item.findViewById<PhotoView>(R.id.photo_view_image_preview)
                val progressView = item.findViewById<MyProgressView>(R.id.pb_image_preview)
                val ivError = item.findViewById<ImageView>(R.id.iv_load_error)
                val imgUrl = imageList[position]

                ProgressInterceptor.addListener(
                    imgUrl
                ) { progress ->
                    Log.d("11111111111111111", "onProgress: $progress")
                    progressView.post {
                        progressView.setProgress(progress)
                    }
                }
                val imageViewTarget = object : DrawableImageViewTarget(photoView) {
                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        Log.d("sssssssss", "onStart: ")
                        progressView.visibility = View.VISIBLE
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        super.onResourceReady(resource, transition)
                        Log.d("readddddddd", "onResourceReady: ")
                        progressView.setProgress(100)
                        ivError.visibility = View.GONE
                        progressView.visibility = View.GONE
                        ProgressInterceptor.removeListener(imgUrl)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        ivError.visibility = View.VISIBLE
                        progressView.visibility = View.GONE
                    }
                }

                GlideApp.with(photoView)
                    .load(imgUrl)
                    .into(imageViewTarget)

                // Now just add PhotoView to ViewPager and return it
                container.addView(
                    item,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                return item
            }

            override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
                container.removeView(o as View)
            }
        }
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val buffer = StringBuffer()
                buffer.append(position + 1)
                    .append(StringConstant.SLASH)
                    .append(imageList.size)
                tvPosition.text = buffer
            }

            override fun onPageSelected(position: Int) {

            }
        })
    }

    private fun saveImage() {
        val imageUrl = imageList[vp.currentItem]
        val imageName = imageUrl.split("/").last()
        RxHttp.get(imageUrl)
            .asDownload(PathConfig.PicturePath + imageName)
            .subscribe(object : SimpleObserver<String>() {
                override fun onSuccess(t: String?) {
                    NotifyUtil.showSuccess(MsgConstant.SUC_SAVE)
                }

                override fun onError(e: Throwable) {
                    NotifyUtil.showError(MsgConstant.ERROR_SAVE)
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
