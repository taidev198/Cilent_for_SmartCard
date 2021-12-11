package com.example.client.ui.home;

import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.api.ApiClient;
import com.example.client.data.api.UtilsApi;
import com.example.client.data.response.DepartmentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter{
    private ApiClient apiClient;
    private DepartmentRepository mDepartmentRepository;
    private HomeContract.View mView;

    public HomePresenter(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void getDepartments() {
        apiClient = UtilsApi.getAPIService();

        DepartmentRepository
                .getInstance(DepartmentRemoteDataSource
                .getInstance())
                .getDepartments()
                .enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                mView.showDepartments(response.body() != null ? response.body().getDepartments() : null);
                System.out.println(response.raw().request().url() + " url");
//                System.out.println(response.body().getmDepartments().get(0).getName().toString());
            }

            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {

            }
        });
    }
}
