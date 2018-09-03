package com.summit.service.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.summit.service.Activity.MenuActivity;
import com.summit.service.GrocerApplication;
import com.summit.service.R;
import com.summit.service.adapter.ProductListAdapter;
import com.summit.service.asyns.TaskDelegate;
import com.summit.service.commons.Apis;
import com.summit.service.dal.GetDataAsync;
import com.summit.service.db.SqlDbHelpers;
import com.summit.service.filters.ProductFilter;
import com.summit.service.model.ConvertModelHelpers;
import com.summit.service.model.order.ProductOrderModel;
import com.summit.service.model.product.ProductItem;
import com.summit.service.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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


public class ProductListFragment extends BaseFragment implements ProductListAdapter.OnItemClickListener, TaskDelegate {

    //Declaration
    private RecyclerView rvProductList;
    private GridLayoutManager mLayoutManager;
    private ProductListAdapter productListAdapter;
    private List<ProductItem> productListModelArrayList;
    private MenuItem item;
    private ProductFilter filter;

    public static final ProductListFragment newInstance(ProductFilter filter){
        ProductListFragment frg = new ProductListFragment();
        frg.filter = filter;
        return frg;
    }

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
        getListData(filter);

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
    private void getListData(ProductFilter filter)
    {
        if(filter != null) {
            GetDataAsync getDataAsync = new GetDataAsync(String.format(Apis.PRODUCT_GET_BY_CATE_API, filter.getCateId(), filter.getPageIndex()), this, LoadingDialog());
            getDataAsync.execute();
        }
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
    public void onItemClick(View view, ProductItem viewModel) {

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
        ProductItem productItem = productListModelArrayList.get(position);
        if (addTocart) {
            int totalKg = productItem.getTotalItem();
            totalKg = totalKg + 1;
            productItem.setTotalItem(totalKg);

        } else {
            int totalKg = productItem.getTotalItem();

            if (totalKg < 1) {
                productItem.setTotalItem(0);
            } else {
                totalKg = totalKg - 1;
                productItem.setTotalItem(totalKg);
            }
        }

        //insert or update to db lite
        int userId = GrocerApplication.getmInstance().getSharedPreferences().getInt(getString(R.string.preferances_userId), 0);
        SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(getActivity());
        ProductOrderModel productOrderModel = sqlDbHelpers.getProductOrder(userId, productItem.getId());

        if(productOrderModel != null){
            productOrderModel = ConvertModelHelpers.toProductOrderModel(productItem);
            productOrderModel.setUserId(userId);
            sqlDbHelpers.updateProductOrder(productOrderModel);
        }else {
            productOrderModel = ConvertModelHelpers.toProductOrderModel(productItem);
            productOrderModel.setUserId(userId);
            sqlDbHelpers.addProductOrder(productOrderModel);
        }

        productListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onTaskCompleted(Object data) {
        ArrayList<ProductItem> cate = new ArrayList<ProductItem>();
        try {
            JSONArray jsCates = new JSONArray((String)data);
            for (int i = 0; i < jsCates.length(); i ++){
                JSONObject obj = jsCates.getJSONObject(i);
                ProductItem t = new ProductItem(obj);
                cate.add(t);
            }
            productListModelArrayList = cate;
            productListAdapter = new ProductListAdapter(getActivity(), productListModelArrayList, ProductListFragment.this);
            rvProductList.setAdapter(productListAdapter);
            productListAdapter.setOnItemClickListener(this);
        }catch (Exception ex){
            Log.e("GetData", ex.getMessage());
        }
    }
}
