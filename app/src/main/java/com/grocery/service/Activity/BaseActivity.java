package com.grocery.service.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.grocery.service.R;

import dmax.dialog.SpotsDialog;


/**
 * *************************************************************************
 *
 * @ClassdName:BaseActivity
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is BaseActivity.
 * <p/>
 * *************************************************************************
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog loadingDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }

    public abstract void initComponents();

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {


    }

    protected AlertDialog LoadingDialog(){
        if(loadingDialog == null){
            loadingDialog =  new SpotsDialog.Builder()
                    .setContext(this)
                    .setCancelable(false)
                    .build();
        }
        return loadingDialog;
    }
}