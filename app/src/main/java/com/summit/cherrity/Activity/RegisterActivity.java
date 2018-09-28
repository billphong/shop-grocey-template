package com.summit.cherrity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.summit.cherrity.GrocerApplication;
import com.summit.cherrity.R;
import com.summit.cherrity.asyns.VolleyCallback;
import com.summit.cherrity.commons.Apis;
import com.summit.cherrity.db.SqlDbHelpers;
import com.summit.cherrity.helpers.DataApiHelpers;
import com.summit.cherrity.model.user.UserModel;
import com.summit.cherrity.util.Utils;

import java.io.IOException;
import java.util.Date;


/**
 * *************************************************************************
 *
 * @ClassdName:LoginActivity
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose: create beautiful signup screens the right way using Material design spec with the assistance of Google’s new design support library.
 * <p/>
 * *************************************************************************
 */


public class RegisterActivity extends BaseActivity {

    private LinearLayout llContainer;
    private RelativeLayout rlSignUp;
    private EditText etPhoneNumber;
    private EditText etEmail;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etConfrimPassWord;
    private EditText etAddress;
    private TextView tvSignIn;


    private String phoneNumber;
    private String emailAdd;
    private String userName;
    private String passWord;
    private String confrimPassWord;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        initComponents();
    }

    /**
     * Init component
     */


    @Override
    public void initComponents() {

        llContainer = (LinearLayout) findViewById(R.id.activity_register_llContainer);

        rlSignUp = (RelativeLayout) findViewById(R.id.activity_register_rlSignUp);
        etPhoneNumber = (EditText) findViewById(R.id.activity_register_etPhone);
        etEmail = (EditText) findViewById(R.id.activity_register_etEmail);
        etUserName = (EditText) findViewById(R.id.activity_register_etUsername);
        etPassword = (EditText) findViewById(R.id.activity_register_etPassword);
        etConfrimPassWord = (EditText) findViewById(R.id.activity_register_etConfrimPassword);
        etPhoneNumber = (EditText) findViewById(R.id.activity_register_etPhone);
        etAddress = (EditText) findViewById(R.id.activity_register_etAddress);
        tvSignIn = (TextView) findViewById(R.id.activity_register_tvSignIn);

        rlSignUp.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);

    }


    /**
     * Validating
     */

    private void submitForm() {

        Utils.hideKeyboard(RegisterActivity.this, llContainer.getWindowToken());

        userName = etUserName.getText().toString().trim();
        emailAdd = etEmail.getText().toString().trim();
        phoneNumber = etPhoneNumber.getText().toString().trim();
        passWord = etPassword.getText().toString().trim();
        confrimPassWord = etConfrimPassWord.getText().toString().trim();
        address = etAddress.getText().toString().trim();


        if (Utils.isOnline(RegisterActivity.this, true)) {
            if (userName.isEmpty()) {
                Utils.snackbar(llContainer, getString(R.string.val_enter_user_name), true, RegisterActivity.this);
            } else if (emailAdd.isEmpty()) {
                Utils.snackbar(llContainer, getString(R.string.val_enter_email), true, RegisterActivity.this);
            } else if (!Utils.isValidEmail(emailAdd)) {
                Utils.snackbar(llContainer, getString(R.string.val_enter_valid_email), true, RegisterActivity.this);
            } else if (passWord.length() < 6) {
                Utils.snackbar(llContainer, getString(R.string.val_password_greter_six), true, RegisterActivity.this);
            } else if (confrimPassWord.isEmpty()) {
                Utils.snackbar(llContainer, getString(R.string.val_enter_conf_password), true, RegisterActivity.this);
            } else if (confrimPassWord.length() < 6) {
                Utils.snackbar(llContainer, getString(R.string.val_conf_password_greter_six), true, RegisterActivity.this);
            } else if (!passWord.equals(confrimPassWord)) {
                Utils.snackbar(llContainer, getString(R.string.val_password_confirm_password_not_match), true, RegisterActivity.this);

            } else if (phoneNumber.isEmpty()) {
                Utils.snackbar(llContainer, getString(R.string.val_enter_phone), true, RegisterActivity.this);
            } else if(!Utils.isPhone(phoneNumber)){
                Utils.snackbar(llContainer, getString(R.string.invalid_phone), true, RegisterActivity.this);
            }

            else if (address.isEmpty()) {
                Utils.snackbar(llContainer, getString(R.string.val_enter_phone), true, RegisterActivity.this);

            }
            else {
                LoadingDialog().show();
                final UserModel userModel = new UserModel();
                userModel.setAddress(address);
                userModel.setPhone(phoneNumber);
                userModel.setEmail(emailAdd);
                userModel.setName(userName);
                userModel.setPassword(passWord);
                userModel.setBirthday(new Date());
                DataApiHelpers.Post(this, Apis.USER_REGISTER_API, userModel, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LoadingDialog().dismiss();
                        Log.d("Register", result);
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                            UserModel obj = mapper.readValue(result, UserModel.class);
                            SqlDbHelpers db = new SqlDbHelpers(RegisterActivity.this);
                            db.addUser(obj);
                            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
                            Utils.hideKeyboard(RegisterActivity.this);
                            GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), true);
                            GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userName), emailAdd);
                            GrocerApplication.getmInstance().savePreferenceDataInt(getString(R.string.preferances_userId), obj.getID());
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Utils.snackbar(llContainer, result, true, RegisterActivity.this);
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {
                        LoadingDialog().dismiss();
                        if(error != null){
                            String strErr = error.getMessage() == null ? getString(R.string.error_anonymous) : error.getMessage();
                            Utils.snackbar(llContainer, strErr, true, RegisterActivity.this);
                        }
                    }
                });

            }
        } else {
            Utils.snackbar(llContainer, "" + getString(R.string.check_connection), true, RegisterActivity.this);

        }

    }


    @Override
    public void onClick(View v) {

        Utils.hideKeyboard(RegisterActivity.this);

        if (v == tvSignIn) {
            Utils.hideKeyboard(RegisterActivity.this);
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
            finish();
        } else if (v == rlSignUp) {

            submitForm();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
        Utils.hideKeyboard(RegisterActivity.this);
        finish();
    }

}
