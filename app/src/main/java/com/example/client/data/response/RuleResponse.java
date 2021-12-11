package com.example.client.data.response;

import com.example.client.data.model.Rule;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RuleResponse {
    @SerializedName("rule")
    @Expose
    private Rule rule;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
