package com.example.client.ui.home;

import com.example.client.data.model.Department;

import java.util.List;

public interface HomeContract {

    interface Presenter {
        void getDepartments();
    }

    interface View {
        void showDepartments(List<Department> departments);

        void showError(Exception e);
    }

}
