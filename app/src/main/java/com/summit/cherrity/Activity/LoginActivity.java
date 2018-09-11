package com.summit.cherrity.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.summit.cherrity.GrocerApplication;
import com.summit.cherrity.R;
import com.summit.cherrity.asyns.VolleyCallback;
import com.summit.cherrity.commons.Apis;
import com.summit.cherrity.db.SqlDbHelpers;
import com.summit.cherrity.filters.UserFilter;
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
 * @purpose: create beautiful login screens using valid email & password. The right way using Material design spec with the assistance of Google’s new design support library.
 * <p/>
 * *************************************************************************
 */


public class LoginActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static final String TAG = LoginActivity.class.getSimpleName();
    private LinearLayout llContainer;
    private EditText etEmail;
    private EditText etPassword;
    private RelativeLayout rlLogin;

    private TextView tvForgotPassword;
    private TextView tvRegister;
    private ImageView ivFacebook;
    private SignInButton ivGoogle;

    private String email;
    private String password;

    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initComponents();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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
        ivGoogle = (SignInButton) findViewById(R.id.activity_login_ivGoogle);

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
                                GrocerApplication.getmInstance().savePreferenceDataInt(getString(R.string.preferances_userId), obj.getID());
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
                        if(error != null){
                            String strErr = error.getMessage() == null ? getString(R.string.error_anonymous) : error.getMessage();
                            Utils.snackbar(llContainer, strErr, true, LoginActivity.this);
                        }
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
            signIn();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            final String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            UserModel userModel = new UserModel();
            userModel.setAddress("");
            userModel.setPhone("");
            userModel.setEmail(email);
            userModel.setName(personName);
            userModel.setPassword("");
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
                        SqlDbHelpers db = new SqlDbHelpers(LoginActivity.this);
                        db.addUser(obj);
                        overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
                        Utils.hideKeyboard(LoginActivity.this);
                        GrocerApplication.getmInstance().savePreferenceDataBoolean(getString(R.string.preferances_islogin), true);
                        GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.preferances_userName), email);
                        GrocerApplication.getmInstance().savePreferenceDataInt(getString(R.string.preferances_userId), obj.getID());
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Utils.snackbar(llContainer, result, true, LoginActivity.this);
                    }

                }

                @Override
                public void onError(VolleyError error) {
                    LoadingDialog().dismiss();
                    if(error != null){
                        String strErr = error.getMessage() == null ? getString(R.string.error_anonymous) : error.getMessage();
                        Utils.snackbar(llContainer, strErr, true, LoginActivity.this);
                    }
                }
            });

        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {

        }
        catch(Exception ex){
            String exception = ex.getLocalizedMessage();
            String exceptionString = ex.toString();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
