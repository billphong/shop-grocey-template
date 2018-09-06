package com.summit.service.model.order;

public class OrderModel {
    private int UserID;
    private String ShipPhone;
    private String ShipInfo;
    private String ShipAddress;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        this.UserID = userID;
    }

    public String getShipPhone() {
        return ShipPhone;
    }

    public void setShipPhone(String shipPhone) {
        this.ShipPhone = shipPhone;
    }

    public String getShipInfo() {
        return ShipInfo;
    }

    public void setShipInfo(String shipInfo) {
        this.ShipInfo = shipInfo;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.ShipAddress = shipAddress;
    }
}
