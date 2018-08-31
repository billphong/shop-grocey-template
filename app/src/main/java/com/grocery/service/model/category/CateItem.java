package com.grocery.service.model.category;

import android.util.Log;

import com.grocery.service.commons.Apis;

import org.json.JSONObject;

public class CateItem {
    private int id;
    private String txt;
    private int imgId;
    private String imgUrl;

    public CateItem(int id, String txt, int imgId) {
        this.id = id;
        this.txt = txt;
        this.imgId = imgId;
    }

    public CateItem(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("ID");
            this.txt = jsonObject.getString("Name");
            this.imgUrl = (Apis.HOST + jsonObject.getString("Img")).replace("//","/");
        }catch (Exception ex){
            Log.e("Init CateItem", ex.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
