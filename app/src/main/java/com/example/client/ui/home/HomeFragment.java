package com.example.client.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.client.R;
import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.model.Department;
import com.example.client.ui.add_department.AddDepartmentActivity;
import com.example.client.ui.base.BaseFragment;
import com.example.client.ui.base.OnRecyclerItemClickListener;
import com.example.client.ui.detail_department.DetailDepartmentActivity;
import com.example.client.ui.main.MainActivity;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends BaseFragment implements HomeContract.View {
    public static final int RQC_ADD_DEPARTMENT = 1000;
    protected RecyclerView mRecyclerView;
    protected HomeContract.Presenter mPresenter;
    protected HomeAdapter mAdapter;
    private int mId;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponents(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swf);
        mRecyclerView = view.findViewById(R.id.recycler_departments);
        mAdapter = new HomeAdapter(getContext());
        view.findViewById(R.id.btn_add).setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddDepartmentActivity.class);
            intent.putExtra("id", mId);
            startActivityForResult(intent, RQC_ADD_DEPARTMENT);
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getDepartments();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new HomePresenter(this);
        mPresenter.getDepartments();
    }

    @Override
    public void showDepartments(List<Department> departments) {

        swipeRefreshLayout.setRefreshing(false);
        mAdapter.setCallBack(new OnRecyclerItemClickListener<Department>() {
            @Override
            public void onItemClicked(View view, long pos, Department item) {
                Intent intent = new Intent(getContext(), DetailDepartmentActivity.class);
                intent.putExtra("id", item.getMid());
                intent.putExtra("title", item.getName());
                intent.putExtra("list", (Serializable) departments);
                startActivity(intent);
            }
        });
        mId = departments.get(departments.size() - 1).getMid() + 1;
        mAdapter.setItems(departments);
        mRecyclerView.setAdapter(mAdapter);
        ((MainActivity) getActivity()).setmDepartments(departments);
    }

    @Override
    public void showError(Exception e) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RQC_ADD_DEPARTMENT:
                    mPresenter.getDepartments();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
    }
}
