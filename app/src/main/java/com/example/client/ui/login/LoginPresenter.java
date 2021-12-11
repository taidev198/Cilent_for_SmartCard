package com.example.client.ui.login;



import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.StaffRemoteDataSource;
import com.example.client.data.StaffRepository;
import com.example.client.data.api.ApiClient;
import com.example.client.data.api.UtilsApi;
import com.example.client.data.model.Staff;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.StaffResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private ApiClient apiClient;
    private StaffRepository mStaffRepo;
    private LoginContract.View mView;
    public LoginPresenter(StaffRepository staffRepository, LoginContract.View view) {
        mStaffRepo = staffRepository;
        mView = view;
        apiClient = UtilsApi.getAPIService();

    }

    @Override
    public void doLogin(Staff staff) {
        StaffRepository.
                getInstance(StaffRemoteDataSource.
                getInstance())
                .login(staff)
                .enqueue(new Callback<StaffResponse>() {
                    @Override
                    public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                        mView.onLoginSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<StaffResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void getDepartment() {
        DepartmentRepository.
                getInstance(DepartmentRemoteDataSource.getInstance())
                .getDepartments()
                .enqueue(new Callback<DepartmentResponse>() {
                    @Override
                    public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                        mView.onGetDepartmentSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
