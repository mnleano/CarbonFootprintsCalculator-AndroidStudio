package com.sti.carbonfootprintscalculator.builder;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mykelneds on 05/03/2017.
 */

public class ToastBuilder {

    public static void createShortToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static void createLongToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }
}
