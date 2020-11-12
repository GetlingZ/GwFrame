package com.getling.gwframe.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: getling
 * @CreateDate: 2020/7/18 14:15
 * @Description:
 */
public class DateUtil {
    public final static String DatePatten = "yyyy-MM-dd";
    public final static String TimePatten = "HH:mm:ss";
    public final static String DateTimePatten = "yyyy-MM-dd HH:mm:ss";
    private static final String INT_PATTEN = "yyyyMMddHHmmss";

    public static String calendar2String(Calendar calendar) {
        return TimeUtils.date2String(calendar.getTime(), DatePatten);
    }

    public static String calendar2String(Calendar calendar, String patten) {
        return TimeUtils.date2String(calendar.getTime(), patten);
    }

    /**
     * 获取年
     *
     * @return 年
     */
    public static int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取今天日期
     */
    public static String getToday() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return formatter.format(date);
    }

    public static String getDateFromLong(String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = INT_PATTEN;
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault(Locale.Category.FORMAT));
        return format.format(date);
    }
}
