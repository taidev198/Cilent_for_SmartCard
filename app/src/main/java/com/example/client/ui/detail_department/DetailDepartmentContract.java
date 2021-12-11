package com.example.client.ui.detail_department;

import com.example.client.data.model.Department;
import com.example.client.data.model.Staff;

import java.util.List;

public interface DetailDepartmentContract {
    interface Presenter {
        void getDepartmentUser(int id);
        void deleteUser(String name, int position);
    }

    interface View {
        void showDepartmentUser(List<Staff> staff);

        void showError();

        void onDeleteSuccess(int position);

        void onDeleteError();
    }

}
