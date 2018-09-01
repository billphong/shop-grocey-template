package com.grocery.service.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grocery.service.Activity.MenuActivity;
import com.grocery.service.R;
import com.grocery.service.adapter.ProductPagerAdapter;
import com.grocery.service.helpers.TextViewHelpers;
import com.grocery.service.model.product.ProductItem;
import com.grocery.service.util.Utils;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


/**
 * *************************************************************************
 *
 * @ClassdName:ProductDetailsFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display product details with field Product name, Price,Description & product slider images & add to cart button
 * <p/>
 * *************************************************************************
 */


public class ProductDetailsFragment extends BaseFragment {


    private ViewPager viewPagerImages;
    private CircleIndicator indicator;
    private TextView tvQuantity;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvDescription;
    private ImageView ivPlus;
    private ImageView ivMins;
    private RelativeLayout rlAddTocart;
    private PagerAdapter imagePagerAdapter;
    private ProductItem productListModel;

    private ArrayList pagerImgList;
    private Bundle bundle;
    private int totalKg = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        setHasOptionsMenu(true);
        initToolbar();
        initComponents(rootView);

        return rootView;
    }

    public void initToolbar() {

        ((MenuActivity) getActivity()).setUpToolbar(productListModel.getName(), true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bundle = getArguments();

        if (bundle != null) {
            productListModel = bundle.getParcelable(getString(R.string.bdl_model));
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.menu_left);
        item.setVisible(true);
        item.setIcon(R.drawable.cart);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Utils.hideKeyboard(getActivity());
                CartListFragment cartFragment = new CartListFragment();
                cartFragment.setTargetFragment(ProductDetailsFragment.this, 222);
                Utils.addNextFragment(getActivity(), cartFragment, ProductDetailsFragment.this, true);
                return true;
            }
        });
    }



    /**
     * init Components
     */


    @Override
    public void initComponents(View rootView) {

        viewPagerImages = rootView.findViewById(R.id.fragment_product_details_vpSlider);
        indicator = rootView.findViewById(R.id.fragment_product_details_indicator);
        rlAddTocart = rootView.findViewById(R.id.fragment_product_details_rlAddToCart);
        tvQuantity = rootView.findViewById(R.id.fragment_product_details_tvTotalKg);
        tvName = rootView.findViewById(R.id.fragment_product_details_tvTitle);
        tvPrice = rootView.findViewById(R.id.fragment_product_details_tvPrice);
        tvDescription = rootView.findViewById(R.id.fragment_product_details_tvDescription);
        ivPlus = rootView.findViewById(R.id.fragment_product_details_ivPlus);
        ivMins = rootView.findViewById(R.id.fragment_product_details_ivMins);

        rlAddTocart.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        ivMins.setOnClickListener(this);

        setUpSliderImages();
        setUpDetails();


    }

    private void setUpDetails() {

        if (productListModel != null) {
            tvName.setText(productListModel.getName());
            tvQuantity.setText(productListModel.getTotalItemStr());
            tvPrice.setText(productListModel.getPriceStr());

            TextViewHelpers.setTextHtml(tvDescription, productListModel.getDescription());
            totalKg = productListModel.getTotalItem();
        }
    }

    private void setUpSliderImages() {
        try {
            pagerImgList = new ArrayList<>();

//            for (int i = 0; i < 5; i++) {
//                pagerImgList.add(R.drawable.graps);
//                pagerImgList.add(R.drawable.apple);
//            }
            if(productListModel != null){
                pagerImgList.add(productListModel.getImg());
                imagePagerAdapter = new ProductPagerAdapter<String>(getActivity(), pagerImgList);
            }else{
                pagerImgList.add(R.drawable.no_img_available);
                imagePagerAdapter = new ProductPagerAdapter<Integer>(getActivity(), pagerImgList);
            }

            viewPagerImages.setAdapter(imagePagerAdapter);
            indicator.setViewPager(viewPagerImages);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.fragment_product_details_ivPlus:
                addToCart(true);
                break;

            case R.id.fragment_product_details_ivMins:
                addToCart(false);
                break;

            case R.id.fragment_product_details_rlAddToCart:
                CartListFragment cartFragment = new CartListFragment();
                cartFragment.setTargetFragment(ProductDetailsFragment.this, 222);
                Utils.addNextFragment(getActivity(), cartFragment, ProductDetailsFragment.this, true);
                break;
        }
    }

    private void addToCart(boolean addCart) {

        if (addCart) {
            totalKg = totalKg + 1;
            tvQuantity.setText(totalKg);

        } else {
            if (totalKg < 1) {
                totalKg = totalKg - 1;
                tvQuantity.setText(totalKg);
            }
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
