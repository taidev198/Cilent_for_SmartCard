package com.example.client.ui.login;



import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.StaffRemoteDataSource;
import com.example.client.data.StaffRepository;
import com.example.client.data.api.ApiClient;
import com.example.client.data.api.UtilsApi;
import com.example.client.data.model.Admin;
import com.example.client.data.model.Staff;
import com.example.client.data.response.AdminResponse;
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
    public void doLogin(Admin admin) {
        StaffRepository.
                getInstance(StaffRemoteDataSource.
                getInstance())
                .login(admin)
                .enqueue(new Callback<AdminResponse>() {
                    @Override
                    public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                        mView.onLoginSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<AdminResponse> call, Throwable t) {
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
