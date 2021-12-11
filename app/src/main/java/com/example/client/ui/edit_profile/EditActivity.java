package com.example.client.ui.edit_profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.client.R;
import com.example.client.data.model.Department;
import com.example.client.data.model.Sex;
import com.example.client.data.model.Staff;
import com.example.client.utils.SpinnerDialog;
import com.example.client.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements EditContract.View {
    private EditPresenter mPresenter;
    protected Staff mStaff;
    private EditText text_id;
    private EditText text_name;
    private EditText text_address;
    private EditText text_birth;
    private TextView text_sex;
    private TextView text_department;
    private ImageView imv_avatar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private ArrayList<Department> listDepartments = new ArrayList<>();
    private ArrayList<Sex> listSex = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mPresenter = new EditPresenter(this);
        text_id = findViewById(R.id.tv_id);
        text_name = findViewById(R.id.tv_name);
        text_address = findViewById(R.id.tv_address);
        text_birth = findViewById(R.id.tv_birth);
        text_sex = findViewById(R.id.tv_sex);
        listDepartments.addAll((List<Department>) getIntent().getSerializableExtra("list"));
        listSex.add(new Sex(0, "Nam"));
        listSex.add(new Sex(1, "Nữ"));
        text_department = findViewById(R.id.tv_department);
        imv_avatar = findViewById(R.id.imv_avatar);
        mStaff = (Staff) getIntent().getSerializableExtra("user");
        initData();
        findViewById(R.id.btn_save).setOnClickListener(view -> {
            mStaff.setId(text_id.getText().toString());
            mStaff.setFullname(text_name.getText().toString());
            mStaff.setAddress(text_address.getText().toString());
            mStaff.setBirth(text_birth.getText().toString());

            mPresenter.editUser(mStaff);
        });
        text_department.setOnClickListener(view -> {
            Tools.showSpinnerDialog(this, listDepartments, "Chọn phòng ban", new SpinnerDialog.OnSpinerItemClick<Department>() {
                @Override
                public void onClick(String var1, Department data) {
                    text_department.setText(var1);
                    mStaff.setIdDepartment(data.getMid());
                }
            });
        });
        text_sex.setOnClickListener(view -> {
            Tools.showSpinnerDialog(this, listSex, "Chọn giới tính", new SpinnerDialog.OnSpinerItemClick<Sex>() {
                @Override
                public void onClick(String var1, Sex data) {
                    text_sex.setText(var1);
                    mStaff.setGender(data.getValue()+"");
                }
            });
        });
    }

    private void initData() {
        text_name.setText(mStaff.getFullname());
        text_address.setText(mStaff.getAddress());
        Date date = new Date(mStaff.getBirth());
        text_birth.setText(simpleDateFormat.format(date));
        text_sex.setText(mStaff.getGender().equals("0") ? "Nam" : "Nữ");
        text_id.setText(mStaff.getId());
        text_department.setText(mStaff.getDepartment());
        try {
            byte[] imageByteArray = Base64.decode(mStaff.getAvatar(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            imv_avatar.setImageBitmap(bmp);
        } catch (Exception e) {

        }


    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
    }
}