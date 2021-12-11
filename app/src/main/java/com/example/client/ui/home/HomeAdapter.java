package com.example.client.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.client.R;
import com.example.client.data.model.Department;
import com.example.client.ui.base.BaseAdapter;
import com.example.client.ui.base.BaseViewHolder;
import com.example.client.ui.base.OnRecyclerItemClickListener;

public class HomeAdapter extends BaseAdapter<Department, OnRecyclerItemClickListener<Department>, HomeAdapter.HomeViewHolder> {


    public HomeAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_department, viewGroup, false);
        return new HomeViewHolder(mContext, itemView, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder homeViewHolder, int i) {
        homeViewHolder.bindData(mItems.get(i));
    }

    public class HomeViewHolder extends BaseViewHolder<Department, OnRecyclerItemClickListener<Department>> {

        private TextView mTextTitle;
        private TextView mTextSubTitle;
        private OnRecyclerItemClickListener<Department> mCallback;

        public HomeViewHolder(Context context, @NonNull View itemView, OnRecyclerItemClickListener<Department> callback) {
            super(context, itemView, callback);
            mCallback =callback;
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextSubTitle = itemView.findViewById(R.id.text_sub);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.item_track_card) {
                System.out.println("hellooo");
                mCallback.onItemClicked(v, getAdapterPosition(), mItem);
            }
        }

        @Override
        public void bindData(Department department) {
            if (department != null) {
                mItem = department;
                mTextTitle.setText(department.getName());
                mTextSubTitle.setText("Số lượng: " +department.getmQuanity());
               // mTextSubTitle.setText(department.getmQuanity());
            }
        }
    }
}
