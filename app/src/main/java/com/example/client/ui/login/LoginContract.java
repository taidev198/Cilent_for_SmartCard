package com.example.client.ui.login;


import com.example.client.data.model.Staff;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.StaffResponse;

public interface LoginContract {
    interface View {
        void onLoginSuccess(StaffResponse staff);

        void onLoginFailure(String error);

        void onGetDepartmentSuccess(DepartmentResponse body);
    }

    interface Presenter {
        void doLogin(Staff staff);
        void getDepartment();
    }

}
