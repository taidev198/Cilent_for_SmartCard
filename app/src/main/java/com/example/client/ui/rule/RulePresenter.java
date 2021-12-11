package com.example.client.ui.rule;

import com.example.client.data.DepartmentRemoteDataSource;
import com.example.client.data.DepartmentRepository;
import com.example.client.data.model.Rule;
import com.example.client.data.response.DepartmentResponse;
import com.example.client.data.response.RuleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RulePresenter implements RuleContract.Presenter {
    private RuleContract.View mView;

    public RulePresenter(RuleContract.View view) {
        mView = view;
    }

    @Override
    public void getRule() {
        DepartmentRepository.getInstance(DepartmentRemoteDataSource.getInstance()).getRule().enqueue(new Callback<RuleResponse>() {
            @Override
            public void onResponse(Call<RuleResponse> call, Response<RuleResponse> response) {

                if ((response.body() != null ? response.body().getRule() : null) != null) {
                    mView.getRuleOnSuccess(response.body().getRule());
                }
            }

            @Override
            public void onFailure(Call<RuleResponse> call, Throwable t) {
                mView.showError();
            }
        });
    }

    @Override
    public void addRule(Rule rule) {
        DepartmentRepository.getInstance(DepartmentRemoteDataSource.getInstance()).addRule(rule).enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                mView.addRuleOnSuccess();
            }

            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                mView.showError();
            }
        });
    }
}
