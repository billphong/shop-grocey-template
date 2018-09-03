package com.summit.service.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import com.summit.service.filters.ProductFilter;
import com.summit.service.fragment.ProductListFragment;
import com.summit.service.model.category.CateItem;
import com.summit.service.support.v4.MyStatePagerAdapter;

import java.util.ArrayList;


/**
 * *************************************************************************
 *
 * @ClassdName:ImagePagerAdapter
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for category with different fragmnet on viewPager adapter
 * <p/>
 * *************************************************************************
 */

public class SubCategoryPagerAdapter extends MyStatePagerAdapter {

    private Context mContext;
    private ArrayList<CateItem> categories;

    public SubCategoryPagerAdapter(Activity context, FragmentManager fm, ArrayList<CateItem> categoryTitles) {
        super(fm);
        mContext = context;
        this.categories = categoryTitles;
    }


    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        CateItem cateItem = this.categories.get(position);
        ProductFilter filter = new ProductFilter();
        filter.setCateId(cateItem.getId());
        return ProductListFragment.newInstance(filter);
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return categories.size();
    }


    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        return  categories.get(position).getTxt();
    }


}