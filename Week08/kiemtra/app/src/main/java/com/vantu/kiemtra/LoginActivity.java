package com.vantu.kiemtra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.vantu.kiemtra.api.ApiClient;
import com.vantu.kiemtra.api.ApiService;
import com.vantu.kiemtra.model.Users;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView textView_register;
    private ImageButton imageButton_login;
    private EditText editText_login_email;
    private EditText editText_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        if (token != null && !token.isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng LoginActivity để không thể quay lại màn hình đăng nhập
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView_register = findViewById(R.id.textView_register);
        imageButton_login = findViewById(R.id.imageButton_login);
        editText_login_email = findViewById(R.id.editText_login_email);
        editText_login_password = findViewById(R.id.editText_login_password);

        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        imageButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = editText_login_email.getText().toString();
        String password = editText_login_password.getText().toString();

        if (!TextUtils.isEmpty(email.trim()) && !TextUtils.isEmpty(password.trim())) {

            ApiClient.apiService.login(email, password).enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getToken();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        sharedPreferences.edit().putString("token", token).apply();

                        Snackbar.make(findViewById(android.R.id.content), "Đăng nhập thành công!", Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            // Lấy nội dung lỗi từ server
                            String errorMessage = response.errorBody().string();
                            Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable throwable) {
                    Log.e("LOGIN_FAILURE", "Lỗi kết nối API: " + throwable.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Lỗi kết nối: " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });

        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your email and password", Snackbar.LENGTH_LONG).show();
        }
    }

}