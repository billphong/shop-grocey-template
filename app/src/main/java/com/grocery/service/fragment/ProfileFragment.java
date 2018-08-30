package com.grocery.service.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grocery.service.Activity.MenuActivity;
import com.grocery.service.R;

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
        ivProfile = (ImageView) rootView.findViewById(R.id.fragment_profile_ivProfilePic);

        tvName.setText(R.string.leo_jorge);
        tvEmail.setText(R.string.lio_jorge_gmail);
        tvPhone.setText(R.string.temp_number);

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
