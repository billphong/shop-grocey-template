package com.summit.cherrity.data;

import android.content.Context;

import com.summit.cherrity.db.SqlDbHelpers;
import com.summit.cherrity.model.ConvertModelHelpers;
import com.summit.cherrity.model.order.ProductOrderModel;
import com.summit.cherrity.model.product.ProductItem;

public class ProductOrderService {
    public static void addUpdateToProductOrderTable(Context context, int userId, ProductItem productItem){
        SqlDbHelpers sqlDbHelpers = new SqlDbHelpers(context);
        ProductOrderModel productOrderModel = sqlDbHelpers.getProductOrder(userId, productItem.getId());

        if(productOrderModel != null){
            productOrderModel = ConvertModelHelpers.toProductOrderModel(productItem);
            productOrderModel.setUserID(userId);
            sqlDbHelpers.updateProductOrder(productOrderModel);
        }else {
            productOrderModel = ConvertModelHelpers.toProductOrderModel(productItem);
            productOrderModel.setUserID(userId);
            sqlDbHelpers.addProductOrder(productOrderModel);
        }
    }
}
