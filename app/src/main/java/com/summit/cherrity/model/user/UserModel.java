package com.summit.cherrity.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
    @JsonProperty("ID")
    private int ID;

    @JsonProperty("Name")
    private String Name ;

    @JsonProperty("Email")
    private String Email ;

    @JsonProperty("Phone")
    private String Phone ;

    @JsonProperty("Address")
    private String Address ;

    @JsonProperty("Birthday")
    private Date Birthday ;

    //not hashed
    private String Password;

    @JsonProperty("ID")
    public int getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(int ID) {
        this.ID = ID;
    }
    @JsonProperty("Name")
    public String getName() {
        return Name;
    }
    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }
    @JsonProperty("Email")
    public String getEmail() {
        return Email;
    }
    @JsonProperty("Email")
    public void setEmail(String email) {
        Email = email;
    }
    @JsonProperty("Phone")
    public String getPhone() {
        return Phone;
    }
    @JsonProperty("Phone")
    public void setPhone(String phone) {
        Phone = phone;
    }
    @JsonProperty("Address")
    public String getAddress() {
        return Address;
    }
    @JsonProperty("Address")
    public void setAddress(String address) {
        Address = address;
    }

    @JsonProperty("Birthday")
    public Date getBirthday() {
        return Birthday;
    }

    @JsonProperty("Birthday")
    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
