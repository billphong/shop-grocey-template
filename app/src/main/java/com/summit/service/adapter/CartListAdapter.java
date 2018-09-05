package com.summit.service.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.summit.service.R;
import com.summit.service.customecomponent.CustomTextView;
import com.summit.service.fragment.CartListFragment;
import com.summit.service.model.order.ProductOrderModel;

import java.util.List;


/**
 * *************************************************************************
 *
 * @ClassdName:CartListAdapter
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for cart list adapter to display cart items & user can add and remove item.
 * <p/>
 * *************************************************************************
 */



public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductOrderModel> productModelList;
    private Context mContext;
    private CartListFragment orderListFragmentNew;


    public CartListAdapter(final Context context, final List<ProductOrderModel> items, final CartListFragment fragment) {
        this.productModelList = items;
        this.mContext = context;
        this.orderListFragmentNew = fragment;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_list_new, parent, false);
        return new ViewHolderData(v);

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(productModelList != null) {
            ((ViewHolderData) holder).bindData(productModelList.get(position), position);
        }
    }


    @Override
    public int getItemCount() {
        return productModelList != null ? productModelList.size() : 0;
    }


    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvProductName;
        private CustomTextView tvProductPrice;
        private CustomTextView tvProductOldPrice;
        private CustomTextView tvNumber;
        private CustomTextView tvTotalKg;
        private ImageView ivProImg;
        private ImageView ivPlus;
        private ImageView ivMins;
        private ImageView ivDelete;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvProductName = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvName);
            tvProductPrice = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvPrice);
            tvProductOldPrice = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvOldPrice);
            tvProductOldPrice.setPaintFlags(tvProductOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvTotalKg = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvTotalKg);
            tvNumber = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvKg);
            ivProImg = (ImageView) itemView.findViewById(R.id.row_cartlist_ivProImg);
            ivPlus = (ImageView) itemView.findViewById(R.id.row_cartlist_ivPlus);
            ivMins = (ImageView) itemView.findViewById(R.id.row_cartlist_ivMins);
            ivDelete = (ImageView) itemView.findViewById(R.id.row_cartlist_ivDelete);


        }


        public void bindData(final ProductOrderModel item, final int position)
        {

            tvProductName.setText("" + item.getName());
            tvProductPrice.setText(item.getPrice() + " đ");
            tvProductOldPrice.setText(item.getOldPrice() + " đ");
            tvNumber.setText(item.getNumberStr());
            tvTotalKg.setText(item.getNumberStr());
            itemView.setTag(item);
            Picasso.get().load(item.getImg()).into(ivProImg);


            ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    orderListFragmentNew.addToCart(true, position);
                }
            });


            ivMins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    orderListFragmentNew.addToCart(false, position);
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    orderListFragmentNew.deleteItem(position);
                }
            });

        }
    }
}
