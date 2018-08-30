package com.grocery.service.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.grocery.service.R;


import java.util.List;

/**
 * *************************************************************************
 *
 * @ClassdName:ImagePagerAdapter
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for product details slider images
 * <p/>
 * *************************************************************************
 */

public class ProductPagerAdapter extends PagerAdapter
{
    Context context;
    private List<Integer> imgList;
    LayoutInflater layoutInflater;



    public ProductPagerAdapter(Context context, List<Integer> imgList) {
        this.context = context;
        this.imgList = imgList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_varcode_image, container, false);
        ImageView imageView = itemView.findViewById(R.id.ivCode);
        imageView.setImageDrawable(context.getResources().getDrawable(imgList.get(position)));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
