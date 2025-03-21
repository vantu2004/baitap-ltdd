package com.vantu.kiemtra.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8081/api/v1/auth/";

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy:MM:dd HH:mm:ss")
            .create();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            // parse Json về List<Users>
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    // Retrofit sẽ tạo một object của ApiService (đằng sau là một class proxy) để gọi API cùng với đoạn cấu hình đã cấu hình cho retrofit ở trên
    public static final ApiService apiService = retrofit.create(ApiService.class);
}
