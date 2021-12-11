package com.example.client.ui.detail_department;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client.R;
import com.example.client.data.model.Department;
import com.example.client.data.model.Staff;
import com.example.client.ui.edit_profile.EditActivity;
import com.example.client.utils.SpinnerDialog;
import com.example.client.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class DetailDepartmentActivity extends AppCompatActivity implements DetailDepartmentContract.View {
    private RecyclerView rcvStudent;
    private TextView tvTitle;
    private StudentAdapter adapter;
    private List<Staff> listStaff = new ArrayList<>();
    private DetailDepartmentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_department);
        mPresenter = new DetailDepartmentPresenter(this);
        mPresenter.getDepartmentUser(getIntent().getIntExtra("id", 0));
        rcvStudent = findViewById(R.id.rcv_sv);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getIntent().getStringExtra("title"));
        adapter = new StudentAdapter(this);
        ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                        }
                    }
                });
        adapter.setOnUpdateStudent(new StudentAdapter.IOnUpdateStudent() {
            @Override
            public void edit(Staff staff, int position) {
                Intent intent = new Intent(DetailDepartmentActivity.this, EditActivity.class);
                staff.setDepartment(tvTitle.getText().toString());
                intent.putExtra("user", staff);
                intent.putExtra("list", getIntent().getSerializableExtra("list"));
                mGetContent.launch(intent);
            }

            @Override
            public void delete(Staff staff, int position) {
                showDialogConfirm(staff,position);

            }
        });
        adapter.setItems(listStaff);
        rcvStudent.setAdapter(adapter);
        tvTitle.setOnClickListener(view -> {

        });
    }

    private void showDialogConfirm(Staff staff,int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn muốn xoá học sinh này?");
        builder.setNegativeButton("Đồng ý", (dialogInterface, i) -> {
            mPresenter.deleteUser(staff.getFullname(), position);
            dialogInterface.cancel();
        });
        builder.setPositiveButton("Huỷ", (dialogInterface, i) -> {
            dialogInterface.cancel();
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public void showDepartmentUser(List<Staff> staff) {
        adapter.setItems(staff);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onDeleteSuccess(int position) {
        adapter.delete(position);
        Toast.makeText(this, "Xoá thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteError() {
        Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();

    }
}