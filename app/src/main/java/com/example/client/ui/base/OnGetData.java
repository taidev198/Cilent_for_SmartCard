package com.example.client.ui.base;

public interface OnGetData<T> {

    void onSuccess(T t);
    void onFailure(Exception e);

}
