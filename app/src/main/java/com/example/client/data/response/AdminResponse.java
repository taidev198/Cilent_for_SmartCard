package com.example.client.data.response;

import com.example.client.data.model.Admin;
import com.example.client.data.model.Staff;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminResponse {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("user")
    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
