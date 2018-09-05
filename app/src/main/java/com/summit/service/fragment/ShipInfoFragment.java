package com.summit.service.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.summit.service.Activity.LoginActivity;
import com.summit.service.Activity.MenuActivity;
import com.summit.service.GrocerApplication;
import com.summit.service.R;
import com.summit.service.db.SqlDbHelpers;
import com.summit.service.model.order.OrderModel;
import com.summit.service.model.user.UserModel;
import com.summit.service.util.Utils;

public class ShipInfoFragment extends BaseFragment {

    private LinearLayout llContainer;
    private EditText etName;
    private EditText etPhone;
    private EditText etAddress;
    private EditText etInfo;
    private TextView tvOrder;

    private OrderModel orderModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ship_info, container, false);

        initToolbar();
        initComponents(rootView);

        return rootView;
    }

    @Override
    public void initComponents(View rootView) {
        llContainer = (LinearLayout) rootView.findViewById(R.id.fragment_ship_info_llContainer);

        etName = (EditText) rootView.findViewById(R.id.fragment_ship_info_etName);
        etPhone = (EditText) rootView.findViewById(R.id.fragment_ship_info_etPhone);
        etAddress = (EditText) rootView.findViewById(R.id.fragment_ship_info_etAddress);
        etInfo = (EditText) rootView.findViewById(R.id.fragment_ship_info_etInfo);
        tvOrder = (TextView) rootView.findViewById(R.id.fragment_ship_info_tvOrder);

        getData();
    }

    public void initToolbar() {
        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.delivery), true);
    }

    private void  getData(){
        SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(getActivity());
        UserModel userModel = sqlDbHelpers.getUser(GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userName), ""));
        if(userModel != null){
            etName.setText(userModel.getName());
            etPhone.setText(userModel.getPhone());
            etAddress.setText(userModel.getAddress());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if(v == tvOrder){
            //call api save order

        }
    }

    private void submitForm(){
        if (etName.getText().toString().trim().isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_name), true, getActivity());
        } else if (etPhone.getText().toString().trim().isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_phone), true, getActivity());
        } else if (etAddress.getText().toString().trim().isEmpty()) {
            Utils.snackbar(llContainer, getString(R.string.val_enter_address), true, getActivity());
        } else {

            orderModel = getOrderModelFromView();
        }
    }

    private OrderModel getOrderModelFromView(){
        OrderModel orderModel = new OrderModel();
        orderModel.setAddress(etAddress.getText().toString().trim());
        orderModel.setPhone(etPhone.getText().toString().trim());
        orderModel.setInfo(etInfo.getText().toString().trim());
        orderModel.setUserId(GrocerApplication.getmInstance().getSharedPreferences().getInt(getString(R.string.preferances_userId), 0));
        return orderModel;
    }
}
