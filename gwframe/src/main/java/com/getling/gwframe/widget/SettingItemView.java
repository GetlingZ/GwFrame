package com.getling.gwframe.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
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
public class SettingItemView extends LinearLayout {
    public SettingItemView(Context context) {
        super(context);
        init(context, null);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private final int dialog_text = 1;
    private final int dialog_num = 2;
    private TextView tvTitle;
    private TextView tvContent;

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_setting_item, this);
        setOrientation(HORIZONTAL);
//        setGravity(Gravity.CENTER);

        tvTitle = findViewById(R.id.tv_title_setting);
        tvContent = findViewById(R.id.tv_content_setting);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SettingItemView);
            String title = typedArray.getString(R.styleable.SettingItemView_settingTitle);
            String content = typedArray.getString(R.styleable.SettingItemView_settingContent);
            int contentColor = typedArray.getColor(R.styleable.SettingItemView_settingContentColor, 0xff333333);
            int titleColor = typedArray.getColor(R.styleable.SettingItemView_settingTitleColor, 0xff333333);
            int titleGravity = typedArray.getInt(R.styleable.SettingItemView_titleGravity, 1);

            int titleSize = typedArray.getInt(R.styleable.SettingItemView_settingTitleSize, 0);
            int contentSize = typedArray.getInt(R.styleable.SettingItemView_settingContentSize, 0);
            int textSize = typedArray.getInt(R.styleable.SettingItemView_settingTextSize, 0);
            int myTypeValue = typedArray.getInt(R.styleable.SettingItemView_myTypeValue, 3);

            tvTitle.setText(title);
            tvTitle.setTextColor(titleColor);
            if (titleSize > 0) {
                tvTitle.setTextSize(myTypeValue, titleSize);
            }

            tvContent.setText(content);
            tvContent.setTextColor(contentColor);
            if (contentSize > 0) {
                tvContent.setTextSize(myTypeValue, contentSize);
            }

            if (textSize > 0) {
                tvTitle.setTextSize(myTypeValue, textSize);
                tvContent.setTextSize(myTypeValue, textSize);
            }

            if (titleGravity == 1) {
                tvTitle.setGravity(Gravity.START);
            } else if (titleGravity == 2) {
                tvTitle.setGravity(Gravity.END);
            } else {
                tvTitle.setGravity(Gravity.CENTER);
            }

            final int type = typedArray.getInt(R.styleable.SettingItemView_showInputDialog, 0);
            if (type != 0) {
                tvContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvContent.getPaint().setAntiAlias(true);//抗锯齿
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

    public SettingItemView setTitle(String title) {
        if (TextUtils.isEmpty(title) || "null".equals(title)) {
            title = "-";
        }
        tvTitle.setText(title);
        return this;
    }

    public SettingItemView setContent(String content) {
        if (TextUtils.isEmpty(content) || "null".equals(content)) {
            content = "-";
        }
        tvContent.setText(content);
        return this;
    }

    @BindingAdapter({"bindContent"})
    public static void bindContent(@NotNull SettingItemView settingItemView, String content) {
        settingItemView.setContent(content);
    }

    @BindingAdapter({"bindContentColor"})
    public static void bindContentColor(@NotNull SettingItemView settingItemView, @ColorRes int color) {
        settingItemView.setContentColor(color);
    }

    public SettingItemView setTitleColor(@ColorRes int color) {
        tvTitle.setTextColor(getResources().getColor(color, null));
        return this;
    }

    public SettingItemView setContentColor(@ColorRes int color) {
        tvContent.setTextColor(getResources().getColor(color, null));
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
