package com.vantu.viduslide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Thread(() -> {
            try {
                // Giả lập công việc mất 2 giây trong worker thread
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Gửi một Runnable từ Worker Thread lên Main Thread qua Handler bằng cách tạo 1 Handler liên kết với Main Looper chạy trên mainThread, sau đó dùng post() để gửi message/runnable vào MessageQueue
            new Handler(Looper.getMainLooper()).post(() -> {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            });
        }).start();

    }
}