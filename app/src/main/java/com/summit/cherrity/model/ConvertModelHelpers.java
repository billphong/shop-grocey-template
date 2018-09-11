package com.summit.cherrity.model;

import com.summit.cherrity.model.order.ProductOrderModel;
import com.summit.cherrity.model.product.ProductItem;

public class ConvertModelHelpers {
    public static ProductOrderModel toProductOrderModel(ProductItem item){
        ProductOrderModel orderModel = new ProductOrderModel();
        orderModel.setNumber(item.getTotalItem());
        orderModel.setOldPrice(item.getOldPrice());
        orderModel.setDiscount(item.getDiscount());
        orderModel.setProductID(item.getId());
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
