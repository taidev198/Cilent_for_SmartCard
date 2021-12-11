package com.example.client.ui.main;

import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.response.DepartmentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContact.Presenter{

    MainContact.View mView;

    public MainPresenter(MainContact.View mView) {
        this.mView = mView;
    }

    @Override
    public void getDepartments() {
        DepartmentRepository.
                getInstance(DepartmentRemoteDataSource.getInstance())
                .getDepartments()
                .enqueue(new Callback<DepartmentResponse>() {
                    @Override
                    public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                        mView.showDepartments(response.body() != null ? response.body().getDepartments() : null);
                    }

                    @Override
                    public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
