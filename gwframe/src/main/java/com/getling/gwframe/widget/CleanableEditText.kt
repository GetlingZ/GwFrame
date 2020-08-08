package com.getling.gwframe.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.Filterable
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ListAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.blankj.utilcode.util.StringUtils
import com.getling.gwframe.R

/**
 * @Author: getling
 * @CreateDate: 2019/11/6 14:19
 * @Description:
 */
class CleanableEditText : FrameLayout {

    private var autoCompleteTextView: AppCompatAutoCompleteTextView? = null
    private var imageView: ImageView? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_cleanable_et, this)
        autoCompleteTextView = findViewById(R.id.et_cleanable)
        imageView = findViewById(R.id.iv_cleanable)

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditText)
            val hint = typedArray.getString(R.styleable.CleanableEditText_cleanableHint)
            autoCompleteTextView!!.hint = hint
            typedArray.recycle()
        }

        autoCompleteTextView!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (StringUtils.isEmpty(s)) {
                    imageView!!.visibility = View.GONE
                } else {
                    imageView!!.visibility = View.VISIBLE
                }
            }
        })

        imageView!!.setOnClickListener { autoCompleteTextView!!.text = null }

    }

    fun getEditText(): AppCompatAutoCompleteTextView {
        return autoCompleteTextView!!
    }

    fun getInputText(): String {
        return autoCompleteTextView?.text.toString().trim()
    }

    fun setInputText(text: String?) {
        autoCompleteTextView?.setText(text)
    }

    fun setMyHint(hint: String) {
        autoCompleteTextView?.hint = hint
    }

    fun <T> setSuggestionAdapter(adapter: T) where T : ListAdapter, T : Filterable {
        if (autoCompleteTextView != null) {
            autoCompleteTextView!!.setAdapter(adapter)
        }
    }
}
