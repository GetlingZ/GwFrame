package com.getling.gwframe.utils;

import com.blankj.utilcode.util.TimeUtils;

import java.util.Calendar;

/**
 * @Author: getling
 * @CreateDate: 2020/7/18 14:15
 * @Description:
 */
public class DateUtil {
    public final static String DatePatten = "yyyy-MM-dd";
    public final static String TimePatten = "HH:mm:ss";
    public final static String DateTimePatten = "yyyy-MM-dd HH:mm:ss";

    public static String calendar2String(Calendar calendar) {
        return TimeUtils.date2String(calendar.getTime(), DatePatten);
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
}
