package com.example.client.data;

import com.example.client.data.model.Staff;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;

public interface StaffDataSource {

    Call<StaffsResponse> getStaffs(int id);

    Call<StaffResponse> login(Staff staff);
}
