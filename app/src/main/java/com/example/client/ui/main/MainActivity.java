package com.example.client.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.client.R;
import com.example.client.data.model.Department;
import com.example.client.data.model.Staff;
import com.example.client.ui.base.BaseActivity;
import com.example.client.ui.home.HomeFragment;
import com.example.client.ui.login.LoginActivity;
import com.example.client.ui.rule.RuleFragment;
import com.example.client.ui.statistical.StatisticalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainContact.View {

    private BottomNavigationView mBottomNavigationView;
    private int mCurrentPosition;
    private MainPresenter mPresenter;
    List<Department> mDepartments;
    Toolbar toolbar;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        mPresenter = new MainPresenter(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBottomNavigationView = findViewById(R.id.navigation_main);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    @Override
    protected void initData() {
        mPresenter.getDepartments();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                mCurrentPosition = 0;
                toolbar.setTitle("Danh sách phòng ban");
                replaceFragment(getSupportFragmentManager(), HomeFragment.newInstance());
                break;
            case R.id.action_profile:
                mCurrentPosition = 1;
                toolbar.setTitle("Cài đặt luật");
                replaceFragment(getSupportFragmentManager(),
                        RuleFragment.newInstance(1));
                break;
            case R.id.action_:
                mCurrentPosition = 2;
                toolbar.setTitle("Thống kê đi muộn");
                replaceFragment(getSupportFragmentManager(),
                        StatisticalFragment.newInstance((Staff) getIntent().
                                getSerializableExtra("user")));
                break;
        }
        return true;
    }

    @Override
    public void showDepartments(List<Department> departments) {
        mDepartments = departments;
        toolbar.setTitle("Ds phòng ban");
    }

    @Override
    public void showError(Exception e) {

    }

    public List<Department> getmDepartments() {
        return mDepartments;
    }

    public void setmDepartments(List<Department> mDepartments) {
        this.mDepartments = mDepartments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences preferences = getSharedPreferences("SHARE", MODE_PRIVATE);
            preferences.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}