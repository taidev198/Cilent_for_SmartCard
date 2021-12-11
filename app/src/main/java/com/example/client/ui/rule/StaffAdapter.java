package com.example.client.ui.rule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.client.R;

import com.example.client.data.model.Staff;
import com.example.client.ui.base.BaseAdapter;
import com.example.client.ui.base.BaseViewHolder;
import com.example.client.ui.base.OnRecyclerItemClickListener;

public class StaffAdapter extends BaseAdapter<Staff, OnRecyclerItemClickListener<Staff>, StaffAdapter.StaffViewHolder> {


    public StaffAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_staff, viewGroup, false);
        return new StaffViewHolder(mContext, itemView, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder staffViewHolder, int i) {
        staffViewHolder.bindData(mItems.get(i));
    }

    public class StaffViewHolder extends BaseViewHolder<Staff, OnRecyclerItemClickListener<Staff>> {

        private TextView mTextTitle;
        private TextView mTextSubTitle;


        public StaffViewHolder(Context context, @NonNull View itemView, OnRecyclerItemClickListener<Staff> callback) {
            super(context, itemView, callback);
            mTextTitle = itemView.findViewById(R.id.text_title_staff);
            mTextSubTitle = itemView.findViewById(R.id.text_sub);

        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void bindData(Staff staff) {
            if (staff != null) {
                mItem = staff;
                mTextTitle.setText(staff.getFullname());
               // mTextSubTitle.setText(department.getmQuanity());
            }
        }
    }
}
