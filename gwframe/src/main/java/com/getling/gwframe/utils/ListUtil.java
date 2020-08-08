package com.getling.gwframe.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

/**
 * @Author: getling
 * @CreateDate: 2019/5/29 17:33
 * @Description:
 */
public class ListUtil {
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    /**
     * 判断是否是一个空集合
     */
    public static boolean isEmptyList(Collection list) {
        return list == null || list.isEmpty();
    }
}
