package com.summit.cherrity.model.product;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.summit.cherrity.commons.Apis;
import com.summit.cherrity.util.Utils;

import org.json.JSONObject;

import java.util.Locale;

public class ProductItem implements Parcelable {
    private int id;
    private String name;
    private String img;
    private int price;
    private int oldPrice;
    private String description;
    private int discount;
    private String saleOff; //khuyen mai
    private int totalItem = 0; //tong so san pham nguoi dung muon order

    public ProductItem(int id, String name, String img, int price, String description, int discount, String saleOff){
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
        this.description = description;
        this.discount = discount;
        this.saleOff = saleOff;
    }

    public ProductItem(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("ID");
            this.name = jsonObject.getString("Name");
            this.img = (Apis.HOST + jsonObject.getString("Img")).replace("//","/");
            this.description = jsonObject.getString("Description");
            this.discount = jsonObject.getInt("Discount");
            this.saleOff = jsonObject.getString("SaleOff");
            this.oldPrice = (int)jsonObject.getDouble("Price");
            if(discount > 0){
                this.price = (100 - discount) * this.oldPrice / 100;
            }else {
                this.price = this.oldPrice;
            }

        }catch (Exception ex){
            Log.e("Init ProductItem", ex.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public String getTotalItemStr(){
        return Integer.toString(totalItem);
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }
    public String getPriceStr(){
        return Utils.Spacer(Integer.toString(price)) + " đ";
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getOldPrice() {
        return oldPrice;
    }
    public String getOldPriceStr(){
        return Utils.Spacer(Integer.toString(oldPrice)) + " đ";
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getDiscount() {
        return discount;
    }
    public String getDiscountStr(){
        return Integer.toString(discount) + " %";
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(String saleOff) {
        this.saleOff = saleOff;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        img = in.readString();
        price = in.readInt();
        oldPrice = in.readInt();
        description = in.readString();
        discount = in.readInt();
        saleOff = in.readString();
        totalItem = in.readInt();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(img);
        dest.writeInt(price);
        dest.writeInt(oldPrice);
        dest.writeString(description);
        dest.writeInt(discount);
        dest.writeString(saleOff);
        dest.writeInt(totalItem);
    }

    public int describeContents() {
        return 0;
    }
}
