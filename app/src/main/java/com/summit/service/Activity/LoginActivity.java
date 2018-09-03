package com.summit.service.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.summit.service.GrocerApplication;
import com.summit.service.R;
import com.summit.service.asyns.VolleyCallback;
import com.summit.service.commons.Apis;
import com.summit.service.db.SqlDbHelpers;
import com.summit.service.filters.UserFilter;
import com.summit.service.helpers.DataApiHelpers;
import com.summit.service.model.user.UserModel;
import com.summit.service.util.Utils;

import java.io.IOException;


/**
 * *************************************************************************
 *
 * @ClassdName:LoginActivity
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose: create beautiful login screens using valid email & password. The right way using Material design spec with the assistance of Google’s new design support library.
 * <p/>
 * *************************************************************************
 */


public class LoginActivity extends BaseActivity {


    private LinearLayout llContainer;
    private EditText etEmail;
    private EditText etPassword;
    private RelativeLayout rlLogin;

    private TextView tvForgotPassword;
    private TextView tvRegister;
    private ImageView ivFacebook;
    private ImageView ivGoogle;

    private String email;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initComponents();


    }


    /**
     * Init component
     */

    @Override
    public void initComponents() {

        llContainer = (LinearLayout) findViewById(R.id.activity_login_llContainer);

        rlLogin = (RelativeLayout) findViewById(R.id.activity_login_rlLogin);
        etEmail = (EditText) findViewById(R.id.activity_login_etEmail);
        etPassword = (EditText) findViewById(R.id.activity_login_etPassword);
        tvRegister = (TextView) findViewById(R.id.activity_login_tvSignUp);
        tvForgotPassword = (TextView) findViewById(R.id.activity_login_tvForgotPassword);
        ivFacebook = (ImageView) findViewById(R.id.activity_login_ivFacebook);
        ivGoogle = (ImageView) findViewById(R.id.activity_login_ivGoogle);

        rlLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);


    }


    /**
     * Check Validating field email,password & open Activity
     */

    private void submitForm() {


        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();


        if (email.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_email), true, LoginActivity.this);
        } else if (!Utils.isValidEmail(email)) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_valid_email), true, LoginActivity.this);
        } else if (password.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_password), true, LoginActivity.this);
        }
//        else if (password.length() < 6) {
//            Utils.snackbar(llContainer, getString(R.string.val_password_greter_six), true, LoginActivity.this);
//        }
        else {

            if (Utils.isOnline(LoginActivity.this, true)) {

                UserFilter filter = new UserFilter();
                filter.setEmail(email);
                filter.setPassword(password);
                LoadingDialog().show();
                DataApiHelpers.Post(this, Apis.USER_API, filter, new VolleyCallback(){
                    @Override
                    public void onSuccess(String result){
                        LoadingDialog().dismiss();
                        if(result.equals("null")){
                            Utils.snackbar(llContainer, "Email hoặc mật khẩu không chính xác", true, LoginActivity.this);
                        }else {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                UserModel obj = mapper.readValue(result, UserModel.class);
                                //add or update user to sqllite
                                SqlDbHelpers db = new SqlDbHelpers(LoginActivity.this);
                                UserModel existUser = db.getUser(obj.getEmail());
                                if(existUser != null){
                                    db.updateUser(obj);
                                }else {
                                    db.addUser(obj);
                                }

                                //
                                GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), true);
                                GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userName), email);
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        LoadingDialog().dismiss();
                    }
                });

            } else {
                Utils.snackbar(llContainer, "" + getString(R.string.check_connection), true, LoginActivity.this);

            }
        }

    }

    /**
     * OnClick listener :  Login & Register & Forgot password & Facebook & google click listener
     */


    @Override
    public void onClick(View v) {

        Utils.hideKeyboard(LoginActivity.this);

        if (v == rlLogin) {
            submitForm();
        } else if (v == tvForgotPassword) {
            Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
        } else if (v == tvRegister) {
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
        } else if (v == ivFacebook) {
            Toast.makeText(getApplicationContext(), getString(R.string.login_with_facebook), Toast.LENGTH_SHORT).show();
        } else if (v == ivGoogle) {
            Toast.makeText(getApplicationContext(), getString(R.string.login_with_google_plus), Toast.LENGTH_SHORT).show();
        }


    }


}
