package com.getling.gwframe.pdf

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.blankj.utilcode.util.FileUtils
import com.getling.gwframe.R
import com.getling.gwframe.utils.NotifyUtil
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.*
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.shockwave.pdfium.PdfDocument
import java.io.File

class PDFViewActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener,
    OnPageErrorListener, OnPageScrollListener, OnErrorListener {

    companion object {
        private const val INTENT_NAME_PDF_PATH = "intent_key_pdf"
        fun start(context: Context, path: String) {
            val intent = Intent(context, PDFViewActivity::class.java)
            intent.putExtra(INTENT_NAME_PDF_PATH, path)
            context.startActivity(intent)
        }
    }

    private val tag = "pdf"

    private lateinit var toolbar: Toolbar
    private lateinit var pdfView: PDFView
    private lateinit var tvPosition: TextView
    private lateinit var pb: ProgressBar

    private var pdfFileName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_pdf_viewer)

        toolbar = findViewById(R.id.toolbar_pdf)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pdfView = findViewById(R.id.pdf_view)
        tvPosition = findViewById(R.id.tv_position_pdf)
        pb = findViewById(R.id.pb_pdf)

        pdfView.setOnClickListener {
            if (tvPosition.visibility == View.VISIBLE) {
                tvPosition.visibility = View.GONE
                toolbar.visibility = View.GONE
            } else {
                tvPosition.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
            }
        }

        val pdfFile = FileUtils.getFileByPath(intent.getStringExtra(INTENT_NAME_PDF_PATH))
        displayPDF(pdfFile)
    }

    private fun displayPDF(file: File) {
        pdfFileName = FileUtils.getFileName(file)

//        pdfView.fromAsset("sample.pdf")
        pdfView.fromFile(file)
//        pdfView.fromUri(UriUtils.file2Uri(file))
            .defaultPage(0)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .onPageScroll(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
            .onError(this)
            .onPageError(this)
            .load()
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor.use { c ->
                if (c != null && c.moveToFirst()) {
                    result = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.lastPathSegment
        }
        return result
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        tvPosition.text = String.format("%s / %s", page + 1, pageCount)
    }

    override fun loadComplete(nbPages: Int) {
        toolbar.title = pdfFileName
        pb.visibility = View.GONE

        val meta = pdfView.documentMeta
        Log.e(tag, "title = " + meta.title)
        Log.e(tag, "author = " + meta.author)
        Log.e(tag, "subject = " + meta.subject)
        Log.e(tag, "keywords = " + meta.keywords)
        Log.e(tag, "creator = " + meta.creator)
        Log.e(tag, "producer = " + meta.producer)
        Log.e(tag, "creationDate = " + meta.creationDate)
        Log.e(tag, "modDate = " + meta.modDate)

        printBookmarksTree(pdfView.tableOfContents, "-")
    }

    private fun printBookmarksTree(tree: List<PdfDocument.Bookmark>, sep: String) {
        for (b in tree) {
            Log.e(tag, String.format("%s %s, p %d", sep, b.title, b.pageIdx))
            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$sep-")
            }
        }
    }

    override fun onPageScrolled(page: Int, positionOffset: Float) {
//        tvPosition.visibility = View.GONE
//        toolbar.visibility = View.GONE
    }

    override fun onPageError(page: Int, t: Throwable?) {
        Log.e(tag, "Cannot load page $page")
        NotifyUtil.showError("加载失败，请稍后再试")
        pb.visibility = View.GONE
    }

    override fun onError(t: Throwable?) {
        Log.e(tag, "Cannot load")
        NotifyUtil.showError("加载失败，请稍后再试")
        pb.visibility = View.GONE
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
