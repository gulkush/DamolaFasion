package com.pathwaymedia.valisimofashions;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by gulshanbudhwani on 26/02/17.
 */

public class FontAwesomeTextView extends TextView {
    public FontAwesomeTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public FontAwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);


    }

    public FontAwesomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);

    }

    private void applyCustomFont(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
        setTypeface(typeface);
    }
}
