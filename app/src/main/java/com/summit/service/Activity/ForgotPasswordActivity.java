package com.summit.service.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.summit.service.R;
import com.summit.service.asyns.TaskDelegate;
import com.summit.service.asyns.VolleyJsonCallback;
import com.summit.service.commons.Apis;
import com.summit.service.dal.GetDataAsync;
import com.summit.service.helpers.DataApiHelpers;
import com.summit.service.model.JsonReturn;
import com.summit.service.util.Utils;

import org.json.JSONObject;

import java.io.IOException;


/**
 * *************************************************************************
 *
 * @ClassdName:ForgotPasswordActivity
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to forgot password.
 * <p/>
 * *************************************************************************
 */



public class ForgotPasswordActivity extends BaseActivity {

    private LinearLayout llContainer;
    private EditText etEmail;

    private RelativeLayout rlSubmit;
    private TextView llSignIn;

    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initComponents();


    }

    /**
     * Init component
     */


    @Override
    public void initComponents() {

        llContainer = (LinearLayout) findViewById(R.id.activity_login_llContainer);
        llSignIn = (TextView) findViewById(R.id.activity_forgotpassword_tvSignIn);

        rlSubmit = (RelativeLayout) findViewById(R.id.activity_forgotpassword_rlSubmit);
        etEmail = (EditText) findViewById(R.id.activity_login_etEmail);


        rlSubmit.setOnClickListener(this);
        llSignIn.setOnClickListener(this);


    }


    /**
     * Validating
     */

    private void submitForm() {


        email = etEmail.getText().toString().trim();


        if (email.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_email), true, ForgotPasswordActivity.this);
        } else if (!Utils.isValidEmail(email)) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_valid_email), true, ForgotPasswordActivity.this);
        } else {
            LoadingDialog().show();
            String api = String.format(Apis.USER_FORGOT_PASSOWRD_API, email);
            DataApiHelpers.GetJson(this, api, null, new VolleyJsonCallback() {
                @Override
                public void onSuccess(Object result) {
                    LoadingDialog().dismiss();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                    try {
                        JsonReturn obj = mapper.readValue(result.toString(), JsonReturn.class);
                        if(obj != null){
                            if(obj.success){
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                                Toast.makeText(ForgotPasswordActivity.this, obj.message, Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Utils.snackbar(llContainer, obj.message, true, ForgotPasswordActivity.this);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    LoadingDialog().dismiss();
                    if(error != null){
                        String strErr = error.getMessage() == null ? getString(R.string.error_anonymous) : error.getMessage();
                        Utils.snackbar(llContainer, strErr, true, ForgotPasswordActivity.this);
                    }
                }
            });

        }

    }


    @Override
    public void onClick(View v) {

        Utils.hideKeyboard(ForgotPasswordActivity.this);


        if (v == rlSubmit) {
            submitForm();
        }

        if (v == llSignIn) {

            finish();
            Utils.hideKeyboard(ForgotPasswordActivity.this);
            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
        Utils.hideKeyboard(ForgotPasswordActivity.this);
        finish();
    }

}
