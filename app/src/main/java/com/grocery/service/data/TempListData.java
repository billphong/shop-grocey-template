package com.grocery.service.data;

import com.grocery.service.R;
import com.grocery.service.adapter.AddressListAdapter;
import com.grocery.service.adapter.OrderListAdapter;
import com.grocery.service.fragment.OrderListFragment;
import com.grocery.service.model.addressBook.AddressBookModel;
import com.grocery.service.model.cart.CartlistModel;
import com.grocery.service.model.notification.Notification;
import com.grocery.service.model.order.OrderListModel;
import com.grocery.service.model.product.ProductListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root
 */

public class TempListData {




    public List<ProductListModel> getProductList() {

        List<ProductListModel> productListModelArrayList = new ArrayList<>();
        ProductListModel productListModel = new ProductListModel();

        productListModel.setProductName("Apple");
        productListModel.setProductPrice("$10 ");
        productListModel.setProductImage(R.drawable.apple);
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Guava");
        productListModel.setProductPrice("$15 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.gw);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Graps");
        productListModel.setProductPrice("$25 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.graps);
        productListModelArrayList.add(productListModel);

        productListModel = new ProductListModel();
        productListModel.setProductName("Pineapple");
        productListModel.setProductPrice("$8 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.pineple);
        productListModelArrayList.add(productListModel);


        productListModel.setProductName("Apple");
        productListModel.setProductPrice("$10 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.apple);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Guava");
        productListModel.setProductPrice("$15 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.gw);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Graps");
        productListModel.setProductPrice("$25 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.graps);
        productListModelArrayList.add(productListModel);

        productListModel = new ProductListModel();
        productListModel.setProductName("Pineapple");
        productListModel.setProductPrice("$8 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.pineple);
        productListModelArrayList.add(productListModel);

        productListModel.setProductName("Apple");
        productListModel.setProductPrice("$10 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.apple);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Guava");
        productListModel.setProductPrice("$15 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.gw);
        productListModelArrayList.add(productListModel);


        productListModel = new ProductListModel();
        productListModel.setProductName("Graps");
        productListModel.setProductPrice("$25 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.graps);
        productListModelArrayList.add(productListModel);

        productListModel = new ProductListModel();
        productListModel.setProductName("Pineapple");
        productListModel.setProductPrice("$8 ");
        productListModel.setkG("1Kg");
        productListModel.setTotalKg(0);
        productListModel.setProductImage(R.drawable.pineple);
        productListModelArrayList.add(productListModel);


        return productListModelArrayList;

    }

    public List<CartlistModel> getCartList()
    {

        List<CartlistModel> cartlistModelNewList = new ArrayList<>();
        CartlistModel productListModel = new CartlistModel();

        productListModel.setProductName("Apple");
        productListModel.setProductPrice(10);
        productListModel.setProductImages(R.drawable.apple);
        productListModel.setKg("1Kg");
        productListModel.setProductQuantity(1);
        cartlistModelNewList.add(productListModel);


        productListModel = new CartlistModel();
        productListModel.setProductName("Guava");
        productListModel.setProductPrice(50);
        productListModel.setProductImages(R.drawable.gw);
        productListModel.setKg("1Kg");
        productListModel.setProductQuantity(1);
        cartlistModelNewList.add(productListModel);

        productListModel = new CartlistModel();
        productListModel.setProductName("Graps");
        productListModel.setProductPrice(30);
        productListModel.setProductImages(R.drawable.graps);
        productListModel.setKg("1Kg");
        productListModel.setProductQuantity(1);
        cartlistModelNewList.add(productListModel);

        productListModel = new CartlistModel();
        productListModel.setProductName("Pineapple");
        productListModel.setProductPrice(15);
        productListModel.setProductImages(R.drawable.pineple);
        productListModel.setKg("1Kg");
        productListModel.setProductQuantity(1);
        cartlistModelNewList.add(productListModel);


       return cartlistModelNewList;


    }


    public List<Notification> getNotificationList()
    {

        List<Notification> notificationArrayList = new ArrayList<>();


        Notification Notification = new Notification();
        Notification.setName("Special offer for Graps.");
        Notification.setDateTime("Today 10:10 PM");
        notificationArrayList.add(Notification);

        Notification = new Notification();
        Notification.setName("Special offer for saffola oil.");
        Notification.setDateTime("Today 10:10 PM");
        notificationArrayList.add(Notification);

        Notification = new Notification();
        Notification.setName("Special offer for Apple.");
        Notification.setDateTime("Today 10:10 PM");
        notificationArrayList.add(Notification);

        Notification = new Notification();
        Notification.setName("Special offer for Pineapple.");
        Notification.setDateTime("Today 10:10 PM");
        notificationArrayList.add(Notification);


       return notificationArrayList;


    }



    public List<AddressBookModel> getAddressList()
    {
        List<AddressBookModel> addressBookModelArrayList = new ArrayList<>();
        AddressBookModel orderListModel = new AddressBookModel();

        orderListModel.setName("Jems");
        orderListModel.setAddress("California, USA");
        orderListModel.setPinCode("123456");
        orderListModel.setMobile_no("+1025478956");
        orderListModel.setSelected(true);

        addressBookModelArrayList.add(orderListModel);

        orderListModel = new AddressBookModel();
        orderListModel.setName("Lee");
        orderListModel.setAddress("Florida, USA");
        orderListModel.setPinCode("789456");
        orderListModel.setMobile_no("+1027894562");
        orderListModel.setSelected(false);
        addressBookModelArrayList.add(orderListModel);


        return addressBookModelArrayList;


    }

    public List<OrderListModel> getOrderList() {

        List<OrderListModel> orderListModelList = new ArrayList<>();


        OrderListModel OrderListModelNew = new OrderListModel();
        OrderListModelNew.setProName("Apple");
        OrderListModelNew.setOrderId("#112233");
        OrderListModelNew.setPrice("$10 ");
        OrderListModelNew.setKg("1Kg");
        OrderListModelNew.setDate("12/12/2017");
        OrderListModelNew.setTime("10:10 PM");
        OrderListModelNew.setOrderImage(R.drawable.apple);
        OrderListModelNew.setStatus("Shipped");
        orderListModelList.add(OrderListModelNew);

        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setProName("Guava");
        OrderListModelNew.setOrderId("#67865");
        OrderListModelNew.setPrice("$15 ");
        OrderListModelNew.setKg("1Kg");
        OrderListModelNew.setDate("12/12/2017");
        OrderListModelNew.setTime("10:10 PM");
        OrderListModelNew.setOrderImage(R.drawable.gw);
        OrderListModelNew.setStatus("Shipped");
        orderListModelList.add(OrderListModelNew);

        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setProName("Graps");
        OrderListModelNew.setOrderId("#336699");
        OrderListModelNew.setPrice("$20 ");
        OrderListModelNew.setKg("1Kg");
        OrderListModelNew.setDate("12/12/2017");
        OrderListModelNew.setTime("10:10 PM");
        OrderListModelNew.setStatus("Shipped");
        OrderListModelNew.setOrderImage(R.drawable.graps);
        orderListModelList.add(OrderListModelNew);


        OrderListModelNew = new OrderListModel();
        OrderListModelNew.setProName("Pineapple");
        OrderListModelNew.setOrderId("#449900");
        OrderListModelNew.setPrice("$18 ");
        OrderListModelNew.setKg("1Kg");
        OrderListModelNew.setDate("12/12/2017");
        OrderListModelNew.setTime("10:10 PM");
        OrderListModelNew.setStatus("Shipped");
        OrderListModelNew.setOrderImage(R.drawable.pineple);
        orderListModelList.add(OrderListModelNew);


        return orderListModelList;


    }




}
