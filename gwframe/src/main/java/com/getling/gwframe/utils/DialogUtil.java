package com.getling.gwframe.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.getling.gwframe.R;
import com.getling.gwframe.constant.SPConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: getling
 * @CreateDate: 2019/9/28 15:22
 * @Description:
 */
public class DialogUtil {
    /**
     * 日期选择
     *
     * @param activity
     */
    public static void showDatePickerDialog(Activity activity, final MyOnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, 0, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                listener.onDateSet(DateUtil.calendar2String(c), c);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 每次打开日期选择器，都是上一次选择的日期
     */
    public static void showDatePickerDialog(Activity activity, Calendar calendar, final MyOnDateSetListener listener) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        if (calendar == null) {
            calendar = Calendar.getInstance(Locale.CHINA);
        }
        new DatePickerDialog(activity, 0, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                listener.onDateSet(DateUtil.calendar2String(c), c);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     */
    public static void showTimePickerDialog(Activity activity, Calendar calendar, MyOnDateSetListener listener) {
        if (calendar == null) {
            calendar = Calendar.getInstance(Locale.CHINA);
        }
        Calendar finalCalendar = calendar;
        TimePickerView pvTime = new TimePickerBuilder(activity, (date, v) -> {
            finalCalendar.setTime(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            listener.onDateSet(format.format(date), finalCalendar);
        }).setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .setSubmitColor(ColorUtils.getColor(R.color.color_03A9F4))//确定按钮文字颜色
                .setCancelColor(ColorUtils.getColor(R.color.color_03A9F4))//取消按钮文字颜色
                .setDate(calendar)
                .build();
        pvTime.show();
//        Dialog mDialog = pvTime.getDialog();
//        if (mDialog != null) {
//
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            pvTime.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
//                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
//                dialogWindow.setDimAmount(0.3f);
//            }
//        }
    }

    public interface MyOnDateSetListener {
        void onDateSet(String formDate, Calendar calendar);
    }

    public static AlertDialog showInputDialog(final Context context, boolean cancelable,
                                              String title, View contentView,
                                              DialogInterface.OnClickListener listener) {
        AlertDialog.Builder customizeDialog = new AlertDialog.Builder(context);
        customizeDialog.setTitle(title);
        customizeDialog.setView(contentView);
        customizeDialog.setPositiveButton("确定", listener);
        customizeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (context instanceof Activity) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            KeyboardUtils.hideSoftInput((Activity) context);
                        }
                    }, 100);
                }
            }
        });
        AlertDialog alertDialog = customizeDialog.create();
        if (!cancelable) {
            alertDialog.setCancelable(false);
            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                        return true;
                    } else {
                        return false; //默认返回 false
                    }
                }
            });
        }
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showEditTextDialog(final Context context, boolean cancelable,
                                                 String title, OnInputListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_text, null);
        final EditText editText = view.findViewById(R.id.et_input_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listener != null) {
                            listener.onInput(editText.getText().toString());
                        }
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (context instanceof Activity) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            KeyboardUtils.hideSoftInput((Activity) context);
                        }
                    }, 100);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        if (!cancelable) {
            alertDialog.setCancelable(false);
            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                        return true;
                    } else {
                        return false; //默认返回 false
                    }
                }
            });
        }
        alertDialog.show();
        return alertDialog;

    }

    public interface OnInputListener {
        void onInput(String text);
    }

    /**
     * 显示一个普通对话框
     */
    public static AlertDialog showNormalDialog(Context context, boolean cancelable, String title, String msg,
                                               String positiveString, DialogInterface.OnClickListener listener,
                                               String negativeString, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveString, listener);
        builder.setNegativeButton(negativeString, negativeListener);
        if (!cancelable) {
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                        return true;
                    } else {
                        return false; //默认返回 false
                    }
                }
            });
            alertDialog.show();
            return alertDialog;
        }
        return builder.show();
    }

    public static AlertDialog showNormalDialog(Context context, String title, String msg,
                                               String positiveString, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveString, listener);
        return builder.show();
    }

    public static AlertDialog showSingleChoiceDialog(Context context, String title, String[] data, boolean savePosition, final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        int position = SPUtils.getInstance().getInt(SPConstant.SP_FACTORY_POSITION, -1);
        if (!savePosition) {
            position = -1;
        }
        builder.setSingleChoiceItems(data, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (savePosition) {
                    SPUtils.getInstance().put(SPConstant.SP_FACTORY_POSITION, which);
                }
                listener.onClick(dialog, which);
            }
        });
        AlertDialog dialog = builder.show();
        if (data != null) {
            setDialogHeight(dialog, data.length);
        }
        return dialog;
    }

    /**
     * 单项选择对话框
     */
    public static AlertDialog showSingleChoiceDialog(Context context, String title, String[] data, int defaultPosition, final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(data, defaultPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, which);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
        if (data != null) {
            setDialogHeight(dialog, data.length);
        }
        return dialog;
    }

    /**
     * 设置 dialog的高度
     * 可根据list的条数来设置高度
     *
     * @param dialog
     */
    public static void setDialogHeight(AlertDialog dialog, int count) {
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = window.getAttributes();
        if (count > 5) {
            //设置为当前屏幕高度的2/3}
            p.height = ScreenUtils.getScreenHeight() / 3 * 2;

        }
        if (count <= 5) {
            p.height = RecyclerView.LayoutParams.WRAP_CONTENT;
        }
        //设置生效
        window.setAttributes(p);
    }

    /**
     * 不可取消
     */
    public static AlertDialog dialogCancelable(AlertDialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
        });
        return dialog;
    }

    public static AlertDialog createLineProgressDialog(Context context, boolean cancelable, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_line_progress, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        if (!cancelable) {
            dialogCancelable(dialog);
        }

        TextView tvTitle = view.findViewById(R.id.tv_title_dialog);
        TextView tvOption = view.findViewById(R.id.tv_option_dialog);
        ProgressBar pb = view.findViewById(R.id.pb_dialog);
        return dialog;
    }

    public static AlertDialog createProgressDialog(Context context, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.setCancelable(cancelable);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH && !cancelable) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.setDimAmount(0f);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        return dialog;
    }

    interface ProgressBarListener {
        void setProgressBar(ProgressBar progress);
    }
}
