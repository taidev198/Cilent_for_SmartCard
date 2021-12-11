package com.example.client.ui.statistical;


import com.example.client.data.model.Staff;

import java.util.List;

public interface StatisticalContract {
    interface Presenter {
        void getStaffs(int id);
        void getDepartmentUser(int id,int position);

    }

    interface View {
        void showStaffs(List<Staff> staffs);

        void showDepartmentUser(List<Staff> staff,int position);

        void showError();
    }
}
