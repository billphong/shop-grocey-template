package com.grocery.service.customecomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.grocery.service.R;
import com.grocery.service.util.FontCache;


public class CustomEdittext extends android.support.v7.widget.AppCompatEditText {

    private Typeface fontType;

    /**
     * Set the font according to style
     *
     * @param context context
     * @param attrs   style attribute like bold, italic
     */


    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        String fontName = ta.getString(R.styleable.CustomEditText_font_name_et);
        if (fontName != null) {
            final Typeface font = FontCache.getInstance().getFont(context, "fonts/" + fontName);
            setTypeface(font);
        }
        ta.recycle();
    }


}