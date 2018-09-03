package com.summit.service.model.cart;

/**
 * Created by Kailash on 10/4/18.
 */

public class CartlistModel
{
   private int productImages;
    private String productName;
    private int productQuantity;
    private int productPrice;
    private String kg;

    public int getProductImages() {
        return productImages;
    }

    public void setProductImages(int productImages) {
        this.productImages = productImages;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }



}
