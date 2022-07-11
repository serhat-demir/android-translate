package com.serhat.translate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslateResponse {
    @SerializedName("data")
    @Expose
    private TranslateData data;

    public TranslateData getData() {
        return data;
    }

    public void setData(TranslateData data) {
        this.data = data;
    }
}