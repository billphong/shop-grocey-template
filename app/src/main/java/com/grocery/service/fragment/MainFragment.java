package com.grocery.service.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.service.Activity.MenuActivity;
import com.grocery.service.R;
import com.grocery.service.adapter.SubCategoryPagerAdapter;
import com.grocery.service.asyns.TaskDelegate;
import com.grocery.service.commons.Apis;
import com.grocery.service.dal.GetDataAsync;
import com.grocery.service.model.BaseModel;
import com.grocery.service.model.category.CateItem;
import com.grocery.service.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


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


public class MainFragment extends BaseFragment implements TaskDelegate{


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

        //final String[] categoryTitles = getResources().getStringArray(R.array.category);
        GetDataAsync getDataAsync = new GetDataAsync(Apis.CATEGORY_API, this, LoadingDialog());
        getDataAsync.execute();
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


    @Override
    public void onTaskCompleted(Object data) {
        ArrayList<CateItem> cate = new ArrayList<CateItem>();
        try {
            JSONArray jsCates = new JSONArray((String)data);
            for (int i = 0; i < jsCates.length(); i ++){
                JSONObject obj = jsCates.getJSONObject(i);
                CateItem t = new CateItem(obj);
                cate.add(t);
            }
            SubCategoryPagerAdapter adapter = new SubCategoryPagerAdapter(getActivity(), getChildFragmentManager(), cate);
            vpSubCategory.setAdapter(adapter);

            // Set the adapter onto the view pager
            vpSubCategory.setAdapter(adapter);

            // Give the TabLayout the ViewPager
            tlHome.setupWithViewPager(vpSubCategory);
        }catch (Exception ex){
            Log.e("GetData", ex.getMessage());
        }
    }
}
