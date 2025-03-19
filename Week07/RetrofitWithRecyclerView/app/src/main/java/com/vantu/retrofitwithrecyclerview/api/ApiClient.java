package com.vantu.retrofitwithrecyclerview.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            // parse Json về List<User>
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final ApiService apiService = retrofit.create(ApiService.class);
}
