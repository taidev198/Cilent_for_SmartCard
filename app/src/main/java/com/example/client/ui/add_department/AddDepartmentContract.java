package com.example.client.ui.add_department;

import com.example.client.data.model.Department;

import java.util.List;

public interface AddDepartmentContract {
    interface Presenter {
        void addDepartments(Department department);
    }

    interface View {
        void onAddDepartmentsSuccess();

        void showError();
    }
}
