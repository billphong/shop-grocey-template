package com.grocery.service.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.grocery.service.GrocerApplication;
import com.grocery.service.R;
import com.grocery.service.util.Utils;

import java.util.Locale;


/****************************************************************************
 * SplashActivity
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is user for SplashActivity.
 ***************************************************************************/

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUpLanguage();
        new SplashTask().execute();
    }

    private void setUpLanguage() {

        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            locale = Resources.getSystem().getConfiguration().locale;
        }


        if (GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.prefrence_selected_lang), "").isEmpty()) {
            if (locale.toString().equalsIgnoreCase("en_in")) {
                GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.prefrence_selected_lang), getString(R.string.lang_en));

            } else {
                GrocerApplication.getmInstance().savePreferenceDataString(getString(R.string.prefrence_selected_lang), getString(R.string.lang_al));
            }

        }

         }

    /**
     * AsycTask for setting splash screen by sleep thread for some time
     */
    private class SplashTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(SPLASH_TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            boolean isLogin = GrocerApplication.getmInstance().getSharedPreferences().getBoolean(getString((R.string.preferances_islogin)), false);
            boolean isWelcome = GrocerApplication.getmInstance().getSharedPreferences().getBoolean(getString((R.string.preferances_isWelcom)), false);


            if (isWelcome) {
                if (isLogin) {


                    Intent mLoginIntent = new Intent(SplashActivity.this, MenuActivity.class);
                    startActivity(mLoginIntent);
                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                    finish();


                } else {
                    Intent mMenuIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mMenuIntent);
                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                    finish();
                }


            } else {
                Intent mMenuIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(mMenuIntent);
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                finish();
            }


        }
    }


}
