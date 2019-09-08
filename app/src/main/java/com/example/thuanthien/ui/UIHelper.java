package com.example.thuanthien.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

public class UIHelper {
    public static void hideSoftKeyboard(Window wd, View view) {
        int flag = android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        if (wd != null) {
            wd.setSoftInputMode(wd.getAttributes().softInputMode & ~flag);
        }
        if (view == null) return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) return;
        final IBinder windowToken = view.getWindowToken();
        if (!imm.hideSoftInputFromWindow(windowToken, 0)) {
            if (!imm.isActive()) {
                return;
            }
            Activity activity = scanForActivity(view.getContext());
            if (activity != null) {
                Window window = activity.getWindow();
                window.setSoftInputMode(window.getAttributes().softInputMode & ~flag);
            }
        }
        System.out.printf("Hide KB called");
    }

    public static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }
}
