package com.vantu.retrofitwithrecyclerview.api;

import com.vantu.retrofitwithrecyclerview.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("posts")
    Call<List<Post>> getPosts();
}
