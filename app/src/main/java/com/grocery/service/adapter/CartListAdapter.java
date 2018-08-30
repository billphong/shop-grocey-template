package com.grocery.service.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.service.R;
import com.grocery.service.customecomponent.CustomTextView;
import com.grocery.service.fragment.CartListFragment;
import com.grocery.service.model.cart.CartlistModel;

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

    private List<CartlistModel> productModelList;
    private Context mContext;
    private CartListFragment orderListFragmentNew;


    public CartListAdapter(final Context context, final List<CartlistModel> items, final CartListFragment fragment) {
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
        ((ViewHolderData) holder).bindData(productModelList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return productModelList.size();
    }


    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvProductName;
        private CustomTextView tvProductPrice;
        private CustomTextView tvKg;
        private CustomTextView tvTotalKg;
        private ImageView ivProImg;
        private ImageView ivPlus;
        private ImageView ivMins;
        private ImageView ivDelete;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvProductName = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvName);
            tvProductPrice = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvPrice);
            tvTotalKg = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvTotalKg);
            tvKg = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvKg);
            ivProImg = (ImageView) itemView.findViewById(R.id.row_cartlist_ivProImg);
            ivPlus = (ImageView) itemView.findViewById(R.id.row_cartlist_ivPlus);
            ivMins = (ImageView) itemView.findViewById(R.id.row_cartlist_ivMins);
            ivDelete = (ImageView) itemView.findViewById(R.id.row_cartlist_ivDelete);


        }


        public void bindData(final CartlistModel item, final int position)
        {

            tvProductName.setText("" + item.getProductName());
            tvProductPrice.setText("$"+item.getProductPrice());
            tvKg.setText(item.getKg());
            tvTotalKg.setText(item.getProductQuantity() +mContext.getString(R.string.kg));
            itemView.setTag(item);
            ivProImg.setImageDrawable(mContext.getResources().getDrawable(item.getProductImages()));


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
