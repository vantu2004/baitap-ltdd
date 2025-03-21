package com.vantu.kiemtra.api;

import com.vantu.kiemtra.model.Users;
import com.vantu.kiemtra.request.RegisterRequest;
import com.vantu.kiemtra.response.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login")
    Call<Users> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("verify")
    Call<String> verify(@Query("email") String email,
                                  @Query("otp") String otp);
    @GET("main")
    Call<List<Users>> getUsers();
}

