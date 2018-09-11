package com.summit.cherrity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.summit.cherrity.Activity.MenuActivity;
import com.summit.cherrity.R;



/**
 * *************************************************************************
 *
 * @ClassdName:AboutusFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display about-us content
 * <p/>
 * *************************************************************************
 */


public class AboutusFragment extends BaseFragment {


    private TextView tvAboutUs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_aboutus, container, false);
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

        tvAboutUs=(TextView) rootView.findViewById(R.id.fragment_aboutus_tvData);
        tvAboutUs.setText(getString(R.string.description_data));

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * SetUp Toolbar & title
     */


    public void initToolbar() {

        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_about_us), false);


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
