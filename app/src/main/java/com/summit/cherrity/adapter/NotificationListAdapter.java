package com.summit.cherrity.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.summit.cherrity.R;
import com.summit.cherrity.customecomponent.CustomTextView;
import com.summit.cherrity.fragment.NotificationListFragment;
import com.summit.cherrity.model.notification.Notification;

import java.util.List;


public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<Notification> productModelList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private NotificationListFragment notificationListFragment;


    public NotificationListAdapter(final Context context, final List<Notification> items, final NotificationListFragment fragment) {
        this.productModelList = items;
        this.mContext = context;
        this.notificationListFragment = fragment;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        v.setOnClickListener(this);
        return new ViewHolderData(v);

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(productModelList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (Notification) v.getTag());
                }
            }, 200);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Notification viewModel);
    }


    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvName;
        private CustomTextView tvdateTime;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvName = (CustomTextView) itemView.findViewById(R.id.row_notification_list_tvName);
            tvdateTime = (CustomTextView) itemView.findViewById(R.id.row_notification_list_tvDateTime);


        }


        public void bindData(final Notification item, final int position) {

            tvName.setText(item.getName());
            tvdateTime.setText(item.getDateTime());
            itemView.setTag(item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    // Give some time to the ripple to finish the effect
                    if (onItemClickListener != null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onItemClickListener.onItemClick(v, item);
                            }
                        }, 200);
                    }
                }
            });


        }
    }
}
