package com.serhat.translate.api;

import com.serhat.translate.model.LanguagesResponse;
import com.serhat.translate.model.TranslateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("languages")
    Call<LanguagesResponse> getLanguages(@Header("X-RapidAPI-Key") String api_key, @Header("X-RapidAPI-Host") String api_host);

    @POST
    @FormUrlEncoded
    Call<TranslateResponse> translate(@Header("X-RapidAPI-Key") String api_key, @Header("X-RapidAPI-Host") String api_host, @Field("q") String query, @Field("target") String target);

    @POST
    @FormUrlEncoded
    Call<TranslateResponse> translateAndDetectSource(@Header("X-RapidAPI-Key") String api_key, @Header("X-RapidAPI-Host") String api_host, @Field("q") String query, @Field("target") String target, @Field("source") String source);
}
