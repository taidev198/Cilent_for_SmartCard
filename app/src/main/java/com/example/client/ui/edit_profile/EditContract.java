package com.example.client.ui.edit_profile;

import com.example.client.data.model.Staff;

import java.util.List;

public interface EditContract {
    interface Presenter {
        void editUser(Staff staff);
    }

    interface View {
        void onSuccess();

        void showError();
    }
}
