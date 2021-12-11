package com.example.client.ui.main;

import com.example.client.data.model.Department;

import java.util.List;

public interface MainContact {
    interface Presenter {
        void getDepartments();
    }

    interface View {
        void showDepartments(List<Department> departments);

        void showError(Exception e);
    }

}
