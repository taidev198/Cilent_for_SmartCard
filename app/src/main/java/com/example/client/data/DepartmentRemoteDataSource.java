package com.example.client.data;

import com.example.client.data.api.ApiClient;
import com.example.client.data.model.Department;
import com.example.client.data.model.Rule;
import com.example.client.data.model.Staff;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.api.UtilsApi;
import com.example.client.data.response.RuleResponse;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;

public class DepartmentRemoteDataSource implements DepartmentDataSource{

    private static DepartmentRemoteDataSource sInstance;
    private static ApiClient mApi;
    private DepartmentRemoteDataSource(ApiClient apiClient) {
        mApi = apiClient;
    }

    public static DepartmentRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new DepartmentRemoteDataSource(UtilsApi.getAPIService());
        }
        return sInstance;
    }

    @Override
    public Call<DepartmentResponse> getDepartments() {
        return mApi.getDepartments();
    }

    @Override
    public Call<StaffsResponse> getStaffs(int id) {
        return mApi.getStaffs(id);
    }

    @Override
    public Call<DepartmentResponse> addDepartments(Department department) {
        return mApi.addDepartments(department);
    }

    @Override
    public Call<DepartmentResponse> addRule(Rule rule) {
        return mApi.addRule(rule);    }

    @Override
    public Call<RuleResponse> getRule() {
        return mApi.getRule();    }

    @Override
    public Call<Staff> editUser(Staff staff) {
        return mApi.editUser(staff);
    }
    @Override
    public Call<StaffResponse> deletetUser(String staff) {
        return mApi.deletetUser(staff);
    }
}
