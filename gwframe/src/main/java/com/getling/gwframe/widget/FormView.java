package com.getling.gwframe.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.getling.gwframe.R;
import com.getling.gwframe.utils.DialogUtil;
import com.getling.gwframe.utils.NotifyUtil;
import com.getling.gwframe.utils.NumberUtil;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: getling
 * @CreateDate: 2019/10/19 8:58
 * @Description:
 */
public class FormView extends LinearLayout {
    public FormView(Context context) {
        super(context);
        init(context, null);
    }

    public FormView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FormView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private final int dialog_text = 1;
    private final int dialog_num = 2;
    private TextView tvTitle;
    private DrawableEditText etContent;

    //设置方向
    private static final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_form, this);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        tvTitle = findViewById(R.id.tv_title_form);
        etContent = findViewById(R.id.et_content_form);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FormView);
            String title = typedArray.getString(R.styleable.FormView_formTitle);
            String content = typedArray.getString(R.styleable.FormView_formContent);
            int contentColor = typedArray.getColor(R.styleable.FormView_formContentColor, 0xff333333);
            int titleColor = typedArray.getColor(R.styleable.FormView_formTitleColor, 0xff333333);
            int titleGravity = typedArray.getInt(R.styleable.FormView_titleGravity, 1);

            int titleSize = typedArray.getInt(R.styleable.FormView_formTitleSize, 0);
            int contentSize = typedArray.getInt(R.styleable.FormView_formContentSize, 0);
            int textSize = typedArray.getInt(R.styleable.FormView_formTextSize, 0);
            int myTypeValue = typedArray.getInt(R.styleable.FormView_myTypeValue, 3);

            int bg = typedArray.getInt(R.styleable.FormView_formBackground, 1);
            boolean isEditable = typedArray.getBoolean(R.styleable.FormView_edit_enable, true);

            tvTitle.setText(title);
            tvTitle.setTextColor(titleColor);
            if (titleSize > 0) {
                tvTitle.setTextSize(myTypeValue, titleSize);
            }

            etContent.setText(content);
            etContent.setTextColor(contentColor);
            if (contentSize > 0) {
                etContent.setTextSize(myTypeValue, contentSize);
            }

            if (textSize > 0) {
                tvTitle.setTextSize(myTypeValue, textSize);
                etContent.setTextSize(myTypeValue, textSize);
            }

            if (titleGravity == 1) {
                tvTitle.setGravity(Gravity.START);
            } else if (titleGravity == 2) {
                tvTitle.setGravity(Gravity.END);
            } else {
                tvTitle.setGravity(Gravity.CENTER);
            }

            if (bg == 1) {
                etContent.setBackgroundResource(R.drawable.shape_edit_bg_white);
            } else {
                etContent.setBackgroundResource(R.drawable.shape_edit_bg);
            }
            etContent.setFocusable(isEditable);
            etContent.setFocusableInTouchMode(isEditable);

            int mWidth = typedArray.getDimensionPixelSize
                    (R.styleable.DrawableEditText_drawable_width, 0);
            int mHeight = typedArray.getDimensionPixelSize
                    (R.styleable.DrawableEditText_drawable_height, 0);
            Drawable mDrawable = typedArray.getDrawable
                    (R.styleable.DrawableEditText_drawable_src);
            int mLocation = typedArray.getInt
                    (R.styleable.DrawableEditText_drawable_location, LEFT);
            etContent.drawDrawable(mDrawable, mWidth, mHeight, mLocation);

            final int type = typedArray.getInt(R.styleable.SettingItemView_showInputDialog, 0);
            if (type != 0) {
                etContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                etContent.getPaint().setAntiAlias(true);//抗锯齿
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initInputDialog(type);
                    }
                });
            }

            typedArray.recycle();
        }
    }

    public FormView setTitle(String title) {
        if (TextUtils.isEmpty(title) || "null".equals(title)) {
            title = "-";
        }
        tvTitle.setText(title);
        return this;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(tvTitle.getText())) {
            return "";
        }
        return tvTitle.getText().toString();
    }

    public FormView setContent(String content) {
        if (TextUtils.isEmpty(content) || "null".equals(content)) {
            content = "-";
        }
        etContent.setText(content);
        return this;
    }

    public String getContent() {
        if (TextUtils.isEmpty(etContent.getText())) {
            return "";
        }
        return etContent.getText().toString();
    }

    @BindingAdapter({"bindContent"})
    public static void bindContent(@NotNull FormView settingItemView, String content) {
        settingItemView.setContent(content);
    }

    @BindingAdapter({"bindContentColor"})
    public static void bindContentColor(@NotNull FormView settingItemView, int color) {
        settingItemView.setContentColor(color);
    }

    public FormView setTitleColor(int color) {
        tvTitle.setTextColor(getResources().getColor(color, null));
        return this;
    }

    public FormView setContentColor(int color) {
        etContent.setTextColor(getResources().getColor(color, null));
        return this;
    }

    public FormView isEditable(boolean editable) {
        etContent.setFocusable(editable);
        etContent.setFocusableInTouchMode(editable);
        return this;
    }

    public FormView setBg(int bg) {
        if (bg == 1) {
            etContent.setBackgroundResource(R.drawable.shape_edit_bg_white);
        } else {
            etContent.setBackgroundResource(R.drawable.shape_edit_bg);
        }
        return this;
    }

    private void initInputDialog(int type) {
        if (type == dialog_num) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input_num, null);
            final EditText et = view.findViewById(R.id.et_input_num);
            DialogUtil.showInputDialog(getContext(), true, getResources().getString(R.string.text_change_num), view, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    double newNum = NumberUtil.toDouble(et.getText().toString());
                    if (onInputZeroListener != null) {
                        onInputZeroListener.onInput(newNum);
                        return;
                    }
                    if (newNum == 0) {
                        NotifyUtil.showWarning(getResources().getString(R.string.text_input_num_over_0));
                        return;
                    }
                    if (onInputNumListener != null) {
                        onInputNumListener.onInput(newNum);
                    }
                }
            });
        }
        if (type == dialog_text) {
            DialogUtil.showEditTextDialog(getContext(), true, getResources().getString(R.string.text_please_input), new DialogUtil.OnInputListener() {
                @Override
                public void onInput(String text) {
                    if (onInputTextListener != null) {
                        onInputTextListener.onInput(text);
                    }
                }
            });
        }
    }

    public void setMyOnClickListener(OnClickListener listener) {
        if (listener != null) {
            tvTitle.setOnClickListener(listener);
            etContent.setOnClickListener(listener);
        }
    }

    private OnInputNumListener onInputNumListener;
    private OnInputTextListener onInputTextListener;
    private OnInputZeroListener onInputZeroListener;

    public void setOnInputListener(OnInputNumListener onInputNumListener) {
        this.onInputNumListener = onInputNumListener;
    }

    public void setOnInputTextListener(OnInputTextListener onInputTextListener) {
        this.onInputTextListener = onInputTextListener;
    }

    public void setOnInputZeroListener(OnInputZeroListener onInputZeroListener) {
        this.onInputZeroListener = onInputZeroListener;
    }

    public interface OnInputNumListener {
        void onInput(double data);
    }

    public interface OnInputTextListener {
        void onInput(String data);
    }

    public interface OnInputZeroListener {
        void onInput(double data);
    }
}
