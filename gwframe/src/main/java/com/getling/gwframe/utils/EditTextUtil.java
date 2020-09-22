package com.getling.gwframe.utils;

import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

/**
 * @Author: getling
 * @CreateDate: 2020/9/8 16:07
 * @Description:
 */
public class EditTextUtil {
    /**
     * 切换明文密码
     *
     * @param editText 需要切换显示的EditText数据
     *                 isHidden 就是一个明文密文切换的开关
     */
    public static void showPwd(EditText editText, boolean isHidden) {
        if (isHidden) {
            //editText不可见
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            //editText可见
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        editText.postInvalidate();
        //切换后将EditText光标置于末尾
        Spannable charSequence = editText.getText();
        if (charSequence != null) {
            Selection.setSelection(charSequence, charSequence.length());
        }
    }

}
