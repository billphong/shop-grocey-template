package com.grocery.service.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.grocery.service.R;
import com.grocery.service.customecomponent.CustomTextView;
import com.grocery.service.fragment.ProductListFragment;
import com.grocery.service.helpers.DownloadImageTask;
import com.grocery.service.model.product.ProductItem;
import com.grocery.service.model.product.ProductListModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ProductItem> productModelList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private ProductListFragment productListFragment;


    public ProductListAdapter(final Context context, final List<ProductItem> items, final ProductListFragment fragment) {
        this.productModelList = items;
        this.mContext = context;
        this.productListFragment = fragment;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_list, parent, false);
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
                    onItemClickListener.onItemClick(v, (ProductItem) v.getTag());
                }
            }, 200);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ProductItem viewModel);
    }


    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvProductName;
        private CustomTextView tvProductPrice;
        private CustomTextView tvOldPrice;
        private CustomTextView tvAddToCard;
        private CustomTextView tvTotalKg;
        private ImageView ivProImg;
        private ImageView ivPlus;
        private ImageView ivMins;
        private RelativeLayout rlTotalCartItem;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvProductName = (CustomTextView) itemView.findViewById(R.id.row_productlist_tvName);
            tvProductPrice = (CustomTextView) itemView.findViewById(R.id.row_productlist_tvPrice);
            tvAddToCard = (CustomTextView) itemView.findViewById(R.id.row_produclist_tvAddtoCart);
            tvTotalKg = (CustomTextView) itemView.findViewById(R.id.row_productlist_tvTotalKg);
            tvOldPrice = (CustomTextView) itemView.findViewById(R.id.row_productlist_tvKg);
            ivProImg = (ImageView) itemView.findViewById(R.id.row_productlist_ivProImg);
            ivPlus = (ImageView) itemView.findViewById(R.id.row_productlist_ivPlus);
            ivMins = (ImageView) itemView.findViewById(R.id.row_productlist_ivMins);
            rlTotalCartItem = (RelativeLayout) itemView.findViewById(R.id.row_produclist_rlTotalCart);


        }


        public void bindData(final ProductItem item, final int position) {

            tvProductName.setText("" + item.getName());
            tvProductPrice.setText(Integer.toString(item.getPrice()));
            tvOldPrice.setText(Integer.toString(item.getOldPrice()));
            tvTotalKg.setText(Integer.toString(item.getTotalItem()));
            itemView.setTag(item);
            //new DownloadImageTask(ivProImg).execute(item.getImg());
            Picasso.get().load(item.getImg()).into(ivProImg);
            rlTotalCartItem.setVisibility(item.getTotalItem()==0? View.GONE:View.VISIBLE);
            tvAddToCard.setVisibility(item.getTotalItem()==0? View.VISIBLE:View.GONE);

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


            ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    productListFragment.addToCart(true,position);
                }
            });


            ivMins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    productListFragment.addToCart(false,position);
                }
            });

            tvAddToCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    productListFragment.addToCart(true,position);
                }
            });

        }
    }
}
