package com.vantu.retrofitwithrecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vantu.retrofitwithrecyclerview.adapter.RecyclerViewAdapter;
import com.vantu.retrofitwithrecyclerview.api.ApiClient;
import com.vantu.retrofitwithrecyclerview.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Post> posts;
    private RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.recyclerView_post);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // vì enqueue chạy kiểu Async nên nếu đặt 2 lệnh kia bên ngoài thì ko nhận đc dữ liệu
        getPosts();
    }

    private void getPosts() {
        ApiClient.apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Toast.makeText(MainActivity.this, "Call API success.", Toast.LENGTH_SHORT).show();
                posts = response.body();

                recyclerViewAdapter = new RecyclerViewAdapter(posts);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Call API error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}