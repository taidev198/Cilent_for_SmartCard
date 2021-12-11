package com.example.client.ui.add_department;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.client.R;
import com.example.client.data.model.Department;
import com.google.android.material.textfield.TextInputEditText;

public class AddDepartmentActivity extends AppCompatActivity implements AddDepartmentContract.View {
    private AddDepartmentPresenter mPresenter;
    TextInputEditText edtName, edtQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        mPresenter = new AddDepartmentPresenter(this);
        edtName = findViewById(R.id.text_name);
        edtQuantity = findViewById(R.id.text_sl);

        findViewById(R.id.btn_add).setOnClickListener(view -> {
            if (edtName.getText().toString().equals("")) {
                Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                return;
            }
            if (edtQuantity.getText().toString().equals("")) {
                Toast.makeText(this, "Vui lòng nhập sĩ số", Toast.LENGTH_SHORT).show();
                return;
            }

            Department department = new Department();
            department.setName(edtName.getText().toString());
            department.setmQuanity(Integer.parseInt(edtQuantity.getText().toString()));
            department.setMid(getIntent().getIntExtra("id", 0));

            mPresenter.addDepartments(department);
        });
        findViewById(R.id.btn_back).setOnClickListener(view -> finish());
    }

    @Override
    public void onAddDepartmentsSuccess() {
        Toast.makeText(this, "Thêm phòng ban thành công!", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void showError() {
        Toast.makeText(this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
    }
}