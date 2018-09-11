package com.summit.cherrity.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.summit.cherrity.R;
import com.summit.cherrity.customecomponent.CustomTextView;
import com.summit.cherrity.model.addressBook.AddressBookModel;

import java.util.List;

/**
 * *************************************************************************
 *
 * @ClassdName:AddressListAdapter
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for address list adapter to display rows & Viewholder.
 * <p/>
 * *************************************************************************
 */


public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<AddressBookModel> productModelList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;


    public AddressListAdapter(final Context context, final List<AddressBookModel> items) {
        this.productModelList = items;
        this.mContext = context;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_address, parent, false);
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

    public void addData(List<AddressBookModel> addressBookModelNewList) {

        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, AddressBookModel viewModel, int position);
    }


    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvName;
        private CustomTextView tvMobile;
        private CustomTextView tvPincode;
        private CustomTextView tvAddreess;
        private RadioButton rbAddress;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvName = (CustomTextView) itemView.findViewById(R.id.row_addresslist_tvName);
            tvMobile = (CustomTextView) itemView.findViewById(R.id.row_addresslist_tvMobile);
            tvPincode = (CustomTextView) itemView.findViewById(R.id.row_addresslist_tvPincode);
            tvAddreess = (CustomTextView) itemView.findViewById(R.id.row_addresslist_tvAddress);
            rbAddress = (RadioButton) itemView.findViewById(R.id.row_addresslist_rbSelectAddress);


        }


        public void bindData(final AddressBookModel item, final int position)
        {

            tvName.setText(item.getName());
            tvMobile.setText(item.getMobile_no());
            tvPincode.setText(item.getPinCode());
            tvAddreess.setText(item.getAddress());
            rbAddress.setChecked(item.isSelected());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    // Give some time to the ripple to finish the effect
                    if (onItemClickListener != null)
                    {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onItemClickListener.onItemClick(v, item,position);
                            }
                        }, 200);
                    }
                }
            });


        }
    }
}
