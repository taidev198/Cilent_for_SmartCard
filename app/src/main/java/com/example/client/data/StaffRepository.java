package com.example.client.data;

import com.example.client.data.model.Staff;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;

public class StaffRepository implements StaffDataSource{

    private static StaffRepository sInstance;
    private static StaffRemoteDataSource mRemote;

    private StaffRepository(StaffRemoteDataSource staffRemoteDataSource) {
        mRemote = staffRemoteDataSource;

    }

    public static StaffRepository getInstance(StaffRemoteDataSource staffRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new StaffRepository(staffRemoteDataSource);
        }
        return sInstance;
    }


    @Override
    public Call<StaffsResponse> getStaffs(int id) {
        return mRemote.getStaffs(id);
    }

    @Override
    public Call<StaffResponse> login(Staff staff) {
        return mRemote.login(staff);
    }
}
