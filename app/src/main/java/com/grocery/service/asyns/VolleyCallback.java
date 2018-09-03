package com.grocery.service.asyns;

import com.android.volley.VolleyError;

public interface VolleyCallback {
    void onSuccess(String result);
    void onError(VolleyError error);
}
