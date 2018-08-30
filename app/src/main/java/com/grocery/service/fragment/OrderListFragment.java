package com.grocery.service.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.service.Activity.MenuActivity;
import com.grocery.service.R;
import com.grocery.service.adapter.OrderListAdapter;
import com.grocery.service.data.TempListData;
import com.grocery.service.model.order.OrderListModel;
import com.grocery.service.util.Utils;

import java.util.List;

/**
 * *************************************************************************
 *
 * @ClassdName:OrderListFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display order item list with field OrderId,ProductName,Total Items,Total Amount,DateTime
 * <p/>
 * *************************************************************************
 */


public class OrderListFragment extends BaseFragment implements OrderListAdapter.OnItemClickListener {

    //Declaration
    private RecyclerView rvProductList;
    private LinearLayoutManager mLayoutManager;
    private OrderListAdapter productListAdapter;
    private List<OrderListModel> OrderListModelNewArrayList;
    private MenuItem item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_order_list, container, false);
        initToolbar();
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


        rvProductList = (RecyclerView) rootView.findViewById(R.id.fragment_order_list_rvProductList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvProductList.setLayoutManager(mLayoutManager);
        getListData();


    }

    /**
     * get Order list data and setUp adapter
     */

    private void getListData() {

        TempListData tempListData=new TempListData();
        OrderListModelNewArrayList = tempListData.getOrderList();

        productListAdapter = new OrderListAdapter(getActivity(), OrderListModelNewArrayList, OrderListFragment.this);
        rvProductList.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(this);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.menu_left);
        item.setVisible(true);
        item.setIcon(R.drawable.cart);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Utils.hideKeyboard(getActivity());
                CartListFragment fragmentProductDetails = new CartListFragment();
                fragmentProductDetails.setTargetFragment(OrderListFragment.this, 222);
                Utils.addNextFragment(getActivity(), fragmentProductDetails, OrderListFragment.this, false);

                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {


    }

    /**
     * SetUp Toolbar & title
     */
    public void initToolbar() {
        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_my_order), false);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            try {
                initToolbar();
            } catch (Exception e) {

            }


        }
    }

    /**
     *  RecyclerView Item click listener
     */


    @Override
    public void onItemClick(View view, OrderListModel viewModel) {


    }


}
