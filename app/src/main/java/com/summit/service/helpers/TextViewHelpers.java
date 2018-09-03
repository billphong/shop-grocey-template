package com.summit.service.helpers;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

public class TextViewHelpers {
    public static void setTextHtml(TextView textView, String html){
        PicassoImageGetter imageGetter = new PicassoImageGetter(textView);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            textView.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, imageGetter, null));
        }else{
            textView.setText(Html.fromHtml(html, imageGetter, null));
        }
    }
}
