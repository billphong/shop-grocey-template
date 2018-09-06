package com.summit.service.asyns;

import com.android.volley.VolleyError;

public interface VolleyJsonCallback {
    void onSuccess(Object result);
    void onError(VolleyError error);
}
