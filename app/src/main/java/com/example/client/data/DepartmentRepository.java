package com.example.client.data;

import com.example.client.data.model.Department;
import com.example.client.data.model.Rule;
import com.example.client.data.model.Staff;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.RuleResponse;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;

public class DepartmentRepository implements DepartmentDataSource {
    private static DepartmentRepository sInstance;
    private static DepartmentRemoteDataSource mRemote;

    private DepartmentRepository(DepartmentRemoteDataSource departmentRemoteDataSource) {
        mRemote = departmentRemoteDataSource;

    }

    public static DepartmentRepository getInstance(DepartmentRemoteDataSource departmentRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new DepartmentRepository(departmentRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public Call<DepartmentResponse> getDepartments() {
        return mRemote.getDepartments();
    }

    @Override
    public Call<StaffsResponse> getStaffs(int id) {
        return mRemote.getStaffs(id);
    }

    @Override
    public Call<DepartmentResponse> addDepartments(Department department) {
        return mRemote.addDepartments(department);
    }

    @Override
    public Call<DepartmentResponse> addRule(Rule rule) {
        return mRemote.addRule(rule);
    }

    @Override
    public Call<RuleResponse> getRule() {
        return mRemote.getRule();
    }

    @Override
    public Call<Staff> editUser(Staff staff) {
        return mRemote.editUser(staff);
    }
    @Override
    public Call<StaffResponse> deletetUser(String staff) {
        return mRemote.deletetUser(staff);
    }
}
