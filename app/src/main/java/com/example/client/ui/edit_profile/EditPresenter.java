package com.example.client.ui.edit_profile;

import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.model.Staff;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPresenter implements EditContract.Presenter {
    EditContract.View mView;

    public EditPresenter(EditContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void editUser(Staff staff) {
        DepartmentRepository.getInstance(DepartmentRemoteDataSource.getInstance()).editUser(staff).enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (response.body() != null) {
                    mView.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                mView.showError();
            }
        });
    }
}
