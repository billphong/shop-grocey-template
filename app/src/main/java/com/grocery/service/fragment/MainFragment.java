package com.grocery.service.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.service.Activity.MenuActivity;
import com.grocery.service.R;
import com.grocery.service.adapter.SubCategoryPagerAdapter;
import com.grocery.service.util.Utils;



/**
 * *************************************************************************
 *
 * @ClassdName:MainFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display all category & tabs Using TabLayout & ViewPager
 * <p/>
 * *************************************************************************
 */


public class MainFragment extends BaseFragment {


    //Declaration
    private ViewPager vpSubCategory;
    private TabLayout tlHome;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_subcategory, container, false);
        initToolbar();
        setHasOptionsMenu(true);
        initComponents(rootView);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void initComponents(View rootView) {

        vpSubCategory = (ViewPager) rootView.findViewById(R.id.fragment_home_vpHome);
        tlHome = (TabLayout) rootView.findViewById(R.id.fragment_home_tlHome);


        final String[] categoryTitles = getResources().getStringArray(R.array.category);
        SubCategoryPagerAdapter adapter = new SubCategoryPagerAdapter(getActivity(), getChildFragmentManager(), categoryTitles);
        vpSubCategory.setAdapter(adapter);

        // Set the adapter onto the view pager
        vpSubCategory.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        tlHome.setupWithViewPager(vpSubCategory);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void initToolbar() {

        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_home), false);


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


}
