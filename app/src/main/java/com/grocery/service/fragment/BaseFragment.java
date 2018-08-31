package com.grocery.service.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.grocery.service.R;
import com.grocery.service.util.Utils;

import dmax.dialog.SpotsDialog;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private AlertDialog loadingDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        return rootView;
    }


    public abstract void initComponents(View rootView);


    @Override
    public void onClick(View v) {

    }

    protected AlertDialog LoadingDialog(){
        if(loadingDialog == null){
            loadingDialog =  new SpotsDialog.Builder()
                    .setContext(getActivity())
                    .setCancelable(false)
                    .build();
        }
        return loadingDialog;
    }

}
