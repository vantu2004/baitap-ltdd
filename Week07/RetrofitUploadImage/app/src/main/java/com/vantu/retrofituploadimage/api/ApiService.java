package com.vantu.retrofituploadimage.api;

import com.vantu.retrofituploadimage.constant.Const;
import com.vantu.retrofituploadimage.model.ImageDto;
import com.vantu.retrofituploadimage.response.ApiResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
//    @GET("image/download/{imageId}")
//    Call<ImageDto> getImages(@Path("imageId") Long imageId);

    // ownerId và ownerType là 2 giá trị kiểu Long/String nhưng ko truyền trực tiếp vì đang dùng @Multipart, nên mọi phần tử trong @Part đều cần là RequestBody hoặc MultipartBody.Part.
    @Multipart
    @POST("add")
    Call<ApiResponse<List<ImageDto>>> addImage(@Part(Const.KEY_OWNER_ID) RequestBody ownerId, @Part(Const.KEY_OWNER_TYPE) RequestBody ownerType, @Part MultipartBody.Part image);
}
