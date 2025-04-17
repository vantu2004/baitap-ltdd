package com.vantu.retrofituploadimage;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.health.connect.datatypes.units.Length;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.vantu.retrofituploadimage.api.ApiClient;
import com.vantu.retrofituploadimage.api.ApiService;
import com.vantu.retrofituploadimage.constant.Const;
import com.vantu.retrofituploadimage.model.ImageDto;
import com.vantu.retrofituploadimage.response.ApiResponse;
import com.vantu.retrofituploadimage.util.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int MY_REQUEST_CODE = 10;
    private Button button_select, button_upload;
    private ImageView imageView_upload, imageView_showImage;
    private TextView textView_id, textView_name, textView_downloadUrl;
    private EditText editText_ownerId, editText_ownerType;
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    mUri = uri;
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageView_upload.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button_select = findViewById(R.id.button_select);
        button_upload = findViewById(R.id.button_upload);

        imageView_upload = findViewById(R.id.imageView_upload);
        imageView_showImage = findViewById(R.id.imageView_showImage);

        textView_id = findViewById(R.id.textView_id);
        textView_name = findViewById(R.id.textView_name);
        textView_downloadUrl = findViewById(R.id.textView_downloadUrl);

        editText_ownerId = findViewById(R.id.editText_ownerId);
        editText_ownerType = findViewById(R.id.editText_ownerType);

        mProgressDialog = new ProgressDialog(this);

        button_select.setOnClickListener(v -> onClickRequestPermission());

        button_upload.setOnClickListener(v -> callApiUploadImage());
    }

    private void callApiUploadImage() {
        mProgressDialog.show();

        String ownerId = String.valueOf(editText_ownerId.getText()).trim();
        String ownerType = String.valueOf(editText_ownerType.getText()).trim();

        RequestBody requestBodyownerId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(ownerId));
        RequestBody requestBodyownerType = RequestBody.create(MediaType.parse("multipart/form-data"), ownerType);

        String realImagePath = RealPathUtil.getRealPath(this, mUri);
        Log.d("realImagePath", "callApiUploadImage: " + realImagePath);

        File file = new File(realImagePath);

        // bên backend yêu cầu token, permitAll() là được
        RequestBody requestBodyMultipartFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData(Const.KEY_MULTIPART_FILES, file.getName(), requestBodyMultipartFile);
        
        ApiClient.apiService.addImage(requestBodyownerId, requestBodyownerType, multipartBody).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ImageDto>>> call, Response<ApiResponse<List<ImageDto>>> response) {
                mProgressDialog.dismiss();
                ApiResponse apiResponse = response.body();

                Log.d("statusCode", "onResponse: " + response.code());
                
                if (apiResponse != null) {
                    List<ImageDto> imageDtos = (List<ImageDto>) apiResponse.getData();
                    ImageDto imageDto = imageDtos.get(0);

                    textView_id.setText(String.valueOf(imageDto.getId()));
                    textView_name.setText(imageDto.getName());
                    textView_downloadUrl.setText(imageDto.getDownloadUrl());

                    String fullUrl = Const.LOCAL_URL + imageDto.getDownloadUrl();

                    Glide.with(MainActivity.this).load(fullUrl).into(imageView_showImage);
                } else {
                    Snackbar.make(findViewById(R.id.main), "null", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ImageDto>>> call, Throwable throwable) {
                mProgressDialog.dismiss();
                Log.d("error", "onFailure: " + throwable.getMessage());
                Snackbar.make(findViewById(R.id.main), throwable.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void onClickRequestPermission() {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String permission;
            // từ android 13, api 33 - TIRAMISU thì ko còn dùng chung chung như READ_EXTERNAL_STORAGE nữa mà tách riêng READ_MEDIA_IMAGES, ...VIDEO, ...AUDIO
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permission = Manifest.permission.READ_MEDIA_IMAGES;
            } else {
                permission = Manifest.permission.READ_EXTERNAL_STORAGE;
            }
            requestPermissions(new String[]{permission}, MY_REQUEST_CODE);
        }
    }

    // requestCode là mã truyền trong lúc yêu cầu cấp quyền
    // permissions là danh sách quyền đã yêu cầu
    // grantResults là danh sách kết quả của quá trình yêu cầu cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

}