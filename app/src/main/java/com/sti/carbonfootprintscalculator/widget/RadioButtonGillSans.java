package com.sti.carbonfootprintscalculator.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by mykelneds on 04/03/2017.
 */

@SuppressLint("AppCompatCustomView")
public class RadioButtonGillSans extends RadioButton {
    public RadioButtonGillSans(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gil_ultrasans.ttf"));
    }

    public RadioButtonGillSans(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gil_ultrasans.ttf"));
    }

    public RadioButtonGillSans(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/gil_ultrasans.ttf"));
    }

}
