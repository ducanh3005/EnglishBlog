package com.tune.englishblog.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.tune.englishblog.R;

public class UiUtils {

    static public void updateHideReadButton(FloatingActionButton drawerHideReadButton) {
        if (drawerHideReadButton != null) {
            if (PrefUtil.get(drawerHideReadButton.getContext(), PrefUtil.SHOW_READ, true)) {
                drawerHideReadButton.setColorNormalResId(getAttrResource(drawerHideReadButton.getContext(), R.attr.colorPrimary, R.color.color_primary));
            } else {
                drawerHideReadButton.setColorNormalResId(R.color.floating_action_button_disabled);
            }
        }
    }

    static public void displayHideReadButtonAction(Context context) {
        if (PrefUtil.get(context, PrefUtil.SHOW_READ, true)) {
            Toast.makeText(context, R.string.context_menu_hide_read, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.context_menu_show_read, Toast.LENGTH_SHORT).show();
        }
    }


    static public int getAttrResource(Context context, int attrId, int defValue) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attrId});
        int result = a.getResourceId(0, defValue);
        a.recycle();
        return result;
    }
}
