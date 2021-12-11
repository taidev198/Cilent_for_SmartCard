package com.example.client.data.response;

import com.example.client.data.model.Staff;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffsResponse {
    @SerializedName("staffs")
    @Expose
    private List<Staff> mStaffs;

    public List<Staff> getmStaffs() {
        return mStaffs;
    }

    public void setmStaffs(List<Staff> mStaffs) {
        this.mStaffs = mStaffs;
    }
}
