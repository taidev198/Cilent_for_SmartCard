package com.example.client.data;

import com.example.client.data.api.ApiClient;
import com.example.client.data.api.UtilsApi;
import com.example.client.data.model.Admin;
import com.example.client.data.model.Staff;
import com.example.client.data.response.AdminResponse;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;

public class StaffRemoteDataSource implements StaffDataSource{

    private static StaffRemoteDataSource sInstance;
    private static ApiClient mApi;
    private StaffRemoteDataSource(ApiClient apiClient) {
        mApi = apiClient;
    }

    public static StaffRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new StaffRemoteDataSource(UtilsApi.getAPIService());
        }
        return sInstance;
    }

    @Override
    public Call<StaffsResponse> getStaffs(int id) {
        return mApi.getStaffs(id);
    }

    @Override
    public Call<AdminResponse> login(Admin admin) {
        return mApi.login(admin);
    }
}
