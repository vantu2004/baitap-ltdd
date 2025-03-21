package com.vantu.kiemtra.api;

import com.vantu.kiemtra.model.Category;
import com.vantu.kiemtra.model.Product;
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
    @POST("auth/login")
    Call<Users> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("auth/verify")
    Call<String> verify(@Query("email") String email,
                                  @Query("otp") String otp);

    @GET("product/categories")
    Call<List<Category>> getAllCategories();

    @GET("product/top-sellers")
    Call<List<Product>> getTopSellers();
}

