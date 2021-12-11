package com.example.client.data;

import com.example.client.data.model.Department;
import com.example.client.data.model.Rule;
import com.example.client.data.model.Staff;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.RuleResponse;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;

public interface DepartmentDataSource {

    Call<DepartmentResponse> getDepartments();
    Call<StaffsResponse> getStaffs(int id);

    Call<DepartmentResponse> addDepartments(Department department);

    Call<DepartmentResponse> addRule(Rule rule);

    Call<RuleResponse> getRule();

    Call<Staff> editUser(Staff staff);

    Call<StaffResponse> deletetUser(String staff);
}
