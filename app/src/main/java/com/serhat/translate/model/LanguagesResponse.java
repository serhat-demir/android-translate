package com.serhat.translate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguagesResponse {
    @SerializedName("data")
    @Expose
    private LanguagesData data;

    public LanguagesData getData() {
        return data;
    }

    public void setData(LanguagesData data) {
        this.data = data;
    }
}