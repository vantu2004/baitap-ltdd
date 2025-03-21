package com.vantu.kiemtra;

import android.content.Intent;
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
import com.vantu.kiemtra.request.RegisterRequest;
import com.vantu.kiemtra.response.RegisterResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText editText_register_userName, editText_register_email, editText_register_password;
    private ImageButton imageButton_register;
    private TextView textView_forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText_register_userName = findViewById(R.id.editText_register_userName);
        editText_register_email = findViewById(R.id.editText_register_email);
        editText_register_password = findViewById(R.id.editText_register_otp);
        imageButton_register = findViewById(R.id.imageButton_verify_otp);

        imageButton_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String userName = editText_register_userName.getText().toString();
        String email = editText_register_email.getText().toString();
        String password = editText_register_password.getText().toString();

        if (!TextUtils.isEmpty(userName) && ! TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            RegisterRequest registerRequest = new RegisterRequest(userName, email, password);

            ApiClient.apiService.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Intent intent = new Intent(RegisterActivity.this, VerifyOtpActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
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
                public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                    Log.e("REGISTER_FAILURE", "Lỗi kết nối API: " + throwable.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Lỗi kết nối: " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        } else{
            Snackbar.make(findViewById(android.R.id.content), "Please enter your userName, email and password", Snackbar.LENGTH_LONG).show();
        }
    }
}