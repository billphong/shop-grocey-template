package com.summit.cherrity.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.summit.cherrity.Activity.MenuActivity;
import com.summit.cherrity.Activity.RegisterActivity;
import com.summit.cherrity.GrocerApplication;
import com.summit.cherrity.R;
import com.summit.cherrity.asyns.VolleyCallback;
import com.summit.cherrity.asyns.VolleyJsonCallback;
import com.summit.cherrity.commons.Apis;
import com.summit.cherrity.db.SqlDbHelpers;
import com.summit.cherrity.helpers.DataApiHelpers;
import com.summit.cherrity.model.order.OrderModel;
import com.summit.cherrity.model.order.ProductOrderModel;
import com.summit.cherrity.model.user.UserModel;
import com.summit.cherrity.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ShipInfoFragment extends BaseFragment {

    private LinearLayout llContainer;
    private EditText etName;
    private EditText etPhone;
    private EditText etAddress;
    private EditText etInfo;
    private TextView tvOrder;

    private OrderModel orderModel;
    private List<ProductOrderModel> lsProductOrderMode;
    private int userId;

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

        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        getData();
    }

    public void initToolbar() {
        MenuActivity menuActivity = ((MenuActivity) getActivity());
        if(menuActivity != null) {
            menuActivity.setUpToolbar(getString(R.string.delivery), true);
        }
    }

    private void  getData(){
        SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(getActivity());
        UserModel userModel = sqlDbHelpers.getUser(GrocerApplication.getmInstance().getSharedPreferences().getString(getString(R.string.preferances_userName), ""));
        if(userModel != null){
            etName.setText(userModel.getName());
            etPhone.setText(userModel.getPhone());
            etAddress.setText(userModel.getAddress());
        }
        userId = GrocerApplication.getmInstance().getSharedPreferences().getInt(getString(R.string.preferances_userId), 0);
    }

    private void submitForm(){
        if (etName.getText().toString().trim().isEmpty()) {
            //Utils.snackbar(llContainer, getString(R.string.val_enter_name), true, getActivity());
            Toast.makeText(getActivity(), getString(R.string.val_enter_name), Toast.LENGTH_SHORT).show();
        } else if (etPhone.getText().toString().trim().isEmpty()) {
            //Utils.snackbar(llContainer, getString(R.string.val_enter_phone), true, getActivity());
            Toast.makeText(getActivity(), getString(R.string.val_enter_phone), Toast.LENGTH_SHORT).show();
        } else if(!Utils.isPhone(etPhone.getText().toString().trim())){
            Toast.makeText(getActivity(), getString(R.string.invalid_phone), Toast.LENGTH_SHORT).show();
        }
        else if (etAddress.getText().toString().trim().isEmpty()) {
            //Utils.snackbar(llContainer, getString(R.string.val_enter_address), true, getActivity());
            Toast.makeText(getActivity(), getString(R.string.val_enter_address), Toast.LENGTH_SHORT).show();
        } else {
            orderModel = getOrderModelFromView();
            lsProductOrderMode = new SqlDbHelpers(getActivity()).getProductOrderByUserId(userId);

            try {
                ObjectMapper mapper = new ObjectMapper();
                JSONObject json = new JSONObject();
                json.put("order", new JSONObject(mapper.writeValueAsString(orderModel)));
                json.put("productOrder", new JSONArray(mapper.writeValueAsString(lsProductOrderMode)));

                LoadingDialog().show();
                DataApiHelpers.PostJson(getActivity().getApplicationContext(), Apis.ORDER_API, json, new VolleyJsonCallback() {

                    @Override
                    public void onSuccess(Object result) {
                        LoadingDialog().dismiss();
                        //getFragmentManager().popBackStack(MainFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        try {
                            Utils.addNextFragment(getActivity(), new MainFragment(), ShipInfoFragment.this, false, false);
                            Toast.makeText(getActivity(), R.string.your_order_successfully, Toast.LENGTH_SHORT).show();
                            Utils.clearBackStack(getActivity(), ShipInfoFragment.this);
                            SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(getActivity());
                            sqlDbHelpers.deleteAllProductOrder();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        LoadingDialog().dismiss();
                        if(error != null){
                            String strErr = error.getMessage() == null ? getString(R.string.error_anonymous) : error.getMessage();
                            Toast.makeText(getActivity(), strErr, Toast.LENGTH_SHORT).show();
                            //Utils.snackbar(llContainer, error.getMessage(), true, getActivity());
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private OrderModel getOrderModelFromView(){
        OrderModel orderModel = new OrderModel();
        orderModel.setShipAddress(etAddress.getText().toString().trim());
        orderModel.setShipPhone(etPhone.getText().toString().trim());
        orderModel.setShipInfo(etInfo.getText().toString().trim());
        orderModel.setUserID(userId);
        return orderModel;
    }
}
