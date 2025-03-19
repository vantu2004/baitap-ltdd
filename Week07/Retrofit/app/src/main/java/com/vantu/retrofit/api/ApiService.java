package com.vantu.retrofit.api;

import com.vantu.retrofit.model.Comment;
import com.vantu.retrofit.model.Post;
import com.vantu.retrofit.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("posts/{postId}/comments")
    Call<List<Comment>> getCommentByPostIdWithPath(@Path("postId") int postId);

    @GET("comments")
    Call<List<Comment>> getCommentByPostIdWithQuery(@Query("postId") int postId);

    @POST("posts")
    Call<Post> insertPost(@Body Post post);
}
