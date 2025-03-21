package com.vantu.kiemtra;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.vantu.kiemtra.api.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends AppCompatActivity {

    private String email;
    private EditText editText_register_otp, editText_verify_email;
    private ImageButton imageButton_verify_otp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText_verify_email = findViewById(R.id.editText_verify_email);
        editText_register_otp = findViewById(R.id.editText_register_otp);
        imageButton_verify_otp = findViewById(R.id.imageButton_verify_otp);

        getEmail();

        imageButton_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void verify() {
        String otp = editText_register_otp.getText().toString();

        if (!TextUtils.isEmpty(otp)){
            ApiClient.apiService.verify(email, otp).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Intent intent = new Intent(VerifyOtpActivity.this, LoginActivity.class);
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
                public void onFailure(Call<String> call, Throwable throwable) {
                    Log.e("REGISTER_FAILURE", "Lỗi kết nối API: " + throwable.getMessage());

                    Intent intent = new Intent(VerifyOtpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please enter OTP.", Snackbar.LENGTH_LONG).show();
        }
    }

    private void getEmail() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        editText_verify_email.setText(email);
    }
}