package com.summit.cherrity.model.order;

public class ProductOrderModel {
    private int UserID;
    private int ProductID;
    private int Number;
    private int Price;
    private int OldPrice;
    private int Discount;
    private String Name;
    private String Img;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        this.UserID = userID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        this.ProductID = productID;
    }

    public int getNumber() {
        return Number;
    }

    public String getNumberStr() {
        return Integer.toString(Number);
    }

    public void setNumber(int number) {
        this.Number = number;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        this.Price = price;
    }

    public int getOldPrice() {
        return OldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.OldPrice = oldPrice;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        this.Discount = discount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        this.Img = img;
    }
}
