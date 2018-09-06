package com.summit.service.model;

import com.summit.service.GrocerApplication;
import com.summit.service.R;
import com.summit.service.model.order.ProductOrderModel;
import com.summit.service.model.product.ProductItem;

public class ConvertModelHelpers {
    public static ProductOrderModel toProductOrderModel(ProductItem item){
        ProductOrderModel orderModel = new ProductOrderModel();
        orderModel.setNumber(item.getTotalItem());
        orderModel.setOldPrice(item.getOldPrice());
        orderModel.setDiscount(item.getDiscount());
        orderModel.setProductId(item.getId());
        orderModel.setImg(item.getImg());
        orderModel.setName(item.getName());
        if(item.getDiscount() > 0){
            orderModel.setPrice(item.getOldPrice() * (100 - item.getDiscount()) / 100);
        }else{
            orderModel.setPrice(item.getOldPrice());
        }
        //orderModel.setUserID(GrocerApplication.getmInstance().getSharedPreferences().getInt("user_id", 0));
        return orderModel;
    }
}
