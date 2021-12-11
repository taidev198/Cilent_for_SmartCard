package com.example.client.ui.statistical;

import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.model.Department;
import com.example.client.data.model.Staff;
import com.example.client.data.response.StaffsResponse;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticalPresenter implements StatisticalContract.Presenter {
    public int countColumnData = 1;
    StatisticalContract.View mView;

    public StatisticalPresenter(StatisticalContract.View mView) {
        this.mView = mView;
    }

    public ArrayList<BarEntry> getDatToChart(List<Department> department) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < department.size(); i++) {
            entries.add(new BarEntry(i, department.get(i).getNumberLate()));
            if (department.get(i).getNumberLate() > 0) {
                countColumnData++;
            }
        }
        return entries;
    }

    public void setDataDepartment(Department department, List<Staff> staff, String month, String year) {
        int dem = 0;
        for (Staff staff1 : staff) {
            Calendar calendar = Calendar.getInstance();

            for (Date date : staff1.getLateDate()) {
                calendar.setTime(date);
                if (date.getMonth() == Integer.parseInt(month)
                        && calendar.get(Calendar.YEAR) == Integer.parseInt(year)) {
                    dem++;
                }
            }
        }
        department.setNumberLate(dem);
    }

    public int getYear(String[] arr, String s) {
        for (int i = 0; i < arr.length; i++) {
            if (s.equals(arr[i]))
                return i;
        }
        return 0;
    }

    @Override
    public void getStaffs(int id) {

    }

    @Override
    public void getDepartmentUser(int id, int position) {
        DepartmentRepository.getInstance(DepartmentRemoteDataSource.getInstance()).getStaffs(id).enqueue(new Callback<StaffsResponse>() {
            @Override
            public void onResponse(Call<StaffsResponse> call, Response<StaffsResponse> response) {

                if ((response.body() != null ? response.body().getmStaffs() : null) != null) {
                    mView.showDepartmentUser(response.body().getmStaffs(), position);
                }
            }

            @Override
            public void onFailure(Call<StaffsResponse> call, Throwable t) {
                mView.showError();
            }
        });
    }
}
