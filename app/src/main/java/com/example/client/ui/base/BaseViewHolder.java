package com.example.client.ui.base;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T, L extends BaseRecyclerListener> extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected T mItem;
    protected L mCallback;
    protected Context mContext;

    public BaseViewHolder(Context context, @NonNull View itemView, L callback) {
        super(itemView);
        mContext = context;
        mCallback = callback;
        itemView.setOnClickListener(this);
    }

    @Override
    public abstract void onClick(View v);

    public abstract void bindData(T t);
}