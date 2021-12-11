package com.example.client.ui.rule;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.data.model.Rule;
import com.example.client.ui.base.BaseFragment;

public class RuleFragment extends BaseFragment implements RuleContract.View {

    private TextView tvTimeIn, tvTimeOut;
    Spinner tvDateIn, tvDateOut;
    EditText edtFines;
    protected static int mId;
    TimePickerDialog timePickerDialogIn = null;
    TimePickerDialog timePickerDialogOut = null;
    private int lastSelectedHourIn = -1;
    private int lastSelectedMinuteIn = -1;
    private int lastSelectedHourOut = -1;
    private int lastSelectedMinuteOut = -1;
    private RulePresenter mPresenter;
    String[] arrDay;

    public static RuleFragment newInstance(int id) {
        mId = id;
        return new RuleFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_staff;
    }

    @Override
    protected void initComponents(View view) {
        mPresenter = new RulePresenter(this);
        mPresenter.getRule();
        tvTimeIn = view.findViewById(R.id.tv_time_in);
        tvTimeOut = view.findViewById(R.id.tv_time_out);
        tvDateIn = view.findViewById(R.id.tv_day_in);
        tvDateOut = view.findViewById(R.id.tv_day_out);
        edtFines = view.findViewById(R.id.edt_late);
        arrDay = getContext().getResources().getStringArray(R.array.days_array);
        ArrayAdapter<String> adapterIn = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrDay);
        adapterIn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tvDateIn.setAdapter(adapterIn);
        tvDateOut.setAdapter(adapterIn);
        // Time Set Listener.
        TimePickerDialog.OnTimeSetListener timeInSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = "";
                String min = "";
                if (minute < 10) {
                    min = "0" + minute;
                } else {
                    min = "" + minute;
                }
                if (hourOfDay < 10) {
                    hour = "0" + hourOfDay;
                } else {
                    hour = "" + hourOfDay;
                }
                tvTimeIn.setText(hour + "h : " + min + "m");
                lastSelectedHourIn = hourOfDay;
                lastSelectedMinuteIn = minute;
            }
        };
        TimePickerDialog.OnTimeSetListener timeOutSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = "";
                String min = "";
                if (minute < 10) {
                    min = "0" + minute;
                } else {
                    min = "" + minute;
                }
                if (hourOfDay < 10) {
                    hour = "0" + hourOfDay;
                } else {
                    hour = "" + hourOfDay;
                }
                tvTimeOut.setText(hour + "h : " + min + "m");
                lastSelectedHourOut = hourOfDay;
                lastSelectedMinuteOut = minute;
            }
        };
        timePickerDialogIn = new TimePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog,
                timeInSetListener, lastSelectedHourIn, lastSelectedMinuteIn, true);
        timePickerDialogIn.setTitle("Giờ đến");
        timePickerDialogOut = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                timeOutSetListener, lastSelectedHourOut, lastSelectedMinuteOut, true);
        timePickerDialogOut.setTitle("Giờ về");

        view.findViewById(R.id.btn_add).setOnClickListener(view1 -> {
            Rule rule = new Rule();
            rule.setId(1);
            rule.setStartTime(tvTimeIn.getText().toString());
            rule.setEndTime(tvTimeOut.getText().toString());
            rule.setStart_date(getDay(String.valueOf(tvDateIn.getSelectedItem())));
            rule.setEnd_date(getDay(String.valueOf(tvDateOut.getSelectedItem())));
            rule.setFines(Integer.parseInt(edtFines.getText().toString()));
            mPresenter.addRule(rule);
        });
    }

    private int getDay(String s) {
        for (int i = 0; i < arrDay.length; i++) {
            if (s.equals(arrDay[i])) {
                return i + 2;
            }
        }
        return 0;
    }

    @Override
    protected void initData() {

        tvTimeIn.setOnClickListener(view -> {
            timePickerDialogIn.show();
        });
        tvTimeOut.setOnClickListener(view -> {
            timePickerDialogOut.show();
        });

    }


    @Override
    public void getRuleOnSuccess(Rule rule) {
        tvTimeIn.setText(rule.getStartTime());
        tvTimeOut.setText(rule.getEndTime());
        edtFines.setText(rule.getFines() + "");
        tvDateIn.setSelection(rule.getStart_date() - 2);
        tvDateOut.setSelection(rule.getEnd_date() - 2);
    }

    @Override
    public void addRuleOnSuccess() {
        Toast.makeText(getContext(), "Lưu luật thành công !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
    }
}
