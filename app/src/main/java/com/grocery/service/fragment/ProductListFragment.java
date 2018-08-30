package com.grocery.service.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.service.Activity.MenuActivity;
import com.grocery.service.R;
import com.grocery.service.adapter.ProductListAdapter;
import com.grocery.service.data.TempListData;
import com.grocery.service.model.product.ProductListModel;
import com.grocery.service.util.Utils;

import java.util.List;


/**
 * *************************************************************************
 *
 * @ClassdName:ProductListFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display product list with field Product name, Price, product images & add and remove item on Cart
 * <p/>
 * *************************************************************************
 */


public class ProductListFragment extends BaseFragment implements ProductListAdapter.OnItemClickListener {

    //Declaration
    private RecyclerView rvProductList;
    private GridLayoutManager mLayoutManager;
    private ProductListAdapter productListAdapter;
    private List<ProductListModel> productListModelArrayList;
    private MenuItem item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        initToolbar();
        setHasOptionsMenu(true);
        initComponents(rootView);

        return rootView;
    }

    /**
     * init Components
     */

    @Override
    public void initComponents(View rootView) {
        rvProductList = (RecyclerView) rootView.findViewById(R.id.fragment_productlist_rvProductList);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvProductList.setLayoutManager(mLayoutManager);
        getListData();

    }
    /**
     * SetUp Toolbar & title
     */

    public void initToolbar() {
        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_home), false);
    }
    /**
     * get Product list data and setUp adapter
     */
    private void getListData()
    {

        TempListData tempListData=new TempListData();
        productListModelArrayList = tempListData.getProductList();
        productListAdapter = new ProductListAdapter(getActivity(), productListModelArrayList, ProductListFragment.this);
        rvProductList.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(this);
    }

    /**
     *  Menu item click listener & open fragment
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.menu_left);
        item.setVisible(true);
        item.setIcon(R.drawable.cart);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Utils.hideKeyboard(getActivity());
                CartListFragment fragmentProductDetails = new CartListFragment();
                fragmentProductDetails.setTargetFragment(ProductListFragment.this, 222);
                Utils.addNextFragment(getActivity(), fragmentProductDetails, ProductListFragment.this, false);

                return true;
            }
        });
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
     *  item click listener & open fragment
     */

    @Override
    public void onItemClick(View view, ProductListModel viewModel) {

        ProductDetailsFragment fragmentProductDetails = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.bdl_model), viewModel);
        fragmentProductDetails.setArguments(bundle);
        Utils.addNextFragment(getActivity(), fragmentProductDetails, ProductListFragment.this, false);


    }

    /**
     *  Add to cart & refresh adapter
     */



    public void addToCart(boolean addTocart, int position) {
        if (addTocart) {
            int totalKg = productListModelArrayList.get(position).getTotalKg();
            totalKg = totalKg + 1;
            productListModelArrayList.get(position).setTotalKg(totalKg);

        } else {
            int totalKg = productListModelArrayList.get(position).getTotalKg();

            if (totalKg < 1) {
                productListModelArrayList.get(position).setTotalKg(0);
            } else {
                totalKg = totalKg - 1;
                productListModelArrayList.get(position).setTotalKg(totalKg);
            }


        }

        productListAdapter.notifyDataSetChanged();

    }
}
