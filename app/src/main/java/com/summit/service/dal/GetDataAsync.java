package com.summit.service.dal;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.summit.service.asyns.TaskDelegate;
import com.summit.service.commons.Processor;
import com.summit.service.helpers.DataApiHelpers;
import com.summit.service.model.BaseModel;
import com.summit.service.model.category.CateItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDataAsync extends AsyncTask<String, Void, String> {
    private String _urlApi;
    private TaskDelegate _delegate;
    private AlertDialog _loadingDialog;

    public GetDataAsync(String urlApi){
        this._urlApi = urlApi;
    }

    public GetDataAsync(String urlApi, TaskDelegate delegate, AlertDialog loadingDialog){
        this._urlApi = urlApi;
        this._delegate = delegate;
        this._loadingDialog = loadingDialog;
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
    protected void onPreExecute() {
        if (_loadingDialog != null) {
            _loadingDialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        if (_loadingDialog != null && _loadingDialog.isShowing()) {
            _loadingDialog.dismiss();
        }
        if(_delegate != null){
            _delegate.onTaskCompleted(s);
        }
        super.onPostExecute(s);
    }
}
