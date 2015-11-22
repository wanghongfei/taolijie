package com.fh.taolijie.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by whf on 11/22/15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SignAndPolicy {
    public String sign;
    public String policy;

    public String saveKey;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSaveKey() {
        return saveKey;
    }

    public void setSaveKey(String saveKey) {
        this.saveKey = saveKey;
    }

}
