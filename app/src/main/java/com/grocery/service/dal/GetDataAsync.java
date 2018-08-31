package com.grocery.service.dal;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.grocery.service.asyns.TaskDelegate;
import com.grocery.service.commons.Processor;
import com.grocery.service.helpers.DataApiHelpers;
import com.grocery.service.model.BaseModel;
import com.grocery.service.model.category.CateItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDataAsync extends AsyncTask<String, Void, String> {
    private String _urlApi;
    private TaskDelegate _delegate;

    public GetDataAsync(String urlApi){
        this._urlApi = urlApi;
    }

    public GetDataAsync(String urlApi, TaskDelegate delegate){
        this._urlApi = urlApi;
        this._delegate = delegate;
    }

    public TaskDelegate get_delegate() {
        return _delegate;
    }

    public void set_delegate(TaskDelegate _delegate) {
        this._delegate = _delegate;
    }

    protected String doInBackground(String... params) {
        return DataApiHelpers.GetData(_urlApi);
    }

    @Override
    protected void onPostExecute(String s) {
        if(_delegate != null){
            _delegate.onTaskCompleted(s);
        }
        super.onPostExecute(s);
    }
}
