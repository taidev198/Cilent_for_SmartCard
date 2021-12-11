package com.example.client.data.response;

import com.example.client.data.model.Staff;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffResponse {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("user")
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
