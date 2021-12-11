package com.example.client.ui.add_department;

import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.model.Department;
import com.example.client.data.response.DepartmentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDepartmentPresenter implements AddDepartmentContract.Presenter {
    AddDepartmentContract.View mView;

    public AddDepartmentPresenter(AddDepartmentContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void addDepartments(Department department) {
        DepartmentRepository.getInstance(DepartmentRemoteDataSource.getInstance()).addDepartments(department).enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                mView.onAddDepartmentsSuccess();
            }

            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                mView.showError();
            }
        });
    }
}
