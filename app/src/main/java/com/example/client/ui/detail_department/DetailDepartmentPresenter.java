package com.example.client.ui.detail_department;

import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDepartmentPresenter implements DetailDepartmentContract.Presenter{
    DetailDepartmentContract.View mView;

    public DetailDepartmentPresenter(DetailDepartmentContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void getDepartmentUser(int id) {
        DepartmentRepository.getInstance(DepartmentRemoteDataSource.getInstance()).getStaffs(id).enqueue(new Callback<StaffsResponse>() {
            @Override
            public void onResponse(Call<StaffsResponse> call, Response<StaffsResponse> response) {
                
                if ((response.body() != null ? response.body().getmStaffs() : null) != null) {
                    mView.showDepartmentUser(response.body().getmStaffs());
                }
            }

            @Override
            public void onFailure(Call<StaffsResponse> call, Throwable t) {
                mView.showError();
            }
        });
    }

    @Override
    public void deleteUser(String name, int position) {
        DepartmentRepository.getInstance(DepartmentRemoteDataSource.getInstance()).deletetUser(name).enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                if (response.body() != null && response.body().getMessage().equals("Success")){
                    mView.onDeleteSuccess(position);
                }else {
                    mView.onDeleteError();
                }
            }

            @Override
            public void onFailure(Call<StaffResponse> call, Throwable t) {
                mView.onDeleteError();
            }
        });
    }
}
