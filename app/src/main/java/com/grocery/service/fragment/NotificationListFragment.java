package com.grocery.service.fragment;

import android.content.Context;
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
import com.grocery.service.adapter.NotificationListAdapter;
import com.grocery.service.data.TempListData;
import com.grocery.service.model.notification.Notification;

import java.util.List;


/**
 * *************************************************************************
 *
 * @ClassdName:NotificationListFragment
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for display notification with special offer
 * <p/>
 * *************************************************************************
 */


public class NotificationListFragment extends BaseFragment implements NotificationListAdapter.OnItemClickListener {

    //Declaration
    private RecyclerView rvNotification;
    private LinearLayoutManager mLayoutManager;
    private NotificationListAdapter productListAdapter;
    private List<Notification> NotificationArrayList;
    private MenuItem item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
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


        rvNotification = (RecyclerView) rootView.findViewById(R.id.fragment_notification_list_rvNotification);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvNotification.setLayoutManager(mLayoutManager);
        getListData();


    }

    /**
     * get notification list data and setUp adapter
     */

    private void getListData() {

        TempListData tempListData=new TempListData();
        NotificationArrayList =tempListData.getNotificationList();

        productListAdapter = new NotificationListAdapter(getActivity(), NotificationArrayList, NotificationListFragment.this);
        rvNotification.setAdapter(productListAdapter);
        productListAdapter.setOnItemClickListener(this);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        item = menu.findItem(R.id.menu_left);
        item.setVisible(false);

    }


    @Override
    public void onClick(View v) {


    }

    /**
     * SetUp Toolbar & title
     */
    public void initToolbar() {
        ((MenuActivity) getActivity()).setUpToolbar(getString(R.string.nav_menu_notification), false);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initToolbar();
    }

    /**
     *  RecyclerView Item click listener
     */


    @Override
    public void onItemClick(View view, Notification viewModel) {


    }


}
