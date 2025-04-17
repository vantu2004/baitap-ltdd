package com.vantu.retrofituploadimage.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/api/v1/images/";

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy:MM:dd HH:mm:ss")
            .create();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            // serialize/deserialize
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    // tạo instance của apiService với đoạn cấu hình của retrofit ở trên
    public static final ApiService apiService = retrofit.create(ApiService.class);
}
