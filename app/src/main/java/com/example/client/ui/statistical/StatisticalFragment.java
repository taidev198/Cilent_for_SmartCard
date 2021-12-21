package com.example.client.ui.statistical;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.client.R;
import com.example.client.data.model.Department;
import com.example.client.data.model.Staff;
import com.example.client.ui.base.BaseFragment;
import com.example.client.ui.main.MainActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StatisticalFragment extends BaseFragment implements StatisticalContract.View {
    private static Staff mUser;
    private StatisticalPresenter mPresenter;
    BarChart barChart;
    List<Department> mDepartments;
    Spinner spYear, spMonth;
    private int count = 0;
    String[] arrMonth;
    String[] arrYear;
    Date date;
    String month = "", year = "";
    List<Staff> mStaff = new ArrayList<>();
    private int xy = 0;

    public static Fragment newInstance(Staff user) {
        mUser = user;
        return new StatisticalFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_statistical;
    }

    @Override
    protected void initComponents(View view) {
        mPresenter = new StatisticalPresenter(this);
        date = new Date();
        barChart = view.findViewById(R.id.bar_chart);
        Description ds = new Description();
        ds.setText("Khoa");
        barChart.setDescription(ds);
        spYear = view.findViewById(R.id.sp_year);
        spMonth = view.findViewById(R.id.sp_month);
        mDepartments = ((MainActivity) getActivity()).getmDepartments();
        arrMonth = getResources().getStringArray(R.array.month_array);
        arrYear = getResources().getStringArray(R.array.year_array);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrMonth);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrYear);
        spMonth.setAdapter(adapterMonth);
        spYear.setAdapter(adapterYear);
        spYear.setSelection(mPresenter.getYear(arrYear, String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
        spMonth.setSelection(date.getMonth());
        month = date.getMonth() + "";
        year = (String) spYear.getSelectedItem();
        if (mDepartments != null) {
            for (int i = 0; i < mDepartments.size(); i++) {
                mPresenter.getDepartmentUser(mDepartments.get(i).getMid(), i);
            }
        }
        view.findViewById(R.id.btn_view).setOnClickListener(view1 -> {
            for (int i = 0; i < arrMonth.length; i++) {
                if (arrMonth[i].equals(String.valueOf(spMonth.getSelectedItem()))) {
                    month = "" + i;
                }
            }
            year = String.valueOf(spYear.getSelectedItem());
            for (int i = 0; i < mDepartments.size(); i++) {
                mPresenter.getDepartmentUser(mDepartments.get(i).getMid(), i);
            }
        });
    }


    @Override
    protected void initData() {


    }

    private void initDataChart() {
        BarDataSet dataSet = new BarDataSet(mPresenter.getDatToChart(mDepartments), "data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);
        dataSet.setValueTextColor(Color.BLACK);
        BarData data = new BarData(dataSet);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ((int) value) + "";
            }
        });
        barChart.setData(data);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setMaxVisibleValueCount(12);
        barChart.animateY(2000);
        barChart.getXAxis().setLabelCount(dataSet.getEntryCount());
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);

        barChart.getAxisLeft().setLabelCount(mPresenter.countColumnData , false);
        barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
//                if (((int) value) == 0)
//                    return "";
                return  "";
            }
        });
        barChart.getAxisRight().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "";
            }
        });
        barChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return " " + mDepartments.get((int) value).getShortName();
            }
        });
    }

    @Override
    public void showStaffs(List<Staff> staffs) {

    }

    @Override
    public void showDepartmentUser(List<Staff> staff, int position) {
        count++;
        if (staff == null || staff.size() == 0) {
            return;
        }
        mPresenter.setDataDepartment(mDepartments.get(position), staff, month, year);

        if (count >= mDepartments.size()) {
            initDataChart();
        }
    }

    @Override
    public void showError() {
        count++;
        if (count == mDepartments.size()) {
            initDataChart();
        }
    }
}