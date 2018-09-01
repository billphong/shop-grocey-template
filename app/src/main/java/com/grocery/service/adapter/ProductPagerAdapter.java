package com.grocery.service.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.grocery.service.R;
import com.grocery.service.helpers.DownloadImageTask;


import java.util.List;

/**
 * *************************************************************************
 * Use image resource
 * @ClassdName:ImagePagerAdapter
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for product details slider images
 * <p/>
 * *************************************************************************
 */

public class ProductPagerAdapter<T> extends PagerAdapter
{
    Context context;
    private List<T> imgList;
    LayoutInflater layoutInflater;



    public ProductPagerAdapter(Context context, List<T> imgList) {
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
        T t = imgList.get(position);
        if(t instanceof Integer) {
            imageView.setImageDrawable(context.getResources().getDrawable((Integer) t));
        }else if(t instanceof String){
            new DownloadImageTask(imageView).execute((String)t);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

