package com.summit.cherrity.asyns;

import com.android.volley.VolleyError;

public interface VolleyJsonCallback {
    void onSuccess(Object result);
    void onError(VolleyError error);
}
