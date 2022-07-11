package com.serhat.translate.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://google-translate1.p.rapidapi.com/language/translate/v2";
    public static final String X_RapidAPI_Key = "7908618473msh9cd8c0a8a7173d8p131fc2jsn7d99912fb186";
    public static final String X_RapidAPI_HOST = "google-translate1.p.rapidapi.com";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
