package com.bromancelabs.firebasechat.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBarUtils {


    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, int message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
