package com.grocery.service.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.grocery.service.Activity.MenuActivity;
import com.grocery.service.R;
import com.grocery.service.util.Utils;


/**
 * *************************************************************************
 *
 * @ClassdName:AddAddressFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for add shipping address with field fullName,phone,email & address
 * <p/>
 * *************************************************************************
 */



public class AddAddressFragment extends BaseFragment {


    private LinearLayout llContainer;
    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etAddressLine1;
    private EditText etAddressLine2;
    private RelativeLayout rlAdd;

    private String fullName;
    private String phone;
    private String email;
    private String addressLineOne;
    private String addressLineTwo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_address, container, false);

        initToolbar();
        initComponents(rootView);
        return rootView;

    }



    /**
     * SetUp Toolbar & title
     */

    public void initToolbar() {
        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.add_shipping_address), true);
    }



    /**
     * init Components
     */

    @Override
    public void initComponents(View rootView) {


        llContainer = rootView.findViewById(R.id.fragment_add_address_llcontainer);
        etName = rootView.findViewById(R.id.fragment_add_address_edAddressFullName);
        etEmail = rootView.findViewById(R.id.fragment_add_address_edEmail);
        etPhone = rootView.findViewById(R.id.fragment_add_address_edMobile);
        etAddressLine1 = rootView.findViewById(R.id.fragment_add_address_edAddressLineOne);
        etAddressLine2 = rootView.findViewById(R.id.fragment_add_address_edAddressLineTwo);
        rlAdd = rootView.findViewById(R.id.fragment_add_address_rlAdd);


        rlAdd.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == rlAdd) {
            addAddress();
        }
    }

    /**
     * Validating
     */

    private void addAddress() {


        email = etEmail.getText().toString().trim();
        fullName = etName.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        addressLineOne = etAddressLine1.getText().toString().trim();
        addressLineTwo = etAddressLine2.getText().toString().trim();

        if (fullName.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_name), true, getActivity());
        } else if (phone.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_mobile_number), true, getActivity());
        } else if (email.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_email), true, getActivity());
        } else if (!Utils.isValidEmail(email)) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_email), true, getActivity());
        } else if (addressLineOne.isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_addressline), true, getActivity());
        } else {

            Toast.makeText(getActivity(), R.string.address_added_success, Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();

        }

    }


}
