package com.example.client.data.response;

import com.example.client.data.model.Department;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepartmentResponse {

    @SerializedName("departments")
    @Expose
    private List<Department> mDepartments;

    public List<Department> getDepartments() {
        return mDepartments;
    }

    public void setmDepartments(List<Department> mDepartments) {
        this.mDepartments = mDepartments;
    }
}
