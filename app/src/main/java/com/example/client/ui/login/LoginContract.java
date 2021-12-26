package com.example.client.ui.login;


import com.example.client.data.model.Admin;
import com.example.client.data.model.Staff;
import com.example.client.data.response.AdminResponse;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.StaffResponse;

public interface LoginContract {
    interface View {
        void onLoginSuccess(AdminResponse adminResponse);

        void onLoginFailure(String error);

        void onGetDepartmentSuccess(DepartmentResponse body);
    }

    interface Presenter {
        void doLogin(Admin admin);
        void getDepartment();
    }

}
