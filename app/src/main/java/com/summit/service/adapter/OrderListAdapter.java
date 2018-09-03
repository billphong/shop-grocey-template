package com.summit.service.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.summit.service.R;
import com.summit.service.customecomponent.CustomTextView;
import com.summit.service.fragment.OrderListFragment;
import com.summit.service.model.order.OrderListModel;

import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<OrderListModel> productModelList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private OrderListFragment productListFragment;


    public OrderListAdapter(final Context context, final List<OrderListModel> items, final OrderListFragment fragment) {
        this.productModelList = items;
        this.mContext = context;
        this.productListFragment = fragment;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_list, parent, false);
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
                    onItemClickListener.onItemClick(v, (OrderListModel) v.getTag());
                }
            }, 200);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, OrderListModel viewModel);
    }


    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvProductName;
        private CustomTextView tvProductPrice;
        private CustomTextView tvOrderId;
        private CustomTextView tvTotalKg;
        private CustomTextView tvStatus;
        private CustomTextView tvDate;
        private CustomTextView tvTime;
        private ImageView ivProfile;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvProductName = (CustomTextView) itemView.findViewById(R.id.row_orderlist_tvProName);
            tvProductPrice = (CustomTextView) itemView.findViewById(R.id.row_orderlist_tvPrice);
            tvOrderId = (CustomTextView) itemView.findViewById(R.id.row_orderlist_tvOrderId);
            tvTotalKg = (CustomTextView) itemView.findViewById(R.id.row_orderlist_tvKg);
            tvDate = (CustomTextView) itemView.findViewById(R.id.row_orderlist_tvDate);
            tvTime = (CustomTextView) itemView.findViewById(R.id.row_orderlist_tvTime);
            tvStatus = (CustomTextView) itemView.findViewById(R.id.row_orderlist_tvStatus);
            ivProfile = (ImageView) itemView.findViewById(R.id.ivOrder);


        }


        public void bindData(final OrderListModel item, final int position) {

            tvProductName.setText(item.getProName());
            tvProductPrice.setText(mContext.getString(R.string.dolar) + item.getPrice());
            tvTotalKg.setText(item.getKg());
            tvDate.setText(item.getDate());
            tvTime.setText(item.getTime());
            tvStatus.setText(item.getStatus());
            tvOrderId.setText(item.getOrderId());
            ivProfile.setImageDrawable(mContext.getResources().getDrawable(item.getOrderImage()));
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
