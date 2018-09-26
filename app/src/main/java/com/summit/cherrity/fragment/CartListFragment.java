package com.summit.cherrity.fragment;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.summit.cherrity.Activity.LoginActivity;
import com.summit.cherrity.Activity.MenuActivity;
import com.summit.cherrity.GrocerApplication;
import com.summit.cherrity.R;
import com.summit.cherrity.adapter.CartListAdapter;
import com.summit.cherrity.db.SqlDbHelpers;
import com.summit.cherrity.model.order.ProductOrderModel;
import com.summit.cherrity.util.Utils;

import java.util.List;


/**
 * *************************************************************************
 *
 * @ClassdName:CartListFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display cart list items & add and remove item in cartList
 * <p/>
 * *************************************************************************
 */

public class CartListFragment extends BaseFragment {

    //Declaration
    private RecyclerView rvProductList;
    private TextView tvTotalPrice;
    private RelativeLayout rlCheckOut;
    private RelativeLayout rlEmpty;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mLayoutTotalMoney;
    private CartListAdapter productListAdapter;
    private List<ProductOrderModel> productListModelArrayList;
    private MenuItem item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_cart_list, container, false);
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
        rlCheckOut = (RelativeLayout) rootView.findViewById(R.id.fragment_order_list_rlCheckOut);
        rlEmpty = (RelativeLayout) rootView.findViewById(R.id.fragment_order_list_rlEmpty);
        tvTotalPrice = (TextView) rootView.findViewById(R.id.fragment_order_list_tvTotalKg);
        mLayoutTotalMoney = (LinearLayout) rootView.findViewById(R.id.fragment_order_list_rlTotalCart);
        mLayoutManager = new LinearLayoutManager(getActivity());

        rvProductList.setLayoutManager(mLayoutManager);

        rlCheckOut.setOnClickListener(this);

        getListData();
        tvTotalPrice.setText(getTotalPrice() + " " + getString(R.string.dolar));
    }

    private int getTotalPrice() {
        int total = 0;
        if(productListModelArrayList != null && productListModelArrayList.size() > 0) {
            for (int i = 0; i < productListModelArrayList.size(); i++) {
                int totalQty = productListModelArrayList.get(i).getNumber();
                int price = productListModelArrayList.get(i).getPrice();
                int totalPrice = totalQty * price;
                total = total + totalPrice;
            }
        }

        return total;
    }

    /**
     * get Cart list data and setUp adapter
     */

    private void getListData() {

//        TempListData tempListData = new TempListData();
//        productListModelArrayList = tempListData.getCartList();
        SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(getActivity());
        productListModelArrayList = sqlDbHelpers.getProductOrderByUserId(GrocerApplication.getmInstance().getSharedPreferences().getInt(getString(R.string.preferances_userId), 0));

        productListAdapter = new CartListAdapter(getActivity(), productListModelArrayList, CartListFragment.this);
        rvProductList.setAdapter(productListAdapter);

        if(productListModelArrayList != null && productListModelArrayList.size() <= 0){
            rlCheckOut.setVisibility(View.GONE);
            mLayoutTotalMoney.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.menu_left);
        item.setVisible(false);
    }


    @Override
    public void onClick(View v) {

        if (v == rlCheckOut) {
            int userId = GrocerApplication.getmInstance().getSharedPreferences().getInt(getString(R.string.preferances_userId), 0);
            if(userId > 0) {
                ShipInfoFragment cartFragment = new ShipInfoFragment();
                cartFragment.setTargetFragment(CartListFragment.this, 222);
                Utils.addNextFragment(getActivity(), cartFragment, CartListFragment.this, true);
            } else{
                Intent mMenuIntent = new Intent(this.getActivity(), LoginActivity.class);
                mMenuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mMenuIntent);
                this.getActivity().overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
            }
        }
    }



    /**
     * SetUp Toolbar & title
     */


    public void initToolbar() {

        if (getTargetFragment() instanceof ProductDetailsFragment) {
            ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_my_cart), true);
        } else if (getTargetFragment() instanceof ProductListFragment) {
            ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_my_cart), true);
        } else if (getTargetFragment() instanceof OrderListFragment) {
            ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_my_cart), true);
        } else {
            ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_my_cart), false);
        }

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


    /**
     *  Add to cart & refresh adapter
     */


    public void addToCart(boolean addTocart, int position) {
        ProductOrderModel productOrderModel = productListModelArrayList.get(position);
        if (addTocart) {
            int totalKg = productOrderModel.getNumber();
            totalKg = totalKg + 1;
            productOrderModel.setNumber(totalKg);

        } else {
            int totalKg = productOrderModel.getNumber();

            if (totalKg > 1) {
                totalKg = totalKg - 1;
                productOrderModel.setNumber(totalKg);
            }
        }

        productListAdapter.notifyDataSetChanged();
        tvTotalPrice.setText(getTotalPrice() + " " + getString(R.string.dolar));

        //insert or update to db lite
        int userId = GrocerApplication.getmInstance().getSharedPreferences().getInt(getString(R.string.preferances_userId), 0);
        SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(getActivity());
        ProductOrderModel productOrderModel2 = sqlDbHelpers.getProductOrder(userId, productOrderModel.getProductID());

        if(productOrderModel2 != null){
            productOrderModel2.setNumber(productOrderModel.getNumber());
            sqlDbHelpers.updateProductOrder(productOrderModel2);
        }else {
            productOrderModel2.setNumber(productOrderModel.getNumber());
            sqlDbHelpers.addProductOrder(productOrderModel2);
        }
    }

    /**
     *  Delete item from cart and refresh adapter
     */

    public void deleteItem(int position) {
        ProductOrderModel productOrderModel = productListModelArrayList.get(position);
        productListModelArrayList.remove(position);
        productListAdapter.notifyDataSetChanged();
        tvTotalPrice.setText(getTotalPrice() + " " + getString(R.string.dolar));

        if (productListModelArrayList.size() == 0) {
            rlEmpty.setVisibility(View.VISIBLE);
            rvProductList.setVisibility(View.GONE);
            rlCheckOut.setVisibility(View.GONE);
        }

        int userId = GrocerApplication.getmInstance().getSharedPreferences().getInt(getString(R.string.preferances_userId), 0);
        SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(getActivity());
        sqlDbHelpers.deleteProductOrder(userId, productOrderModel.getProductID());

    }
}
