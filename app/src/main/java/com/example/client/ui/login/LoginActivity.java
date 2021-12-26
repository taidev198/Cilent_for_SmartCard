package com.example.client.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.data.StaffRemoteDataSource;
import com.example.client.data.StaffRepository;
import com.example.client.data.model.Admin;
import com.example.client.data.model.Department;
import com.example.client.data.model.Staff;
import com.example.client.data.response.AdminResponse;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.StaffResponse;
import com.example.client.ui.base.BaseActivity;
import com.example.client.ui.main.MainActivity;
import com.example.client.utils.Tools;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements LoginContract.View,
        View.OnClickListener {


    private LoginPresenter mPresenter;
    private RelativeLayout mLoginBtn;
    private TextInputEditText mUsername;
    private TextInputEditText mPass;
    private Pattern pattern;
    private Pattern patternPass;
    private final String USERNAME_PATTERN = "^[A-Z0-9]*$";
    private final String PASS_PATTERN = "^[a-zA-Z1-9]{6,8}$";
    private Admin admin;
    private CheckBox cbRemember;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initComponents() {
        Tools.setSystemBarLight(this);
        Tools.setSystemBarColor(this, R.color.white);
        pattern = Pattern.compile(USERNAME_PATTERN);
        patternPass = Pattern.compile(PASS_PATTERN);
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(this);
        mUsername = findViewById(R.id.text_username);
        mPass = findViewById(R.id.text_pass);
        cbRemember = findViewById(R.id.cb_remember);

    }

    @Override
    protected void initData() {
        mPresenter = new LoginPresenter(StaffRepository.
                getInstance(StaffRemoteDataSource.
                        getInstance()), this);
    }

    @Override
    public void onLoginSuccess(AdminResponse staff) {
        if (staff.getMessage().equals("login sucessfully")) {
            mPresenter.getDepartment();
            admin = staff.getAdmin();
        } else Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginFailure(String error) {

    }

    @Override
    public void onGetDepartmentSuccess(DepartmentResponse departmentResponse) {
//        for (Department department : departmentResponse.getDepartments()) {
//            if (department.getMid() == mStaff.getIdDepartment()) {
//                mStaff.setDepartment(department.getName());
//                break;
//            }
//        }
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        //To pass:
        if (cbRemember.isChecked()){
            SharedPreferences preferences = getSharedPreferences("SHARE", MODE_PRIVATE);
            preferences.edit().putString("user", new Gson().toJson(admin)).apply();
        }
        intent.putExtra("user", admin);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (R.id.btn_login == view.getId()) {
            if (validateLogin()) {
                Admin ad = new Admin();
                ad.setUsername(mUsername.getText().toString());
                ad.setPassword(mPass.getText().toString());
                mPresenter.doLogin(ad);
            }
        }
    }

    private boolean validateLogin() {
        if (mUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!mUsername.getText().toString().equals("admin")) {
            Toast.makeText(this, "Sai tên đăng nhập!", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (!pattern.matcher(mUsername.getText().toString()).matches()) {
//            Toast.makeText(this, "Tên đăng nhập chưa đúng định dạng!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (mPass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (!patternPass.matcher(mPass.getText().toString()).matches()) {
//            Toast.makeText(this, "Mật khẩu chưa đúng định dạng!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }
}
