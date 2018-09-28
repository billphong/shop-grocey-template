package com.summit.cherrity.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.summit.cherrity.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Build;


/**
 * @purpose commonly used functions
 * @purpose
 */
public class Utils {


    /**
     * @param context
     * @param message
     * @return
     * @description use to check internet network connection if network
     * connection not available than alert for open network
     * settings
     */
    public static boolean isOnline(final Activity context, boolean message) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        if (message) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name_new));
            dialog.setCancelable(false);
            dialog.setMessage(context.getString(R.string.check_connection));
            dialog.setPositiveButton(context.getString(R.string.settings), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                }
            });
            dialog.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            dialog.show();

            return false;
        }
        return false;
    }


    /**
     * @param inputEmail
     * @return
     * @purpose validate email
     */
    public final static boolean isValidEmail(CharSequence inputEmail) {
        if (inputEmail == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
        }
    }

    public static boolean isPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches() || (number.length() != 10 && number.length() != 11)) {
            return false;
        }
        return true;
    }

    public static String doubleToStringNoDecimal(double d) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);;
        formatter.applyPattern("#.###");
        return formatter.format(d);
    }

    public static String Spacer(String number){
        StringBuilder strB = new StringBuilder();
        strB.append(number);
        int Three = 0;

        for(int i=number.length();i>0;i--){
            Three++;
            if(Three == 3 && i > 0){
                strB.insert(i-1, ".");
                Three = 0;
            }
        }
        return strB.toString();
    }
    /**
     * Hide KeyBoard Using CurrentFocus
     *
     * @return
     */
    public static void hideKeyboard(Context mContext) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        View focus = ((Activity) mContext).getCurrentFocus();

        if (focus != null) {

            inputManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeyboard(Context mContext, IBinder windowToken){
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(windowToken != null){
            inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static void snackbar(final View view, final String msg, boolean isSnakbar, Context mContext) {

        try {
            if (isSnakbar) {
                Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
                snack.getView().setBackgroundColor(Color.parseColor("#84af2a"));
                View viewNew = snack.getView();
                TextView tv = (TextView) viewNew.findViewById(android.support.design.R.id.snackbar_text);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                snack.show();
            } else {
                Toast.makeText(mContext, "" + msg, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @param mActivity
     * @param targetedFragment
     * @param shooterFragment
     * @purpose for call targeted fragment from current fragment
     */

    public static void addNextFragment(Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, boolean isDownToUp) {
        addNextFragment(mActivity, targetedFragment, shooterFragment, isDownToUp, true);
    }

    public static void addNextFragment(Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, boolean isDownToUp, boolean isAddToBackStack) {
        FragmentTransaction transaction = null;//mActivity.getFragmentManager().beginTransaction();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            transaction = mActivity.getFragmentManager().beginTransaction();
//        else
//            transaction = shooterFragment.getChildFragmentManager().beginTransaction();

        if (isDownToUp) {
            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
                    R.animator.slide_fragment_vertical_right_out);

        } else {
            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
                    R.animator.slide_fragment_horizontal_right_out);

        }


        transaction.add(R.id.flcontainer, targetedFragment, targetedFragment.getClass().getSimpleName());

        transaction.hide(shooterFragment);
        if(isAddToBackStack) {
            transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    public static void clearBackStack(Activity mActivity, Fragment currentFragment){
        FragmentManager manager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            manager = mActivity.getFragmentManager();
        else if(currentFragment != null)
            manager = currentFragment.getChildFragmentManager();

        while (manager != null && manager.getBackStackEntryCount() > 0){
            manager.popBackStackImmediate();
        }
    }
}
