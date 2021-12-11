package com.example.client.ui.detail_department;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.client.R;
import com.example.client.data.model.Staff;
import com.example.client.ui.base.BaseAdapter;
import com.example.client.ui.base.BaseViewHolder;
import com.example.client.ui.base.OnRecyclerItemClickListener;

public class StudentAdapter extends BaseAdapter<Staff, OnRecyclerItemClickListener<Staff>, StudentAdapter.StudentViewHolder> {
    IOnUpdateStudent iOnUpdateStudent;

    public StudentAdapter(Context context) {
        super(context);
    }

    public interface IOnUpdateStudent {
        void edit(Staff staff, int position);

        void delete(Staff staff, int position);
    }

    public void setOnUpdateStudent(IOnUpdateStudent iOnUpdateStudent) {
        this.iOnUpdateStudent = iOnUpdateStudent;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student, viewGroup, false);
        return new StudentAdapter.StudentViewHolder(mContext, itemView, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(mItems.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.tvMoney);
                popupMenu.inflate(R.menu.item_menu);
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.edit:
                            iOnUpdateStudent.edit(mItems.get(position), position);
                            break;
                        default:
                            iOnUpdateStudent.delete(mItems.get(position), position);
//                            list.remove(position);
//                            notifyItemRemoved(position);
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            }
        });
    }

    public class StudentViewHolder extends BaseViewHolder<Staff, OnRecyclerItemClickListener<Staff>> {
        private TextView tvName, tvAddress, tvNumber, tvMoney;

        public StudentViewHolder(Context context, @NonNull View itemView, OnRecyclerItemClickListener<Staff> callback) {
            super(context, itemView, callback);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvNumber = itemView.findViewById(R.id.tv_number_late);
            tvMoney = itemView.findViewById(R.id.tv_money);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void bindData(Staff staff) {
            tvName.setText(staff.getFullname());
            tvAddress.setText(staff.getAddress());
            tvNumber.setText("Số lần đi muộn: " + staff.getLateDate().size());
            tvMoney.setText("Mức phạt: " + 0);
        }
    }

    public void delete(int position){
        mItems.remove(position);
        notifyItemRemoved(position);
    }
}
