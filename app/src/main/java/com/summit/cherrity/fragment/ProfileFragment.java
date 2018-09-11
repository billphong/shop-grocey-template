package com.summit.cherrity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.summit.cherrity.Activity.MenuActivity;
import com.summit.cherrity.GrocerApplication;
import com.summit.cherrity.R;
import com.summit.cherrity.db.SqlDbHelpers;
import com.summit.cherrity.model.user.UserModel;

/**
 * *************************************************************************
 *
 * @ClassdName:ProfileFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display user profile with userName,Email,Profile image, PhoneNumber
 * <p/>
 * *************************************************************************
 */


public class ProfileFragment extends BaseFragment {

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvAddress;
    private ImageView ivProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initToolbar();
        setHasOptionsMenu(true);
        initComponents(rootView);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    /**
     * init Components
     */


    @Override
    public void initComponents(View rootView) {

        tvName = (TextView) rootView.findViewById(R.id.fragment_profile_tvName);
        tvEmail = (TextView) rootView.findViewById(R.id.fragment_profile_tvEmail);
        tvPhone = (TextView) rootView.findViewById(R.id.fragment_profile_tvPhone);
        tvAddress = (TextView) rootView.findViewById(R.id.fragment_profile_tvAddress);
        ivProfile = (ImageView) rootView.findViewById(R.id.fragment_profile_ivProfilePic);

        SqlDbHelpers db = new SqlDbHelpers(getActivity());
        String email = GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userName), "");
        UserModel existUser = db.getUser(email);
        if(existUser != null){
            tvName.setText(existUser.getName());
            tvEmail.setText(existUser.getEmail());
            tvPhone.setText(existUser.getPhone());
            tvAddress.setText(existUser.getAddress());
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * SetUp Toolbar & title
     */
    public void initToolbar() {
        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.profile), false);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            try {
                initToolbar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
