package com.vantu.webviewclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vantu.webviewclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tránh lỗi nếu không có ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Cho phép WebView tự động thu nhỏ nội dung để vừa với màn hình thiết bị
        binding.webview.getSettings().setLoadWithOverviewMode(true);

        // Kích hoạt chế độ hiển thị viewport rộng (giúp hiển thị trang web như trên desktop)
        binding.webview.getSettings().setUseWideViewPort(true);

        // Bật JavaScript – cần thiết để chạy các script trên trang web hiện đại
        binding.webview.getSettings().setJavaScriptEnabled(true);

        // Gán WebViewClient để xử lý link trong WebView thay vì mở trình duyệt bên ngoài
        binding.webview.setWebViewClient(new WebViewClient());

        // Cho phép người dùng phóng to/thu nhỏ nội dung trong WebView
        binding.webview.getSettings().setBuiltInZoomControls(true);

        // Bật hỗ trợ DOM Storage (giống như localStorage trong trình duyệt)
        binding.webview.getSettings().setDomStorageEnabled(true);

        // Bật hỗ trợ cơ sở dữ liệu HTML5 trong WebView
        binding.webview.getSettings().setDatabaseEnabled(true);

        // Cấu hình cache: nếu có cache thì dùng, nếu không thì mới tải lại từ Internet
        binding.webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Gán WebChromeClient để xử lý các tính năng nâng cao như alert(), console.log, upload file, v.v.
        binding.webview.setWebChromeClient(new WebChromeClient());

        binding.webview.loadUrl("https://phimmoichill.boo/");

    }

}