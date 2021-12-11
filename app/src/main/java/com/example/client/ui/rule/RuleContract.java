package com.example.client.ui.rule;

import com.example.client.data.model.Rule;
import com.example.client.data.model.Staff;

import java.util.List;

public interface RuleContract {

    interface Presenter {
        void getRule();
        void addRule(Rule rule);
    }

    interface View {
        void getRuleOnSuccess(Rule rule);

        void addRuleOnSuccess();

        void showError();
    }

}
