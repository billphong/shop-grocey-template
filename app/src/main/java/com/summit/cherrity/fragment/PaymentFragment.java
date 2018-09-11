package com.summit.cherrity.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.summit.cherrity.Activity.MenuActivity;
import com.summit.cherrity.R;

/**
 * *************************************************************************
 *
 * @ClassdName:PaymentFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display Payment Confirmation with field Delivery DateTime & Address,Total Items & Amount
 * <p/>
 * *************************************************************************
 */



public class PaymentFragment extends BaseFragment {


    private LinearLayout llContainer;
    private TextView tvName;
    private TextView tvDateTime;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvAmount;
    private TextView tvCharge;
    private TextView tvTotalAmount;
    private TextView tvDeliveryCharge;
    private RelativeLayout rlOrderNow;

    private String fullName;
    private String phone;
    private String email;
    private String addressLineOne;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        initToolbar();
        initComponents(rootView);

        return rootView;

    }

    /**
     * SetUp Toolbar & title
     */
    public void initToolbar() {
        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.pro_payment), true);
    }


    /**
     * init Components
     */

    @Override
    public void initComponents(View rootView) {


        llContainer = rootView.findViewById(R.id.fragment_payment_llContainer);
        tvName = rootView.findViewById(R.id.fragment_payment_tvName);
        tvCharge = rootView.findViewById(R.id.fragment_payment_tvDeliveryCharge);
        tvPhone = rootView.findViewById(R.id.fragment_payment_tvMobile);
        tvAddress = rootView.findViewById(R.id.fragment_payment_tvAddress);
        tvAmount = rootView.findViewById(R.id.fragment_payment_tvAmount);
        tvTotalAmount = rootView.findViewById(R.id.fragment_payment_tvTotalAmount);
        rlOrderNow = rootView.findViewById(R.id.fragment_payment_rlOrder);


        rlOrderNow.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == rlOrderNow) {

            Toast.makeText(getActivity(), R.string.your_order_successfully, Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            initToolbar();
        }
    }

}
