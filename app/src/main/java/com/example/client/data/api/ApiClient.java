package com.example.client.data.api;

import com.example.client.data.model.Admin;
import com.example.client.data.model.Department;
import com.example.client.data.model.Rule;
import com.example.client.data.model.Staff;
import com.example.client.data.response.AdminResponse;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.RuleResponse;
import com.example.client.data.response.StaffResponse;
import com.example.client.data.response.StaffsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClient {


    @GET("department/getAll")
    Call<DepartmentResponse> getDepartments();

    @FormUrlEncoded
    @POST("user/department/get/")
    Call<StaffsResponse> getStaffs(@Field("id") int id);

    @POST("user/login/admin")
    Call<AdminResponse> login(@Body Admin admin);

    @POST("department/add")
    Call<DepartmentResponse> addDepartments(@Body Department department);

    @POST("department/addRule")
    Call<DepartmentResponse> addRule(@Body Rule rule);

    @POST("user/update")
    Call<Staff> editUser(@Body Staff staff);

    @FormUrlEncoded
    @POST("user/delete")
    Call<StaffResponse> deletetUser(@Field("fullname") String fullname);

    @GET("department/getRule")
    Call<RuleResponse> getRule();
}
