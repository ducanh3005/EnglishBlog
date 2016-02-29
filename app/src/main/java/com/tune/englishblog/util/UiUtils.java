package com.tune.englishblog.util;

import android.content.Context;
import android.content.res.TypedArray;

public class UiUtils {


    static public int getAttrResource(Context context, int attrId, int defValue) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attrId});
        int result = a.getResourceId(0, defValue);
        a.recycle();
        return result;
    }
}
