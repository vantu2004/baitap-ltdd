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

public class MainActivity extends AppCompatActivity {

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

        // tạo 1 handler liên kết với Main Looper chạy trên mainThread
        // Khi gọi postDelayed(Runnable, 2000), Handler sẽ đẩy Runnable vào MessageQueue của Main Looper với thời gian chờ 2 giây. MessageQueue của Main Thread sẽ kiểm tra thời gian của Message này, nếu ch tới tg thì giữ lại trong queue chờ đến đúng tg
        // Không có Worker Thread nào gửi Message cả. Vì đã khởi tạo Handler trên Main Thread (new Handler(Looper.getMainLooper())), nên MessageQueue nhận Runnable từ chính Main Thread, chứ không phải từ Worker Thread.
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // Kết thúc IntroActivity
        }, 2000);
    }
}