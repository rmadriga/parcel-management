package com.mynt.parcel_management.library;

import org.springframework.beans.factory.annotation.Value;

public class RestModel {

    @Value("${mynt.voucher.maxretry}")
    private int maxRetry;

    @Value("${mynt.voucher.baseurl}")
    private String restUrl;

    @Value("${mynt.voucher.apikey}")
    private String apiKey;

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
